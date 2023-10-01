package kr.co.skudeview.domain.post.service;

import kr.co.skudeview.domain.file.entity.File;
import kr.co.skudeview.domain.file.entity.FileFormat;
import kr.co.skudeview.domain.file.repository.FileRepository;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.member.entity.MemberLikePost;
import kr.co.skudeview.domain.member.repository.MemberLikePostRepository;
import kr.co.skudeview.domain.member.repository.MemberRepository;
import kr.co.skudeview.domain.post.dto.PostRequestDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.domain.post.repository.PostRepository;
import kr.co.skudeview.domain.post.repository.PostSearchRepository;
import kr.co.skudeview.global.common.PostCategory;
import kr.co.skudeview.global.exception.DuplicatedException;
import kr.co.skudeview.global.exception.NotFoundException;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final PostSearchRepository postSearchRepository;

    private final MemberLikePostRepository memberLikePostRepository;

    private final RedisTemplate<String, Object> redisTemplate; // RedisTemplate 주입

    private final FileRepository fileRepository;


//    @Override
//    @Transactional
//    public Long createPost(String email, PostRequestDto.CREATE create) throws IOException {
//        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);
//
//        isMember(findMember);
//
//        isPostCategory(String.valueOf(create.getPostCategory()));
//
//        if (create.getPostFile() == null) {
//            //첨부 파일 없음
//            Post post = toEntity(create, findMember.get());
//            postRepository.save(post);
//            return post.getId();
//        } else {
//            //첨부 파일 있음
//            MultipartFile postFile = create.getPostFile();
//            String originalFilename = postFile.getOriginalFilename();
//            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
//            String savePath = "C:/deview_img/" + storedFileName;
//            postFile.transferTo(new File(savePath)); //IO Exception
//            Post postEntity = toEntity(create, findMember.get());
//            Long savedId = postRepository.save(postEntity).getId();
//            Post post = postRepository.findById(savedId).get();
//
//            PostFile postFileEntity = toPostFileEntity(originalFilename, storedFileName, post);
//            postFileRepository.save(postFileEntity);
//            return post.getId();
//        }
//    }

    @Override
    @Transactional
    public Long createPost(String email, PostRequestDto.CREATE create, List<MultipartFile> files) throws IOException {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(findMember);

        isPostCategory(String.valueOf(create.getPostCategory()));

        /*게시글 entity 생성*/
        Post post = toEntity(create, findMember.get());
        postRepository.save(post);


        if (files != null && !files.isEmpty()) {
            /* 지원하지 않는 확장자 파일 제거 */
            List<MultipartFile> validatedFiles = filesValidation(files);

            /* 걸러진 파일들 업로드 */
            filesUpload(validatedFiles, post.getId());

            /* 유효성 검증을 끝낸 파일들을 하나씩 꺼냄. */
            for (MultipartFile validatedFile : validatedFiles) {
                /* File Entity 생성 후 저장 */
                File file = new kr.co.skudeview.domain.file.entity.File(validatedFile, post);
                fileRepository.save(file);
            }
        }
        log.info("post.getId() = {}",post.getId());
        return post.getId();
    }

    @Override
    public Page<PostResponseDto.READ> searchPostWithPaging(Pageable pageable, String postCategory, String searchType, String searchText) {
        return postSearchRepository.findWithPaging(pageable, postCategory, searchType, searchText).map(this::toReadDto);
    }

    @Override
    public List<PostResponseDto.READ> searchNoticePost() {
        return postSearchRepository.findNoticePost().stream().map(this::toReadDto).toList();
    }

    @Override
    public PostResponseDto.READ addPostLikeByLoginNickname(Long postId, String loginNickname) {
        Optional<Member> loginMember = memberRepository.findMemberByNicknameAndDeleteAtFalse(loginNickname);
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPostLikeDuplicated(postId, loginMember.get().getId());

        MemberLikePost create = MemberLikePost.builder()
                .member(loginMember.get())
                .post(post.get())
                .build();

        memberLikePostRepository.save(create);

        post.get().addLikeCount();

        postRepository.save(post.get());

        return toReadDto(postRepository.findPostByIdAndDeleteAtFalse(postId).get());
    }

    @Override
    public List<PostResponseDto.READ> getPostsByMemberNickname(String memberNickname) {
        return postSearchRepository.findPostsByMemberNickname(memberNickname).stream().map(this::toReadDto).toList();
    }

    @Override
    @Transactional
    public Long updatePost(String email, Long postId, PostRequestDto.UPDATE update) {
        //email을 통한 인가 추가 예정(프론트에서 진행해야 할 것으로 생각 중 논의 예정)

        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);

        post.get().updatePost(update.getTitle(), update.getContent(), update.getPostCategory());

        return post.get().getId();
    }

    @Override
    @Transactional
    public Long deletePost(Long postId) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);
        /*해당 boardId를 가지고 있는 file 먼저 삭제*/
        fileRepository.deleteAllByPostId(postId);

        post.get().changeDeleteAt();

        postRepository.save(post.get());

        return post.get().getId();
    }

    /**
     * 게시글 상세정보 조회
     */
    @Override
    @Transactional
    public PostResponseDto.READ getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        /*File*/
        List<File> fileList = fileRepository.findAllByPostId(postId);
        List<FileFormat> fileFormatList = new ArrayList<>();

        /*파일이 존재한다면*/
        if (fileList != null) {
            for (File file : fileList) {
                FileFormat fileFormat = new FileFormat(file);
                fileFormatList.add(fileFormat);
            }
        }
        isPost(post);

        updateCntToRedis(postId, "views");

        PostResponseDto.READ dto = toDetailReadDto(post.get(), fileFormatList);

        return dto;
    }

    /*
    게시글 상세조회 요청 시, 해당 postId에 해당하는 viewCnt를 +1 해준 값을 Redis에 저장
     */
    @Transactional
    @Override
    public void updateCntToRedis(Long postId, String hashKey) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

        String key = "postId::" + postId;

        if (hashOperations.get(key, hashKey) == null) {
            if (hashKey.equals("views")) {
                hashOperations.put(key, hashKey, postRepository.findPostByIdAndDeleteAtFalse(postId).get().getViewCount());
            }
            hashOperations.increment(key, hashKey, 1L);
            System.out.println("hashOperations.get is null ---- " + hashOperations.get(key, hashKey));
        } else {
            hashOperations.increment(key, hashKey, 1L);
            System.out.println("hashOperations.get is not null ---- " + hashOperations.get(key, hashKey));
        }
    }

    /*
    Redis에 기록된 정보들을 DB에 업데이트를 진행하면서 데이터의 일관성을 유지하고, Redis의 저장된 정보들을 초기화
    Spring Scheduled를 사용하여 일정 시간마다 실행이 되도록 설정
     */
    @Transactional
    @Scheduled(fixedDelay = 1000L * 180L) // 18초에서 -> 180 초로 변경
    @Override
    public void deleteCntToRedis() {
        String viewKey = "views";
        Set<String> redisKey = redisTemplate.keys("postId*");
        Iterator<String> it = redisKey.iterator();

        while (it.hasNext()) {
            String data = it.next();
            Long postId = Long.parseLong(data.split("::")[1]);

            if (redisTemplate.opsForHash().get(data, viewKey) == null) {
                break;
            } else {
                Long viewCnt = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(data, viewKey)));
                addViewCntFromRedis(postId, viewCnt);
                redisTemplate.opsForHash().delete(data, viewKey);
            }

        }
        System.out.println("Update Complete From Redis");
    }

    private void addViewCntFromRedis(Long postId, Long viewCnt) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

