package org.skuhub.skuhub.api.timetable.service;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.model.timetable.PersonalTimetableEntity;
import org.skuhub.skuhub.repository.timetable.PersonalTimetableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalTimetableService {

    private final PersonalTimetableRepository personalTimetableRepository;

    public List<PersonalTimetableEntity> getUserTimetable(Integer userKey) {
        return personalTimetableRepository.findByUserKey(userKey);
    }
}
