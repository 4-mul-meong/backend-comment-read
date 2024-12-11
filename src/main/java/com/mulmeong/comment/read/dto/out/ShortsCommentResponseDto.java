package com.mulmeong.comment.read.dto.out;

import com.mulmeong.comment.read.entity.ShortsComment;
import com.mulmeong.comment.read.vo.ShortsCommentResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ShortsCommentResponseDto {

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

    public static ShortsCommentResponseDto toDto(ShortsComment shortsComment) {
        return ShortsCommentResponseDto.builder()
                .shortsUuid(shortsComment.getShortsUuid())
                .memberUuid(shortsComment.getMemberUuid())
                .commentUuid(shortsComment.getCommentUuid())
                .content(shortsComment.getContent())
                .createdAt(shortsComment.getCreatedAt())
                .updatedAt(shortsComment.getUpdatedAt())
                .likeCount(shortsComment.getLikeCount())
                .dislikeCount(shortsComment.getDislikeCount())
                .recommentCount(shortsComment.getRecommentCount())
                .isDeleted(shortsComment.isDeleted())
                .build();
    }

    public ShortsCommentResponseVo toVo() {
        return ShortsCommentResponseVo.builder()
                .shortsUuid(shortsUuid)
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