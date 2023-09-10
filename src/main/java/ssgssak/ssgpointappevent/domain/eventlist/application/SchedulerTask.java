package ssgssak.ssgpointappevent.domain.eventlist.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;
import ssgssak.ssgpointappevent.domain.eventlist.infrastructure.EventListRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SchedulerTask {
    //현재일이 종료일을 넘어갈 경우 자동으로 종료처리(매일 00시에 메서드 자동 동작)
    //날짜 바뀔 때마다 Dday도 같이 update
    private final EventListRepository eventListRepository;

    @Scheduled(cron = "0 0 * * * *") // 매일 00시에 실행
    public void checkEventExpirationDateAndRemainDays() {
        LocalDate today = LocalDate.now();
        List<EventList> onGoingEvents = eventListRepository.findAllByIsOver(false);
        for(EventList event : onGoingEvents) { // 현재일과 마감일 비교해서 현재일이 마감일보다 크면 isOver를 true로 변경
            if(event.getEndDate().isBefore(today)) {
                event.updateIsOverToTrue();
            }
            else { // 디데이 업데이트
                event.updateRemainDays();
            }
        }
    }
}
