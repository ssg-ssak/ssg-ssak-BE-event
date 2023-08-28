package ssgssak.ssgpointappevent.domain.event.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ssgssak.ssgpointappevent.domain.event.dto.CreateNewEventListDto;
import ssgssak.ssgpointappevent.domain.event.dto.ReadEventsDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateEventListDto;
import ssgssak.ssgpointappevent.domain.event.entity.EventList;
import ssgssak.ssgpointappevent.domain.event.infrastructure.EventListRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventListServiceImpl {
    private final EventListRepository eventListRepository;
    private final ModelMapper modelMapper;
    /*
    1. 새로운 이벤트 생성
    2. 이벤트 종료일 변경
    3. 진행 이벤트 최신순으로 받아오기
     */

    // 1. 새로운 이벤트 생성
    public void createEventList(CreateNewEventListDto eventListInfoDto) {
        log.info("eventListInfoDto : " + eventListInfoDto);
        EventList newEvent = modelMapper.map(eventListInfoDto, EventList.class);
        eventListRepository.save(newEvent);
    }

    // 2. 이벤트 종료일 변경
    public void changeEventListEndDate(UpdateEventListDto updateEventListDto, Long eventListId) {
        EventList eventList = eventListRepository.findById(eventListId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        eventList.changeEndDate(updateEventListDto.getEndDate());
        eventListRepository.save(eventList);
    }

    // 3. 진행 이벤트 최신순으로 받아오기
    public ReadEventsDto getLatestEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByStartDateDesc(false);
        log.info("events : " + events);
        ReadEventsDto eventsDto = ReadEventsDto.builder().events(events).build();
        return eventsDto;
    }

    // 4. 진행 이벤트 마감임박순으로 받아오기
    public ReadEventsDto getImminentEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByEndDateAsc(false);
        log.info("events : " + events);
        ReadEventsDto eventsDto = ReadEventsDto.builder().events(events).build();
        return eventsDto;
    }
}
