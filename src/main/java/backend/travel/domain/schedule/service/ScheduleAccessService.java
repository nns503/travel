package backend.travel.domain.schedule.service;

import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.entity.ScheduleAccess;
import backend.travel.domain.schedule.exception.ExistsScheduleAccessUserException;
import backend.travel.domain.schedule.exception.NotFoundScheduleAccessException;
import backend.travel.domain.schedule.exception.NotFoundScheduleException;
import backend.travel.domain.schedule.exception.UnauthorizedScheduleException;
import backend.travel.domain.schedule.repository.ScheduleAccessRepository;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.exception.NotFoundUserException;
import backend.travel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleAccessService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleAccessRepository scheduleAccessRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addAccess(Long userId, Long addUserId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);

        validateAuthorizedSchedule(userId, schedule);

        User addUser = userRepository.findById(addUserId)
                .orElseThrow(NotFoundUserException::new);

        if(scheduleAccessRepository.existsScheduleAccessByUserIdAndScheduleId(addUserId, scheduleId)){
            throw new ExistsScheduleAccessUserException();
        }

        ScheduleAccess scheduleAccess = ScheduleAccess.builder()
                .user(addUser)
                .schedule(schedule)
                .build();

        scheduleAccessRepository.save(scheduleAccess);
    }

    @Transactional
    public void removeAccess(Long userId, Long removeUserId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);

        validateAuthorizedSchedule(userId, schedule);

        ScheduleAccess scheduleAccess = scheduleAccessRepository.findScheduleAccessByUserIdAndScheduleId(removeUserId, scheduleId)
                .orElseThrow(NotFoundScheduleAccessException::new);

        scheduleAccessRepository.delete(scheduleAccess);

    }

    private void validateAuthorizedSchedule(Long userId, Schedule schedule) {
        if(!schedule.getUser().getId().equals(userId)) {
            throw new UnauthorizedScheduleException();
        }
    }
}
