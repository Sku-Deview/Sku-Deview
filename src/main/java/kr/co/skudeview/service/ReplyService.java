package kr.co.skudeview.service;


import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import kr.co.skudeview.service.dto.response.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

    Long createReply(Long postId, ReplyRequestDto.CREATE create);

    List<ReplyResponseDto.READ> getAllReplies(Long postId);

    Long updateReply(Long replyId, ReplyRequestDto.UPDATE update);

    Long deleteReply(Long postId, Long replyId);
}
