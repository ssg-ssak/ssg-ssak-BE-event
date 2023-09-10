package ssgssak.ssgpointappevent.domain.eventlist.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.eventlist.dto.CreateEventListInputDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.GetEventsOutputDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.CreateEventListOutputDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.UpdateEventListInputDto;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;
import ssgssak.ssgpointappevent.domain.eventlist.infrastructure.EventListRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventListServiceImpl {
    private final EventListRepository eventListRepository;
    private final ModelMapper modelMapper;
    /*
    1. 새로운 이벤트 생성
    2. 이벤트 종료일 변경
    3. 진행 이벤트 최신순으로 받아오기
     */

    // 1. 새로운 이벤트 생성
    public CreateEventListOutputDto createEventList(CreateEventListInputDto eventListInfoDto) {
        log.info("eventListInfoDto : " + eventListInfoDto);
        EventList newEvent = modelMapper.map(eventListInfoDto, EventList.class);
        newEvent.updateRemainDays(); // D-Day값 오늘 날짜에서 계산해서 넣어주기
        eventListRepository.save(newEvent);
        return CreateEventListOutputDto.builder()
                .eventListId(newEvent.getId())
                .build();
    }

    // 2. 이벤트 종료일 변경
    public void changeEventListEndDate(UpdateEventListInputDto updateEventListInputDto) {
        EventList eventList = eventListRepository.findById(updateEventListInputDto.getEventListId())
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        eventList.changeEndDate(updateEventListInputDto.getEndDate());
        eventList.updateRemainDays(); // D-Day값도 같이 업데이트
    }

    // 3. 진행 이벤트 최신순으로 받아오기
    @Transactional(readOnly = true)
    public GetEventsOutputDto getLatestEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByStartDateDesc(false);
        log.info("events : " + events);
        GetEventsOutputDto eventsDto = GetEventsOutputDto.builder().events(events).build();
        return eventsDto;
    }

    // 4. 진행 이벤트 마감임박순으로 받아오기
    @Transactional(readOnly = true)
    public GetEventsOutputDto getImminentEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByEndDateAsc(false);
        log.info("events : " + events);
        GetEventsOutputDto eventsDto = GetEventsOutputDto.builder().events(events).build();
        return eventsDto;
    }

    // 5. 종료된 이벤트 전체 조회하기
    @Transactional(readOnly = true)
    public GetEventsOutputDto getEndedEvents() {
        List<EventList> events = eventListRepository.findAllByIsOver(true);
        log.info("events : " + events);
        GetEventsOutputDto eventsDto = GetEventsOutputDto.builder().events(events).build();
        return eventsDto;
    }
}
