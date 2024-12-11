package com.mulmeong.comment.read.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShortsCommentResponseVo {

    private String shortsUuid;
    private String memberUuid;
    private String commentUuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long dislikeCount;
    private Long recommentCount;
    private boolean isDeleted;
}