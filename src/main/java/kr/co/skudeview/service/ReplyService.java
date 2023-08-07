package kr.co.skudeview.service;


import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import kr.co.skudeview.service.dto.response.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

    Long createReply(String email,Long postId, ReplyRequestDto.CREATE create);

    List<ReplyResponseDto.READ> getAllReplies(Long postId);

    List<ReplyResponseDto.READ> getSearchReplies(ReplyRequestDto.CONDITION condition);

    Long updateReply(String email,Long replyId, ReplyRequestDto.UPDATE update);

    Long deleteReply(String email,Long postId, Long replyId);

}
