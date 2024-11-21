package com.mulmeong.comment.read.application;

import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.FeedCommentResponseDto;
import com.mulmeong.event.FeedCommentCreateEvent;
import com.mulmeong.event.FeedCommentDeleteEvent;
import com.mulmeong.event.FeedCommentUpdateEvent;

public interface FeedCommentService {
    void createFeedComment(FeedCommentCreateEvent message);

    void updateFeedComment(FeedCommentUpdateEvent message);

    void deleteFeedComment(FeedCommentDeleteEvent message);

    CursorPage<FeedCommentResponseDto> getFeedCommentsByPage(
            String feedUuid,
            String sortBy,
            String lastId,
            Integer pageSize,
            Integer pageNo);
}
