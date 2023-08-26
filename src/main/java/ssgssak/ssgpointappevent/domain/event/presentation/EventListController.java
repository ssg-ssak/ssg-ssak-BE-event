package ssgssak.ssgpointappevent.domain.event.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.event.application.EventListServiceImpl;
import ssgssak.ssgpointappevent.domain.event.dto.CreateNewEventListDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateEventListDto;
import ssgssak.ssgpointappevent.domain.event.vo.CreateNewEventListVo;
import ssgssak.ssgpointappevent.domain.event.vo.UpdateEventListVo;

@RestController
@RequestMapping("/api/v1/event/event-list")
@RequiredArgsConstructor
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
}
