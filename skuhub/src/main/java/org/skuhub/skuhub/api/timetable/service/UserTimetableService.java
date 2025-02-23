package org.skuhub.skuhub.api.timetable.service;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.UserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.response.UserTimetableResponse;
import org.skuhub.skuhub.model.timetable.UserTimetableEntity;
import org.skuhub.skuhub.repository.timetable.UserTimetableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTimetableService {

    private final UserTimetableRepository userTimetableRepository;

    public void createUserTimetable(UserTimetableRequest request) {
        UserTimetableEntity entity = UserTimetableEntity.builder()
                .userKey(request.getUserKey())
                .timetableId(request.getTimetableId())
                .personalGrade(request.getPersonalGrade())
                .personalSemester(request.getPersonalSemester())
                .createdAt(request.getCreatedAt())
                .score(request.getScore())
                .majorStatus(request.getMajorStatus())
                .build();

        userTimetableRepository.save(entity);
    }

    public List<UserTimetableResponse> getUserTimetable(Integer userKey) {
        List<UserTimetableEntity> timetables = userTimetableRepository.findByUserKey(userKey);
        return timetables.stream().map(UserTimetableResponse::fromEntity).collect(Collectors.toList());
    }
}
