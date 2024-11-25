package com.mulmeong.comment.read.application;

import com.mulmeong.comment.read.common.exception.BaseException;
import com.mulmeong.comment.read.common.response.BaseResponseStatus;
import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.FeedRecommentResponseDto;
import com.mulmeong.comment.read.dto.out.ShortsRecommentResponseDto;
import com.mulmeong.comment.read.entity.FeedComment;
import com.mulmeong.comment.read.entity.FeedRecomment;
import com.mulmeong.comment.read.entity.ShortsComment;
import com.mulmeong.comment.read.entity.ShortsRecomment;
import com.mulmeong.comment.read.infrsatructure.ShortsCommentRepositoryCustom;
import com.mulmeong.comment.read.infrsatructure.ShortsRecommentRepository;
import com.mulmeong.comment.read.infrsatructure.ShortsRecommentRepositoryCustom;
import com.mulmeong.event.*;
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
public class ShortsRecommentServiceImpl implements ShortsRecommentService {

    private final ShortsRecommentRepository shortsRecommentRepository;
    private final ShortsRecommentRepositoryCustom shortsRecommentRepositoryCustom;
    private final MongoTemplate mongoTemplate;

    @Override
    public void createShortsRecomment(ShortsRecommentCreateEvent message) {
        ShortsRecomment shortsRecomment = message.toEntity();
        shortsRecommentRepository.save(shortsRecomment);
        incrementRecommentCount(shortsRecomment.getCommentUuid());
    }

    @Override
    public void updateShortsRecomment(ShortsRecommentUpdateEvent message) {
        ShortsRecomment recomment = shortsRecommentRepository.findByRecommentUuid(message.getRecommentUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT)
        );
        ShortsRecomment updated = message.toEntity(recomment);
        shortsRecommentRepository.save(updated);
    }

    @Override
    public void deleteShortsRecomment(ShortsRecommentDeleteEvent message) {
        ShortsRecomment recomment = shortsRecommentRepository.findByRecommentUuid(message.getRecommentUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT)
        );
        decrementRecommentCount(recomment.getCommentUuid());
        shortsRecommentRepository.deleteByRecommentUuid(recomment.getRecommentUuid());
    }

    @Override
    public CursorPage<ShortsRecommentResponseDto> getShortsRecomments(
            String commentUuid, String lastId, Integer pageSize, Integer pageNo) {
        CursorPage<ShortsRecomment> cursorPage = shortsRecommentRepositoryCustom
                .getShortsReomments(commentUuid, lastId, pageSize, pageNo);

        return CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                .map(ShortsRecommentResponseDto::toDto).toList());
    }


    //todo : 댓글, 대댓글 수 count - 추후 batch로 구현 예정
    public void incrementRecommentCount(String commentUuid) {
        Query query = new Query(Criteria.where("commentUuid").is(commentUuid));
        Update update = new Update().inc("recommentCount", 1);
        mongoTemplate.updateFirst(query, update, ShortsComment.class);
    }

    public void decrementRecommentCount(String commentUuid) {
        Query query = new Query(Criteria.where("commentUuid").is(commentUuid));
        Update update = new Update().inc("recommentCount", -1);
        mongoTemplate.updateFirst(query, update, ShortsComment.class);
    }

}
