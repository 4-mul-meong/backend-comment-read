package com.mulmeong.comment.read.application;

import com.mulmeong.comment.read.common.exception.BaseException;
import com.mulmeong.comment.read.common.response.BaseResponseStatus;
import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.FeedCommentResponseDto;
import com.mulmeong.comment.read.entity.FeedComment;
import com.mulmeong.comment.read.infrsatructure.FeedCommentRepository;
import com.mulmeong.comment.read.infrsatructure.FeedCommentRepositoryCustom;
import com.mulmeong.event.comment.FeedCommentCreateEvent;
import com.mulmeong.event.comment.FeedCommentDeleteEvent;
import com.mulmeong.event.comment.FeedCommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedCommentServiceImpl implements FeedCommentService {

    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentRepositoryCustom feedCommentRepositoryCustom;
    private final MongoTemplate mongoTemplate;

    @Override
    public void createFeedComment(FeedCommentCreateEvent message) {
        FeedComment feedComment = message.toEntity();
        feedCommentRepository.save(feedComment);
    }

    @Override
    public void updateFeedComment(FeedCommentUpdateEvent message) {
        FeedComment comment = feedCommentRepository.findByCommentUuid(message.getCommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT)
        );
        FeedComment updated = message.toEntity(comment);
        feedCommentRepository.save(updated);
    }

    @Override
    public void deleteFeedComment(FeedCommentDeleteEvent message) {
        FeedComment comment = feedCommentRepository.findByCommentUuid(message.getCommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT)
        );
        FeedComment deleted = message.toEntity(comment);
        feedCommentRepository.save(deleted);
    }


    @Override
    public CursorPage<FeedCommentResponseDto> getFeedCommentsByPage(
            String feedUuid,
            String sortBy,
            String lastId,
            Integer pageSize,
            Integer pageNo) {

        List<String> cursors = feedCommentRepository.findByFeedUuid(feedUuid).stream()
                .map(this::buildCursor)
                .toList();
        CursorPage<FeedComment> cursorPage = feedCommentRepositoryCustom.getFeedComments(
                feedUuid, sortBy, lastId, pageSize, pageNo);

        return CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                .map(FeedCommentResponseDto::toDto).toList());
    }

    public String buildCursor(FeedComment comment) {
        String cursor = String.format("%010d", comment.getLikeCount())
                + String.format("%010d", 1000000000L - comment.getDislikeCount())
                + String.format("%010d", comment.getRecommentCount())
                + comment.getId(); // ID 추가

        Query query = new Query(Criteria.where("commentUuid").is(comment.getCommentUuid()));
        Update update = new Update().set("customCursor", cursor);
        mongoTemplate.updateFirst(query, update, FeedComment.class);
        return cursor;
    }
}
