package com.mulmeong.comment.read.presentation;

import com.mulmeong.comment.read.application.FeedCommentService;
import com.mulmeong.comment.read.common.response.BaseResponse;
import com.mulmeong.comment.read.common.utils.CursorPage;
import com.mulmeong.comment.read.dto.out.FeedCommentResponseDto;
import com.mulmeong.comment.read.vo.FeedCommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/feeds")
@CrossOrigin(origins = "*")
public class FeedCommentController {

    private final FeedCommentService feedCommentService;

    @GetMapping("/{feedUuid}/comments")
    @Operation(summary = "피드 댓글 최신순 , 추천순 조회", tags = {"Feed Comment Service"})
    public BaseResponse<CursorPage<FeedCommentResponseVo>> getFeedCommentsByPage(
            @PathVariable String feedUuid,
            @Parameter(
                    description = "정렬 기준",
                    schema = @Schema(allowableValues = {"latest", "likes"})
            )
            @RequestParam(defaultValue = "latest", value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "nextCursor", required = false) String lastId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNo", required = false) Integer pageNo
    ) {
        CursorPage<FeedCommentResponseDto> cursorPage = feedCommentService.getFeedCommentsByPage(
                    feedUuid, sortBy, lastId, pageSize, pageNo);

        return new BaseResponse<>(
                CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                        .map(FeedCommentResponseDto::toVo).toList())
        );

    }
}
