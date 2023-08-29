package ssgssak.ssgpointappevent.domain.event.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.event.application.RedirectionEventServiceImpl;
import ssgssak.ssgpointappevent.domain.event.dto.CreateRedirectionEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.GetRedirectionEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateRedirectionEventDto;
import ssgssak.ssgpointappevent.domain.event.vo.CreateRedirectionEventVo;
import ssgssak.ssgpointappevent.domain.event.vo.GetRedirectionEventVo;
import ssgssak.ssgpointappevent.domain.event.vo.UpdateRedirectionEventVo;

@RestController
@RequestMapping("/api/v1/event/redirection")
@RequiredArgsConstructor
public class RedirectionEventController { // 페이지 이동 이벤트
    private final RedirectionEventServiceImpl redirectionEventService;
    private final ModelMapper modelMapper;
    /*
    아래는 어드민 API
    1. 이벤트 생성
    2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
     */

    // 1. 이벤트 생성
    @PostMapping("/admin")
    public void createRedirectionEvent(@RequestBody CreateRedirectionEventVo createRedirectionEventVo){
        CreateRedirectionEventDto createRedirectionEventDto = modelMapper.map(createRedirectionEventVo,
                CreateRedirectionEventDto.class);
        redirectionEventService.createRedirectionEvent(createRedirectionEventDto);
    }

    // 2. 이벤트 정보 변경
    @PutMapping("/admin")
    public void updateRedirectionEvent(@RequestBody UpdateRedirectionEventVo updateRedirectionEventVo,
                                       @RequestParam(name = "id") Long eventListId){
        UpdateRedirectionEventDto updateRedirectionEventDto = modelMapper.map(updateRedirectionEventVo,
                UpdateRedirectionEventDto.class);
        redirectionEventService.updateRedirectionEvent(updateRedirectionEventDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회
     */

    // 1. 이벤트 조회
    @GetMapping("")
    public ResponseEntity<GetRedirectionEventVo> getRedirectionEvent(@RequestParam(name = "id") Long eventListId) {
        GetRedirectionEventDto getRedirectionEventDto = redirectionEventService.getRedirectionEvent(eventListId);
        GetRedirectionEventVo getRedirectionEventVo = modelMapper.map(getRedirectionEventDto, GetRedirectionEventVo.class);
        return new ResponseEntity<>(getRedirectionEventVo, HttpStatus.OK);
    }
}
