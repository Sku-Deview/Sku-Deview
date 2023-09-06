package kr.co.skudeview.domain.career.service;

import kr.co.skudeview.domain.career.dto.UniversityDto;

public interface UniversityService {

    void createUniversity(String email, UniversityDto universityDto);

    UniversityDto getUniversityMajor(UniversityDto universityDto);

    void updateUniversity(String email, UniversityDto universityDto);

    void deleteUniversity(String email);


}
