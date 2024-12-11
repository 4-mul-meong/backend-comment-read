package com.mulmeong.comment.read.dto.out;

import com.mulmeong.comment.read.entity.ShortsRecomment;
import com.mulmeong.comment.read.vo.ShortsRecommentResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ShortsRecommentResponseDto {

    private String commentUuid;
    private String memberUuid;
    private String recommentUuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long dislikeCount;

    public static ShortsRecommentResponseDto toDto(ShortsRecomment shortsRecomment) {
        return ShortsRecommentResponseDto.builder()
                .commentUuid(shortsRecomment.getCommentUuid())
                .memberUuid(shortsRecomment.getMemberUuid())
                .recommentUuid(shortsRecomment.getRecommentUuid())
                .content(shortsRecomment.getContent())
                .createdAt(shortsRecomment.getCreatedAt())
                .updatedAt(shortsRecomment.getUpdatedAt())
                .likeCount(shortsRecomment.getLikeCount())
                .dislikeCount(shortsRecomment.getDislikeCount())
                .build();
    }

    public ShortsRecommentResponseVo toVo() {
        return ShortsRecommentResponseVo.builder()
                .commentUuid(commentUuid)
                .memberUuid(memberUuid)
                .recommentUuid(recommentUuid)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .build();
    }
}
