package backend.travel.domain.schedule.service;

import backend.travel.domain.schedule.dto.ScheduleListDTO;
import backend.travel.domain.schedule.dto.request.CreateScheduleRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleDateTimeRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleMemoRequest;
import backend.travel.domain.schedule.dto.response.CreateScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetAllScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetMyAllScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetScheduleResponse;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.entity.ScheduleStatus;
import backend.travel.domain.schedule.exception.NotFoundScheduleException;
import backend.travel.global.excepiton.StartDateAfterEndDateException;
import backend.travel.domain.schedule.exception.UnauthorizedScheduleException;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.exception.NotFoundUserException;
import backend.travel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse createSchedule(Long userId, CreateScheduleRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();

        validateStartDateBeforeEndDate(startDate, endDate);

        Schedule schedule = Schedule.builder()
                .title(request.title())
                .memo("")
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .allCost(0L)
                .status(ScheduleStatus.PLANNED)
                .isPublic(true)
                .isDelete(false)
                .likeCount(0L)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(savedSchedule.getId());
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, UpdateScheduleDateTimeRequest request){
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        validateAuthorizedSchedule(userId, schedule);

        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();

        validateStartDateBeforeEndDate(startDate, endDate);

        schedule.updateDateTime(startDate, endDate);
    }

    @Transactional
    public void updateScheduleMemo(Long userId, Long scheduleId, UpdateScheduleMemoRequest request) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        validateAuthorizedSchedule(userId, schedule);

        schedule.updateMemo(request.memo());
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        validateAuthorizedSchedule(userId, schedule);

        schedule.delete();
    }

    public GetScheduleResponse getSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        isAuthorOfPrivateSchedule(userId, schedule);
        return GetScheduleResponse.of(schedule);
    }

    public GetAllScheduleResponse getAllSchedule(PageRequest pageable) {
        Slice<ScheduleListDTO> scheduleList = scheduleRepository.findScheduleList(pageable);
        return GetAllScheduleResponse.of(scheduleList);
    }

    public GetMyAllScheduleResponse getMyAllSchedule(Long userId, PageRequest pageable) {
        Slice<ScheduleListDTO> scheduleList = scheduleRepository.findMyScheduleList(userId, pageable);
        return GetMyAllScheduleResponse.of(scheduleList);
    }

    private void isAuthorOfPrivateSchedule(Long userId, Schedule schedule) {
        if(!schedule.getIsPublic() && !schedule.getUser().getId().equals(userId)){
            throw new UnauthorizedScheduleException();
        }
    }

    private void validateAuthorizedSchedule(Long userId, Schedule schedule) {
        if(!schedule.getUser().getId().equals(userId)){
            throw new UnauthorizedScheduleException();
        }
    }

    private void validateStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)){
            throw new StartDateAfterEndDateException();
        }
    }
}
