package kr.co.skudeview.dummy;

import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.PostCategory;
import kr.co.skudeview.repository.*;
import kr.co.skudeview.service.*;
import kr.co.skudeview.service.dto.request.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class DummyDataTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyService replyService;
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    SkillService skillService;
    @Autowired
    SkillRepository skillRepository;

    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MessageService messageService;
    @Autowired
    MessageRepository messageRepository;

    @Test
    void createSkill() {
        SkillRequestDto.CREATE create1 = SkillRequestDto.CREATE.builder()
                .name("Java")
                .build();

        SkillRequestDto.CREATE create2 = SkillRequestDto.CREATE.builder()
                .name("Python")
                .build();

        SkillRequestDto.CREATE create3 = SkillRequestDto.CREATE.builder()
                .name("JavaScript")
                .build();

        SkillRequestDto.CREATE create4 = SkillRequestDto.CREATE.builder()
                .name("SpringBoot")
                .build();

        SkillRequestDto.CREATE create5 = SkillRequestDto.CREATE.builder()
                .name("React")
                .build();

        SkillRequestDto.CREATE create6 = SkillRequestDto.CREATE.builder()
                .name("C++")
                .build();

        SkillRequestDto.CREATE create7 = SkillRequestDto.CREATE.builder()
                .name("C#")
                .build();

        SkillRequestDto.CREATE create8 = SkillRequestDto.CREATE.builder()
                .name("Flutter")
                .build();

        SkillRequestDto.CREATE create9 = SkillRequestDto.CREATE.builder()
                .name("Spring")
                .build();

        SkillRequestDto.CREATE create10 = SkillRequestDto.CREATE.builder()
                .name("Node.js")
                .build();

        skillService.createSkill(create1);
        skillService.createSkill(create2);
        skillService.createSkill(create3);
        skillService.createSkill(create4);
        skillService.createSkill(create5);
        skillService.createSkill(create6);
        skillService.createSkill(create7);
        skillService.createSkill(create8);
        skillService.createSkill(create9);
        skillService.createSkill(create10);
    }

    @Test
    void createMember() {
        for (int i = 1; i < 5; i++) {
            ArrayList<String> skill = new ArrayList<>();
            skill.add("Java");
            skill.add("SpringBoot");

            MemberRequestDto.CREATE create = MemberRequestDto.CREATE.builder()
                    .email("test" + i + "@test.com")
                    .password("password" + i)
                    .name("이름" + i)
                    .nickname("닉네임" + i)
                    .telephone("0100000" + i)
                    .address("서울시 아무구" + i)
                    .birthDate(LocalDate.of(i % 15 + 1980, i % 11 + 1, i % 20 + 1))
                    .gender("MALE")
                    .role("ADMIN")
                    .skillName(skill)
                    .build();

            memberService.createMember(create);
        }

        for (int i = 5; i < 10; i++) {
            ArrayList<String> skill = new ArrayList<>();
            skill.add("Python");
            skill.add("C++");

            MemberRequestDto.CREATE create = MemberRequestDto.CREATE.builder()
                    .email("test" + i + "@test.com")
                    .password("password" + i)
                    .name("이름" + i)
                    .nickname("닉네임" + i)
                    .telephone("0100000" + i)
                    .address("서울시 아무구" + i)
                    .birthDate(LocalDate.of(i % 15 + 1980, i % 11 + 1, i % 20 + 1))
                    .gender("FEMALE")
                    .role("USER")
                    .skillName(skill)
                    .build();

            memberService.createMember(create);
        }

        for (int i = 10; i < 15; i++) {
            ArrayList<String> skill = new ArrayList<>();
            skill.add("JavaScript");
            skill.add("React");

            MemberRequestDto.CREATE create = MemberRequestDto.CREATE.builder()
                    .email("test" + i + "@test.com")
                    .password("password" + i)
                    .name("이름" + i)
                    .nickname("닉네임" + i)
                    .telephone("0100000" + i)
                    .address("서울시 아무구" + i)
                    .birthDate(LocalDate.of(i % 15 + 1980, i % 11 + 1, i % 20 + 1))
                    .gender("MALE")
                    .role("USER")
                    .skillName(skill)
                    .build();

            memberService.createMember(create);
        }

        for (int i = 15; i < 20; i++) {
            ArrayList<String> skill = new ArrayList<>();
            skill.add("Flutter");
            skill.add("Java");
            skill.add("React");

            MemberRequestDto.CREATE create = MemberRequestDto.CREATE.builder()
                    .email("test" + i + "@test.com")
                    .password("password" + i)
                    .name("이름" + i)
                    .nickname("닉네임" + i)
                    .telephone("0100000" + i)
                    .address("서울시 아무구" + i)
                    .birthDate(LocalDate.of(i % 15 + 1980, i % 11 + 1, i % 20 + 1))
                    .gender("FEMALE")
                    .role("USER")
                    .skillName(skill)
                    .build();

            memberService.createMember(create);
        }
    }

    @Test
    void createPost() {
        for (int i = 1; i < 100; i++) {
            PostRequestDto.CREATE create = PostRequestDto.CREATE.builder()
                    .memberEmail("test" + (i % 10 + 1) + "@test.com")
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .postCategory(PostCategory.FREE)
                    .likeCount(1)
                    .viewCount(1)
                    .build();

            postService.createPost(create);
        }

        for (int i = 100; i < 200; i++) {
            PostRequestDto.CREATE create = PostRequestDto.CREATE.builder()
                    .memberEmail("test" + (i % 10 + 7) + "@test.com")
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .postCategory(PostCategory.QNA)
                    .likeCount(1)
                    .viewCount(1)
                    .build();

            postService.createPost(create);
        }

    }

    @Test
    void createReply() {
        for (int i = 1; i < 300; i++) {
            ReplyRequestDto.CREATE create = ReplyRequestDto.CREATE.builder()
                    .postId((long) (i % 199 + 1))
                    .memberEmail("test" + (i % 19 + 1) + "@test.com")
                    .content("댓글 내용..." + i)
                    .likeCount(1)
                    .build();

            replyService.createReply((long) (i % 199 + 1), create);
        }
    }

    @Test
    void createMessage() {
        for (int i = 1; i < 300; i++) {
            MessageRequestDto.CREATE build = MessageRequestDto.CREATE.builder()
                    .title("호랑이에게")
                    .content("안녕호랑아")
                    .senderName("토끼")
                    .receiverName("호랑이")
                    .build();
            messageService.createMessage(build);
        }


        for (int i = 1; i < 300; i++) {
            MessageRequestDto.CREATE build2 = MessageRequestDto.CREATE.builder()
                    .title("토끼에게")
                    .content("안녕토끼야")
                    .senderName("호랑이")
                    .receiverName("토끼")
                    .build();
            messageService.createMessage(build2);
        }


    }
}
