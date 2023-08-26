package ssgssak.ssgpointappevent.domain.event.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ssgssak.ssgpointappevent.domain.event.dto.CreateNewEventListDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateEventListDto;
import ssgssak.ssgpointappevent.domain.event.entity.EventList;
import ssgssak.ssgpointappevent.domain.event.infrastructure.EventListRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventListServiceImpl {
    private final EventListRepository eventListRepository;
    private final ModelMapper modelMapper;

    private static final DateTimeFormatter stringToDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /*
    1. 새로운 이벤트 생성
    2. 이벤트 종료일 변경
     */

    // 1. 새로운 이벤트 생성
    public void createEventList(CreateNewEventListDto eventListInfoDto) {
        log.info("eventListInfoDto : " + eventListInfoDto);
        EventList newEvent = EventList.builder()
                .startDate(LocalDate.parse(eventListInfoDto.getStartDate(), stringToDate))
                .endDate(LocalDate.parse(eventListInfoDto.getEndDate(), stringToDate))
                .isOver(eventListInfoDto.getIsOver())
                .eventType(eventListInfoDto.getEventType())
                .build();
        eventListRepository.save(newEvent);
    }

    // 2. 이벤트 종료일 변경
    public void changeEventListEndDate(UpdateEventListDto updateEventListDto, Long eventListId) {
        EventList eventList = eventListRepository.findById(eventListId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        LocalDate newEndDate = LocalDate.parse(updateEventListDto.getEndDate(), stringToDate);
        eventList.changeEndDate(newEndDate);
        eventListRepository.save(eventList);
    }
}