//        isPost(post);

        if (!post.isEmpty()) {
            post.get().addViewCount(viewCnt);

            postRepository.save(post.get());
        }

    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isPostCategory(String category) {
        PostCategory.of(category);
    }

    private void isPostLikeDuplicated(Long postId, Long memberId) {
        if (memberLikePostRepository.existsMemberLikePostByPost_IdAndMember_IdAndDeleteAtFalse(postId, memberId)) {
            throw new DuplicatedException(ResponseStatus.FAIL_POST_LIKE_MEMBER_DUPLICATED);
        }
    }

    private PostResponseDto.READ toDetailReadDto(Post post, List<FileFormat> fileFormatList) {
        return PostResponseDto.READ.builder()
                .postId(post.getId())
                .memberEmail(post.getMember().getEmail())
                .memberNickname(post.getMember().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .fileFormat(fileFormatList)
                .viewCount(post.getViewCount())
                .likeCount(postRepository.countLikesByPostId(post.getId()))
                .replyCount(postRepository.countRepliesByPostId(post.getId()))
                .regDate(post.getRegDate())
                .build();
    }

    private PostResponseDto.READ toReadDto(Post post) {
        return PostResponseDto.READ.builder()
                .postId(post.getId())
                .memberEmail(post.getMember().getEmail())
                .memberNickname(post.getMember().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .viewCount(post.getViewCount())
                .likeCount(postRepository.countLikesByPostId(post.getId()))
                .replyCount(postRepository.countRepliesByPostId(post.getId()))
                .regDate(post.getRegDate())
                .build();
    }

    private Post toEntity(PostRequestDto.CREATE create, Member member) {

        return Post.builder()
                .member(member)
                .title(create.getTitle())
                .content(create.getContent())
                .likeCount(0)
                .viewCount(0)
                .postCategory(create.getPostCategory())
                .build();
    }

    /*파일의 유효성 검증*/
    private List<MultipartFile> filesValidation(List<MultipartFile> files) throws IOException {
        /*접근 거부 파일 확장자명*/
        String[] accessDeniedFileExtension = {"exe", "zip"};
        /*접근 거부 파일 컨텐츠 타입*/
        String[] accessDeniedFileContentType = {"application/x-msdos-program", "application/zip"};


        ArrayList<MultipartFile> validatedFiles = new ArrayList<>();


        for (MultipartFile file : files) {
            /*원본 파일 이름*/
            String originalFileName = file.getOriginalFilename();
            /*파일의 확장자명*/
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            /*파일의 컨텐츠타입*/
            String fileContentType = file.getContentType();

            /*accessDeniedFileExtension, accessDeniedFileContentType -> 업로드 불가*/
            if (Arrays.asList(accessDeniedFileExtension).contains(fileExtension) ||
                    Arrays.asList(accessDeniedFileContentType).contains(fileContentType)) {
                log.warn(fileExtension + "(" + fileContentType + ") 파일은 지원하지 않는 확장자입니다.");
            } else {/*업로드 가능*/
                validatedFiles.add(file);
            }


        }
        return validatedFiles;
    }

    /*파일 업로드 메소드*/
    private void filesUpload(List<MultipartFile> files, Long postId) throws IOException {

        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        for (MultipartFile file : files) {
            /*업로드 경로*/
            java.io.File uploadPath = new java.io.File(rootDir + "/media/" + postId + "_" + file.getOriginalFilename());
            /*업로드*/
            file.transferTo(uploadPath);
        }
    }
}

