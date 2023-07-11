package kr.co.skudeview.controller;

import kr.co.skudeview.service.ReplyService;
import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import kr.co.skudeview.service.dto.response.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReplyApiController {

    private final ReplyService replyService;

    /**
     * 댓글 생성
     * @param postId 게시글 PK
     * @param create ReplyRequestDto.Create
     * @return replyId
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reply/{postId}")
    public Long createReply(@PathVariable Long postId, @RequestBody ReplyRequestDto.CREATE create) {
        Long replyId = replyService.createReply(postId, create);
        return replyId;
    }

    /**
     * 게시글 번호로 모든 댓글 조회
     * @param postId 게시글 PK
     * @return List<ReplyResponseDto.READ>
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reply/{postId}")
    public List<ReplyResponseDto.READ> getReplies(@PathVariable Long postId) {
        List<ReplyResponseDto.READ> allReplies = replyService.getAllReplies(postId);
        return allReplies;
    }

    /**
     * 댓글 수정
     * @param @PathVariable postId
     * @param @PathVariable replyId
     * @param update ReplyRequestDto.UPDATE
     * @return updateReplyId
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reply/{postId}/{replyId}")
    public Long updateReply(@PathVariable Long postId, @PathVariable Long replyId, @RequestBody ReplyRequestDto.UPDATE update) {
        Long updateReplyId = replyService.updateReply(replyId, update);
        return updateReplyId;
    }

    /**
     * 댓글 삭제
     * @param @PathVariable postId
     * @param @PathVariable replyId
     * @return  deleteReplyId
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/reply/{postId}/{replyId}")
    public Long deleteReply(@PathVariable Long postId, @PathVariable Long replyId) {
        Long deleteReplyId = replyService.deleteReply(postId, replyId);
        return deleteReplyId;
    }
}
