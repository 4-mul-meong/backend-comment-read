package com.mulmeong.comment.read.dto.out;

import com.mulmeong.comment.read.entity.FeedRecomment;
import com.mulmeong.comment.read.vo.FeedRecommentResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class FeedRecommentResponseDto {

    private String commentUuid;
    private String memberUuid;
    private String recommentUuid;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long dislikeCount;

    public static FeedRecommentResponseDto toDto(FeedRecomment feedRecomment) {
        return FeedRecommentResponseDto.builder()
                .commentUuid(feedRecomment.getCommentUuid())
                .memberUuid(feedRecomment.getMemberUuid())
                .recommentUuid(feedRecomment.getRecommentUuid())
                .content(feedRecomment.getContent())
                .createdAt(feedRecomment.getCreatedAt())
                .updatedAt(feedRecomment.getUpdatedAt())
                .likeCount(feedRecomment.getLikeCount())
                .dislikeCount(feedRecomment.getDislikeCount())
                .build();
    }

    public FeedRecommentResponseVo toVo() {
        return FeedRecommentResponseVo.builder()
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
