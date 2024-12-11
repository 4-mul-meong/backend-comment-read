package com.mulmeong.comment.read.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FeedRecommentResponseVo {

    private String commentUuid;
    private String memberUuid;
    private String recommentUuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long dislikeCount;
}
