package com.mulmeong.event.comment;

import com.mulmeong.comment.read.entity.ShortsComment;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
public class ShortsCommentCreateEvent {

    private String shortsUuid;
    private String memberUuid;
    private String commentUuid;
    private boolean isDeleted;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ShortsComment toEntity() {
        return ShortsComment.builder()
                .shortsUuid(shortsUuid)
                .memberUuid(memberUuid)
                .commentUuid(commentUuid)
                .content(content)
                .isDeleted(isDeleted)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .likeCount(0L)
                .dislikeCount(0L)
                .recommentCount(0L)
                .build();
    }

}
