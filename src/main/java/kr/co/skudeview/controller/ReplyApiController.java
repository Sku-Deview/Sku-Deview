package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.ReplyService;
import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import kr.co.skudeview.service.dto.response.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ReplyApiController {

    private final ReplyService replyService;

    /**
     * 댓글 생성
     * @param postId 게시글 PK
     * @param create ReplyRequestDto.Create
     * @return replyId
     */
    @PostMapping("/reply/{postId}")
    public ResponseFormat<Long> createReply(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long postId,
                                            @RequestBody @Valid ReplyRequestDto.CREATE create) {
        Long replyId = replyService.createReply(userDetails.getUsername(),postId, create);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, replyId);
    }

    /**
     * 댓글 수정
     * @param @PathVariable postId
     * @param @PathVariable replyId
     * @param update ReplyRequestDto.UPDATE
     * @return updateReplyId
     */
    @PatchMapping("/reply/{postId}/{replyId}")
    public ResponseFormat<Long> updateReply(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long postId,
                                            @PathVariable Long replyId,
                                            @RequestBody @Valid ReplyRequestDto.UPDATE update) {
        Long updateReplyId = replyService.updateReply(userDetails.getUsername(),replyId, update);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, updateReplyId);
    }

    /**
     * 댓글 삭제
     * @param @PathVariable postId
     * @param @PathVariable replyId
     * @return  deleteReplyId
     */
    @DeleteMapping("/reply/{postId}/{replyId}")
    public ResponseFormat<Long> deleteReply(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long postId,
                                            @PathVariable Long replyId) {
        Long deleteReplyId = replyService.deleteReply(userDetails.getUsername(),postId, replyId);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteReplyId);
    }

    /**
     * 게시글 번호로 모든 댓글 조회
     * @param postId 게시글 PK
     * @return List<ReplyResponseDto.READ>
     */
    @GetMapping("/reply/{postId}")
    public ResponseFormat<List<ReplyResponseDto.READ>> getReplies(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getAllReplies(postId));
    }

    /**
     * Search Reply API - 검색 조건에 맞는 Reply 다중 조회
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<ReplyResponseDto.READ>
     */
    @GetMapping("/reply/search")
    public ResponseFormat<List<ReplyResponseDto.READ>> getSearchReplies(@RequestBody @Valid ReplyRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getSearchReplies(condition));
    }

}
