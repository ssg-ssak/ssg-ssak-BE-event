package ssgssak.ssgpointappevent.domain.eventlist.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.eventlist.application.EventListServiceImpl;
import ssgssak.ssgpointappevent.domain.eventlist.dto.CreateEventListDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.GetEventsDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.ReturnEventListIdDto;
import ssgssak.ssgpointappevent.domain.eventlist.dto.UpdateEventListDto;
import ssgssak.ssgpointappevent.domain.eventlist.vo.CreateEventListInputVo;
import ssgssak.ssgpointappevent.domain.eventlist.vo.GetEventsOutputVo;
import ssgssak.ssgpointappevent.domain.eventlist.vo.CreateEventListOutputVo;
import ssgssak.ssgpointappevent.domain.eventlist.vo.UpdateEventListInputVo;

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
    /*
    이벤트리스트아이디는 다른 이벤트를 생성할 때 CreateVo에 담아서 사용해야 하기 때문에 createEventList() 메서드에서 반환해준다.
    이벤트리스트 데이터 생성 -> 이벤트리스트아이디 반환 -> 반환된 이벤트리스트아이디로 각 이벤트 생성(drawing, redirection 등)
     */
    @PostMapping("/admin")
    public ResponseEntity<CreateEventListOutputVo> createEventList(@RequestBody CreateEventListInputVo createNewEventListVo){
        CreateEventListDto createEventListDto = modelMapper.map(createNewEventListVo, CreateEventListDto.class);
        ReturnEventListIdDto returnEventListIdDto = eventListService.createEventList(createEventListDto);
        CreateEventListOutputVo createEventListOutputVo = modelMapper.map(
                returnEventListIdDto, CreateEventListOutputVo.class
        );
        return new ResponseEntity<>(createEventListOutputVo, HttpStatus.OK);
    }

    // 2. 이벤트 정보수정(종료일 변경)
    @PutMapping("/admin")
    public void updateEventList(@RequestBody UpdateEventListInputVo updateEventListInputVo,
                                @RequestParam(name = "id") Long eventListId){
        UpdateEventListDto updateEventListDto = modelMapper.map(updateEventListInputVo, UpdateEventListDto.class);
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
        GetEventsDto getEventsDto = eventListService.getLatestEvents();
        log.info("readEventsDto : " + getEventsDto);
        GetEventsOutputVo getEventsOutputVo = modelMapper.map(getEventsDto, GetEventsOutputVo.class);
        log.info("getEventsOutputVo : " + getEventsOutputVo);
        return new ResponseEntity<>(getEventsOutputVo, HttpStatus.OK);
    }

    // 2. 진행 이벤트 조회하기(마감임박순)
    @GetMapping("/imminent-order")
    public ResponseEntity<GetEventsOutputVo> getImminentEvents() {
        GetEventsDto getEventsDto = eventListService.getImminentEvents();
        log.info("readEventsDto : " + getEventsDto);
        GetEventsOutputVo getEventsOutputVo = modelMapper.map(getEventsDto, GetEventsOutputVo.class);
        log.info("getEventsOutputVo : " + getEventsOutputVo);
        return new ResponseEntity<>(getEventsOutputVo, HttpStatus.OK);
    }
}
