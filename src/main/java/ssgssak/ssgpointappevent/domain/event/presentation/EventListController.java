package ssgssak.ssgpointappevent.domain.event.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.event.application.EventListServiceImpl;
import ssgssak.ssgpointappevent.domain.event.dto.CreateNewEventListDto;
import ssgssak.ssgpointappevent.domain.event.dto.ReadEventsDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateEventListDto;
import ssgssak.ssgpointappevent.domain.event.vo.CreateNewEventListVo;
import ssgssak.ssgpointappevent.domain.event.vo.GetEventsOutputVo;
import ssgssak.ssgpointappevent.domain.event.vo.UpdateEventListVo;

@RestController
@RequestMapping("/api/v1/event/event-list")
@RequiredArgsConstructor
@Slf4j
public class EventListController {
    private final EventListServiceImpl eventListService;
    private final ModelMapper modelMapper;

    /*
    아래는 어드민 API
    // todo: 관리자 인증 방식 정하기
    1. 새로운 이벤트 생성
    2. 이벤트 정보수정(종료일 변경)
    */

    // 1. 새로운 이벤트 생성
    @PostMapping("/admin/create")
    public void createEventList(@RequestBody CreateNewEventListVo createNewEventListVo){
        CreateNewEventListDto eventListInfoDto = modelMapper.map(createNewEventListVo, CreateNewEventListDto.class);
        eventListService.createEventList(eventListInfoDto);
    }

    // 2. 이벤트 정보수정(종료일 변경)
    @PutMapping("/admin/update/{eventListId}")
    public void updateEventList(@RequestBody UpdateEventListVo updateEventListVo, @PathVariable Long eventListId){
        UpdateEventListDto updateEventListDto = modelMapper.map(updateEventListVo, UpdateEventListDto.class);
        eventListService.changeEventListEndDate(updateEventListDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 진행 이벤트 조회하기(최신순)
    2. 진행 이벤트 조회하기(마감임박순)
     */

    // 1. 진행 이벤트 조회하기(최신순)
    @GetMapping("/latest-order")
    public ResponseEntity<GetEventsOutputVo> getLatestEvents() {
        ReadEventsDto readEventsDto = eventListService.getLatestEvents();
        GetEventsOutputVo getEventsOutputVo = modelMapper.map(readEventsDto, GetEventsOutputVo.class);
        return new ResponseEntity<>(getEventsOutputVo, HttpStatus.OK);
    }

    // 2. 진행 이벤트 조회하기(마감임박순)
    @GetMapping("/imminent-order")
    public ResponseEntity<GetEventsOutputVo> getImminentEvents() {
        ReadEventsDto readEventsDto = eventListService.getImminentEvents();
        log.info("readEventsDto : " + readEventsDto);
        GetEventsOutputVo getEventsOutputVo = modelMapper.map(readEventsDto, GetEventsOutputVo.class);
        log.info("getEventsOutputVo : " + getEventsOutputVo);
        return new ResponseEntity<>(getEventsOutputVo, HttpStatus.OK);
    }
}
