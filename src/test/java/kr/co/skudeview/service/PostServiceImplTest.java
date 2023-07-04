package kr.co.skudeview.service;


import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.PostCategory;
import kr.co.skudeview.domain.enums.Role;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import kr.co.skudeview.service.mapper.MemberMapper;
import kr.co.skudeview.service.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    PostMapper postMapper;
    @Test
    void createPost() {
        MemberRequestDto.CREATE readMember = MemberRequestDto.CREATE.builder()
                .email("member1@test.com")
                .password("1234")
                .name("테스트")
                .nickname("닉네임1")
                .telephone("01011112222")
                .address("서울시 성북구 정릉동")
                .birthDate(LocalDate.of(2000,01,01))
                .gender(String.valueOf(Gender.MALE))
                .role(String.valueOf(Role.USER))
                .build();

        Member member = memberMapper.toEntity(readMember);

        PostRequestDto.CREATE readPost = PostRequestDto.CREATE.builder()
                .memberEmail(readMember.getEmail())
                .title("테스트제목")
                .content("테스트본문")
                .postCategory(PostCategory.FREE)
                .likeCount(1)
                .viewCount(1)
                .build();
        Long postId = postService.createPost(readPost);
        Optional<Post> post = postRepository.findById(postId);
        assertThat(post.get().getId()).isEqualTo(postId);
    }

    @Test
    void findAll() {
        List<PostResponseDto.READ> all = postService.getAllPosts();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    void updatePost() {

        PostRequestDto.UPDATE build = PostRequestDto.UPDATE.builder()
                .postId(2L)
                .title("수정 제목")
                .content("수정 내용")
                .postCategory(PostCategory.NOTICE)
                .build();

        Long updateId = postService.updatePost(2L, build);
        Optional<Post> post = postRepository.findById(updateId);
        assertThat(post.get().getTitle()).isEqualTo("수정 제목");
        assertThat(post.get().getContent()).isEqualTo("수정 내용");
        assertThat(post.get().getPostCategory()).isEqualTo(PostCategory.NOTICE);

    }

    @Test
    void deletePost() {
        MemberRequestDto.CREATE readMember = MemberRequestDto.CREATE.builder()
                .email("member2@test.com")
                .password("12345")
                .name("테스트2")
                .nickname("닉네임22")
                .telephone("01022222222")
                .address("서울시 성북구 정릉동 266")
                .birthDate(LocalDate.of(2000,01,01))
                .gender(String.valueOf(Gender.MALE))
                .role(String.valueOf(Role.USER))
                .build();

        Member member = memberMapper.toEntity(readMember);

        PostRequestDto.CREATE readPost = PostRequestDto.CREATE.builder()
                .memberEmail(readMember.getEmail())
                .title("delete")
                .content("delete")
                .postCategory(PostCategory.FREE)
                .likeCount(1)
                .viewCount(1)
                .build();
        Long postId = postService.createPost(readPost);
        List<PostResponseDto.READ> all = postService.getAllPosts();
        assertThat(all.size()).isEqualTo(2);
        postService.deletePost(postId);
        List<PostResponseDto.READ> all2 = postService.getAllPosts();
        assertThat(all2.size()).isEqualTo(1);

    }

    @Test
    void findById() {
        PostResponseDto.READ post = postService.getPostDetail(1L);
        Optional<Post> result = postRepository.findById(1L);
        assertThat(result.get().getViewCount()).isEqualTo(3);
    }
}