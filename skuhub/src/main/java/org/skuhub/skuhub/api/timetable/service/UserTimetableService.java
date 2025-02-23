package org.skuhub.skuhub.api.timetable.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.UpdateUserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.request.UserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.response.UserTimetableResponse;
import org.skuhub.skuhub.model.timetable.UserTimetableEntity;
import org.skuhub.skuhub.repository.timetable.UserTimetableRepository;
import org.springframework.stereotype.Service;



import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTimetableService {

    private final UserTimetableRepository userTimetableRepository;

    /**
     * 사용자 시간표 등록
     */
    public void createUserTimetable(UserTimetableRequest request) {
        UserTimetableEntity timetable = UserTimetableEntity.builder()
                .userKey(request.getUserKey())
                .timetableId(request.getTimetableId())
                .personalGrade(request.getPersonalGrade())
                .personalSemester(request.getPersonalSemester())
                .createdAt(request.getCreatedAt()) // DTO에서 Instant 타입 유지
                .score(request.getScore())
                .majorStatus(request.getMajorStatus())
                .build();

        userTimetableRepository.save(timetable);
    }

    /**
     * 사용자 시간표 조회
     */
    public List<UserTimetableResponse> getUserTimetable(Integer userKey) {
        return userTimetableRepository.findByUserKey(userKey).stream()
                .map(UserTimetableResponse::fromEntity)
                .toList();
    }

    /**
     * 사용자 시간표 수정
     */
    @Transactional
    public boolean updateUserTimetable(UpdateUserTimetableRequest request) {
        if (request.getPersonalKey() == null) {
            throw new IllegalArgumentException("Error: Personal Key is missing! Please provide a valid personal_key.");
        }

        Optional<UserTimetableEntity> timetableOptional =
                userTimetableRepository.findById(request.getPersonalKey());

        if (timetableOptional.isEmpty()) {
            throw new IllegalArgumentException("Error: Timetable not found for given personal_key: " + request.getPersonalKey());
        }

        UserTimetableEntity timetable = timetableOptional.get();

        // 요청 데이터를 엔터티에 적용
        if (request.getUserKey() != null) {
            timetable.setUserKey(request.getUserKey());
        }
        if (request.getTimetableId() != null) {
            timetable.setTimetableId(request.getTimetableId());
        }
        if (request.getPersonalGrade() != null) {
            timetable.setPersonalGrade(request.getPersonalGrade());
        }
        if (request.getPersonalSemester() != null) {
            timetable.setPersonalSemester(request.getPersonalSemester());
        }
        if (request.getScore() != null) {
            timetable.setScore(request.getScore());
        }
        if (request.getMajorStatus() != null) {
            timetable.setMajorStatus(request.getMajorStatus());
        }
        if (request.getCreatedAt() != null) {
            timetable.setCreatedAt(Instant.parse(request.getCreatedAt()));
        }

        userTimetableRepository.save(timetable);
        return true;
    }

    @Transactional
    public boolean deleteUserTimetable(Integer personalKey) {
        Optional<UserTimetableEntity> timetableOptional = userTimetableRepository.findById(personalKey);

        if (timetableOptional.isEmpty()) {
            return false; // 404: 시간표가 존재하지 않음
        }

        userTimetableRepository.delete(timetableOptional.get());
        return true;
    }


}
