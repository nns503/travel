package backend.travel.domain.region.service;

import backend.travel.domain.cost.repository.CostRepository;
import backend.travel.domain.region.dto.request.CreateRegionRequest;
import backend.travel.domain.region.dto.request.UpdateRegionRequest;
import backend.travel.domain.region.dto.response.CreateRegionResponse;
import backend.travel.domain.region.dto.response.GetAllRegionResponse;
import backend.travel.domain.region.entity.Location;
import backend.travel.domain.region.entity.Region;
import backend.travel.domain.region.exception.NotFoundRegionException;
import backend.travel.domain.region.repository.RegionRepository;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.exception.NotFoundScheduleException;
import backend.travel.domain.schedule.exception.ScheduleOutOfRangeException;
import backend.travel.domain.schedule.exception.UnauthorizedScheduleException;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.exception.NotFoundUserException;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.global.excepiton.StartDateAfterEndDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RegionService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final RegionRepository regionRepository;
    private final CostRepository costRepository;

    @Transactional
    public CreateRegionResponse createRegion(Long userId, Long scheduleId, CreateRegionRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);

        validateAuthorizedSchedule(userId, schedule);

        LocalDateTime startDate = request.startDate();
        LocalDateTime endDate = request.endDate();

        validateStartDateBeforeEndDate(startDate, endDate);
        isWithinScheduleRange(startDate, endDate, schedule);

        Location location = Location.builder()
                .x(request.x())
                .y(request.y())
                .title(request.title())
                .address(request.address())
                .build();

        Region region = Region.builder()
                .memo(request.memo())
                .startDate(startDate)
                .endDate(endDate)
                .schedule(schedule)
                .location(location)
                .build();

        Region savedRegion = regionRepository.save(region);
        region.getSchedule().updateUpdateAt(LocalDateTime.now());

        return new CreateRegionResponse(savedRegion.getId());
    }


    @Transactional
    public void updateRegion(Long userId, Long scheduleId, Long regionId, UpdateRegionRequest request) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleteFalse(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        Region region = regionRepository.findById(regionId)
                .orElseThrow(NotFoundRegionException::new);
        validateAuthorizedSchedule(userId, region.getSchedule());

        LocalDateTime startDate = request.startDate();
        LocalDateTime endDate = request.endDate();

        validateStartDateBeforeEndDate(startDate, endDate);
        isWithinScheduleRange(startDate, endDate, schedule);

        Location location = Location.builder()
                .x(request.x())
                .y(request.y())
                .title(request.title())
                .address(request.address())
                .build();

        region.updateRegion(region.getMemo(), startDate, endDate, location);
        region.getSchedule().updateUpdateAt(LocalDateTime.now()); // 현재는 변경 감지가 되지 않아도 호출됨
    }

    @Transactional
    public void deleteRegion(Long userId, Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(NotFoundRegionException::new);
        validateAuthorizedSchedule(userId, region.getSchedule());
        costRepository.deleteAllByRegionId(regionId);
        regionRepository.delete(region);
    }

    public GetAllRegionResponse getAllRegion(Long scheduleId) {
        return new GetAllRegionResponse(regionRepository.findRegionList(scheduleId));
    }

    private void validateAuthorizedSchedule(Long userId, Schedule schedule) {
        if(!schedule.getUser().getId().equals(userId)){
            throw new UnauthorizedScheduleException();
        }
    }

    private void validateStartDateBeforeEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate.isAfter(endDate)){
            throw new StartDateAfterEndDateException();
        }
    }

    private void isWithinScheduleRange(LocalDateTime startDate, LocalDateTime endDate, Schedule schedule) {
        LocalDate scheduleStartDate = schedule.getStartDate();
        LocalDate scheduleEndDate = schedule.getEndDate();

        if (startDate.toLocalDate().isBefore(scheduleStartDate) || endDate.toLocalDate().isAfter(scheduleEndDate)) {
            throw new ScheduleOutOfRangeException();
        }
    }

}
