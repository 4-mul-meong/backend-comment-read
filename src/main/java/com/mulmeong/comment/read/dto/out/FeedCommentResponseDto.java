package com.mulmeong.comment.read.dto.out;

import com.mulmeong.comment.read.entity.FeedComment;
import com.mulmeong.comment.read.vo.FeedCommentResponseVo;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class FeedCommentResponseDto {

    private String feedUuid;
    private String memberUuid;
    private String commentUuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long dislikeCount;
    private Long recommentCount;
    private boolean isDeleted;

    public static FeedCommentResponseDto toDto(FeedComment feedComment) {
        return FeedCommentResponseDto.builder()
                .feedUuid(feedComment.getFeedUuid())
                .memberUuid(feedComment.getMemberUuid())
                .commentUuid(feedComment.getCommentUuid())
                .content(feedComment.getContent())
                .createdAt(feedComment.getCreatedAt())
                .updatedAt(feedComment.getUpdatedAt())
                .likeCount(feedComment.getLikeCount())
                .dislikeCount(feedComment.getDislikeCount())
                .recommentCount(feedComment.getRecommentCount())
                .isDeleted(feedComment.isDeleted())
                .build();
    }

    public FeedCommentResponseVo toVo() {
        return FeedCommentResponseVo.builder()
                .feedUuid(feedUuid)
                .memberUuid(memberUuid)
                .commentUuid(commentUuid)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .recommentCount(recommentCount)
                .isDeleted(isDeleted)
                .build();
    }
}
