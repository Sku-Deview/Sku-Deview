package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.University;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.UniversityRepository;
import kr.co.skudeview.service.dto.request.UniversityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createUniversity(String email, UniversityDto universityDto) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        UniversityDto findDto = getUniversityMajor(universityDto);
        findMember.get().changeUnivMajor(findDto.getUnivName(), findDto.getMajor());
    }

    @Override
    @Transactional
    public UniversityDto getUniversityMajor(UniversityDto universityDto) {
        String major = universityDto.getMajor();
        String univName = universityDto.getUnivName();
        Optional<University> findUnivMajor = universityRepository.findByUnivNameAndMajor(univName, major);
        return  universityDto.builder()
                .univName(findUnivMajor.get().getUnivName())
                .major(findUnivMajor.get().getMajor())
                .build();
    }

    @Override
    @Transactional
    public void updateUniversity(String email, UniversityDto universityDto) {
        Optional<Member> findMember =  memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        UniversityDto findDto = getUniversityMajor(universityDto);

        findMember.get().changeUnivMajor(findDto.getUnivName(),findDto.getMajor());
    }

    @Override
    @Transactional
    public void deleteUniversity(String email) {
        Optional<Member> findMember =  memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        findMember.get().deleteUnivMajor();
    }


}
