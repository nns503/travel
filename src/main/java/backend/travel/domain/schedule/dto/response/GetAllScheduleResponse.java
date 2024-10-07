package backend.travel.domain.schedule.dto.response;

import backend.travel.domain.schedule.dto.ScheduleListDTO;
import backend.travel.global.common.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetAllScheduleResponse(
        List<ScheduleListDTO> scheduleList,
        SliceInfo sliceInfo
) {
    public static GetAllScheduleResponse of(Slice<ScheduleListDTO> scheduleList){
        return new GetAllScheduleResponse(scheduleList.getContent(), SliceInfo.of(scheduleList));
    }
}
