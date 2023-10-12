package kr.co.skudeview.domain.follow.service;

import kr.co.skudeview.domain.follow.dto.FollowRequestDto;
import kr.co.skudeview.domain.follow.dto.FollowResponseDto;
import kr.co.skudeview.domain.follow.entity.Follow;
import kr.co.skudeview.domain.follow.repository.FollowRepository;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.member.repository.MemberRepository;
import kr.co.skudeview.global.exception.DuplicatedException;
import kr.co.skudeview.global.exception.NotFoundException;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    @Override
    public FollowResponseDto.READ createFollow(String email, FollowRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        isMember(findMember);

        isFollowDuplicated(create.getFromMemberNickname(), create.getToMemberNickname());

        Follow follow = toEntity(create);

        followRepository.save(follow);

        return toReadDto(follow);
    }

    @Override
    public List<FollowResponseDto.READ> getFollowings(String fromMemberNickname) {
        return followRepository.findFollowsByFromMember_NicknameAndAndDeleteAtFalse(fromMemberNickname).stream().map(this::toReadDto).toList();
    }

    @Override
    public List<FollowResponseDto.READ> getFollowers(String toMemberNickname) {
        return followRepository.findFollowsByToMember_NicknameAndAndDeleteAtFalse(toMemberNickname).stream().map(this::toReadDto).toList();
    }

    @Override
    public Long deleteFollow(String fromMemberNickname, String toMemberNickname) {
        Optional<Follow> follow = followRepository.findFollowByFromMember_NicknameAndToMember_NicknameAndDeleteAtFalse(fromMemberNickname, toMemberNickname);

        isFollow(follow);

        followRepository.delete(follow.get());

        return follow.get().getId();
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isFollow(Optional<Follow> follow) {
        if (follow.isEmpty()) {
            throw new DuplicatedException(ResponseStatus.FAIL_FOLLOW_NOT_FOUND);
        }
    }

    private void isFollowDuplicated(String fromMemberNickname, String toMemberNickname) {
        if (followRepository.existsByFromMember_NicknameAndToMember_NicknameAndDeleteAtFalse(fromMemberNickname, toMemberNickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_FOLLOW_DUPLICATED);
        }
    }

    private Follow toEntity(FollowRequestDto.CREATE create) {
        Optional<Member> fromMember = memberRepository.findMemberByNicknameAndDeleteAtFalse(create.getFromMemberNickname());
        Optional<Member> toMember = memberRepository.findMemberByNicknameAndDeleteAtFalse(create.getToMemberNickname());

        isMember(fromMember);
        isMember(toMember);

        return Follow.builder()
                .fromMember(fromMember.get())
                .toMember(toMember.get())
                .build();
    }

    private FollowResponseDto.READ toReadDto(Follow follow) {
        return FollowResponseDto.READ.builder()
                .fromMember(follow.getFromMember())
                .toMember(follow.getToMember())
                .followingCnt(followRepository.countByFromMember_NicknameAndAndDeleteAtFalse(follow.getFromMember().getNickname()))
                .followerCnt(followRepository.countByToMember_NicknameAndAndDeleteAtFalse(follow.getToMember().getNickname()))
                .build();
    }

}
