package com.mulmeong.comment.read.application;

import com.mulmeong.comment.read.common.exception.BaseException;
import com.mulmeong.comment.read.common.response.BaseResponseStatus;
import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.ShortsRecommentResponseDto;
import com.mulmeong.comment.read.entity.ShortsComment;
import com.mulmeong.comment.read.entity.ShortsRecomment;
import com.mulmeong.comment.read.infrsatructure.ShortsRecommentRepository;
import com.mulmeong.comment.read.infrsatructure.ShortsRecommentRepositoryCustom;
import com.mulmeong.event.comment.ShortsRecommentCreateEvent;
import com.mulmeong.event.comment.ShortsRecommentDeleteEvent;
import com.mulmeong.event.comment.ShortsRecommentUpdateEvent;
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

    @Override
    public void createShortsRecomment(ShortsRecommentCreateEvent message) {
        ShortsRecomment shortsRecomment = message.toEntity();
        shortsRecommentRepository.save(shortsRecomment);
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

}
