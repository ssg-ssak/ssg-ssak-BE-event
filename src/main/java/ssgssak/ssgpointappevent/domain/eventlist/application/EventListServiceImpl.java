package ssgssak.ssgpointappevent.domain.eventlist.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.eventlist.dto.CreateEventListDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.ReadEventsDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.UpdateEventListDto;
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
    public Long createEventList(CreateEventListDto eventListInfoDto) {
        log.info("eventListInfoDto : " + eventListInfoDto);
        EventList newEvent = modelMapper.map(eventListInfoDto, EventList.class);
        eventListRepository.save(newEvent);
        return newEvent.getId();
    }

    // 2. 이벤트 종료일 변경
    public void changeEventListEndDate(UpdateEventListDto updateEventListDto, Long eventListId) {
        EventList eventList = eventListRepository.findById(eventListId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        eventList.changeEndDate(updateEventListDto.getEndDate());
    }

    // 3. 진행 이벤트 최신순으로 받아오기
    @Transactional(readOnly = true)
    public ReadEventsDto getLatestEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByStartDateDesc(false);
        log.info("events : " + events);
        ReadEventsDto eventsDto = ReadEventsDto.builder().events(events).build();
        return eventsDto;
    }

    // 4. 진행 이벤트 마감임박순으로 받아오기
    @Transactional(readOnly = true)
    public ReadEventsDto getImminentEvents() {
        List<EventList> events = eventListRepository.findAllByIsOverOrderByEndDateAsc(false);
        log.info("events : " + events);
        ReadEventsDto eventsDto = ReadEventsDto.builder().events(events).build();
        return eventsDto;
    }
}
