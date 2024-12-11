package com.mulmeong.comment.read.application;

import com.mulmeong.comment.read.common.exception.BaseException;
import com.mulmeong.comment.read.common.response.BaseResponseStatus;
import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.FeedRecommentResponseDto;
import com.mulmeong.comment.read.entity.FeedComment;
import com.mulmeong.comment.read.entity.FeedRecomment;
import com.mulmeong.comment.read.infrsatructure.FeedRecommentRepository;
import com.mulmeong.comment.read.infrsatructure.FeedRecommentRepositoryCustom;
import com.mulmeong.event.comment.FeedRecommentCreateEvent;
import com.mulmeong.event.comment.FeedRecommentDeleteEvent;
import com.mulmeong.event.comment.FeedRecommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedRecommentServiceImpl implements FeedRecommentService {

    private final FeedRecommentRepository feedRecommentRepository;
    private final FeedRecommentRepositoryCustom feedRecommentRepositoryCustom;

    @Override
    public void createFeedRecomment(FeedRecommentCreateEvent message) {
        FeedRecomment feedRecomment = message.toEntity();
        feedRecommentRepository.save(feedRecomment);
    }

    @Override
    public void updateFeedRecomment(FeedRecommentUpdateEvent message) {
        FeedRecomment recomment = feedRecommentRepository.findByRecommentUuid(message.getRecommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT)
        );
        FeedRecomment updated = message.toEntity(recomment);
        feedRecommentRepository.save(updated);
    }

    @Override
    public void deleteFeedRecomment(FeedRecommentDeleteEvent message) {
        FeedRecomment recomment = feedRecommentRepository.findByRecommentUuid(message.getRecommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT)
        );
        feedRecommentRepository.deleteByRecommentUuid(recomment.getRecommentUuid());
    }

    @Override
    public CursorPage<FeedRecommentResponseDto> getFeedRecomments(
            String commentUuid, String lastId, Integer pageSize, Integer pageNo) {
        CursorPage<FeedRecomment> cursorPage = feedRecommentRepositoryCustom
                .getFeedRecomments(commentUuid, lastId, pageSize, pageNo);

        return CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                .map(FeedRecommentResponseDto::toDto).toList());
    }

    //todo : 댓글, 대댓글 수 count - 추후 batch로 구현 예정
}
