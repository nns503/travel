package backend.travel.domain.like.service;

import backend.travel.domain.like.entity.Like;
import backend.travel.domain.like.exception.ScheduleAlreadyLikedException;
import backend.travel.domain.like.repository.LikeRepository;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.exception.NotFoundScheduleException;
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
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void likeSchedule(Long userId, Long scheduleId){
        if(likeRepository.existsByUserIdAndScheduleId(userId, scheduleId)){
            throw new ScheduleAlreadyLikedException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
        Like like = Like.builder()
                .user(user)
                .schedule(schedule)
                .build();

        scheduleRepository.updateLikeCount(schedule.getId());
        likeRepository.save(like);
    }


}
