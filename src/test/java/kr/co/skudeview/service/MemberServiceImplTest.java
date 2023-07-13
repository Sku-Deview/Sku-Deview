//package kr.co.skudeview.service;
//
//import kr.co.skudeview.domain.Member;
//import kr.co.skudeview.domain.enums.Gender;
//import kr.co.skudeview.domain.enums.Role;
//import kr.co.skudeview.repository.MemberRepository;
//import kr.co.skudeview.service.dto.request.MemberRequestDto;
//import kr.co.skudeview.service.dto.response.MemberResponseDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//class MemberServiceImplTest {
//
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    void createMember() throws Exception {
//        MemberRequestDto.CREATE member1 = MemberRequestDto.CREATE.builder()
//                .email("member1@test.com")
//                .password("1234")
//                .name("테스트")
//                .nickname("닉네임1")
//                .telephone("01011112222")
//                .address("서울시 성북구 정릉동")
//                .birthDate(LocalDate.of(2000,01,01))
//                .gender(String.valueOf(Gender.MALE))
//                .role(String.valueOf(Role.USER))
//                .build();
//
//        memberService.createMember(member1);
//
//        Optional<Member> member = memberRepository.findMemberByEmail("member1@test.com");
//
//        Assertions.assertEquals(member1.getName(), member.get().getName());
//    }
//
//    @Test
//    void getMemberByEmail() throws Exception {
//        MemberResponseDto.READ read = memberService.getMemberByEmail("member1@test.com");
//
//        Assertions.assertEquals(read.getName(), "테스트");
//    }
//
//    @Test
//    void getAllMembers() throws Exception {
//        List<MemberResponseDto.READ> list = memberService.getAllMembers();
//
//        Assertions.assertEquals(list.size(), 1);
//    }
//
//    @Test
//    void updateMember() throws Exception {
//
//        MemberRequestDto.UPDATE update = MemberRequestDto.UPDATE.builder()
//                .memberId(1L)
//                .password("0000")
//                .name("이름수정")
//                .nickname("닉네임수정")
//                .telephone("01000000000")
//                .address("서울시 도봉구 창동")
//                .birthDate(LocalDate.of(2000,01,01))
//                .gender(String.valueOf(Gender.MALE))
//                .role(String.valueOf(Role.USER))
//                .build();
//
//        memberService.updateMember(update);
//
//        Assertions.assertEquals(memberRepository.findMemberByTelephone(update.getTelephone()).get().getTelephone(), "01000000000");
//    }
//
//    @Test
//    void deleteMember() throws Exception {
//
//        memberService.deleteMember(1L);
//
//        Assertions.assertEquals(memberRepository.findById(1L),Optional.empty());
//    }
//}