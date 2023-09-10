package ssgssak.ssgpointappevent.domain.redirectionevent.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.redirectionevent.application.RedirectionEventServiceImpl;
import ssgssak.ssgpointappevent.domain.redirectionevent.dto.CreateRedirectionEventInputDto;
import ssgssak.ssgpointappevent.domain.redirectionevent.dto.GetRedirectionEventDto;
import ssgssak.ssgpointappevent.domain.redirectionevent.dto.UpdateRedirectionEventDto;
import ssgssak.ssgpointappevent.domain.redirectionevent.vo.CreateRedirectionEventInputVo;
import ssgssak.ssgpointappevent.domain.redirectionevent.vo.GetRedirectionEventOutputVo;
import ssgssak.ssgpointappevent.domain.redirectionevent.vo.UpdateRedirectionEventInputVo;

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
    public void createRedirectionEvent(@RequestBody CreateRedirectionEventInputVo createRedirectionEventInputVo){
        CreateRedirectionEventInputDto createRedirectionEventInputDto = modelMapper.map(createRedirectionEventInputVo,
                CreateRedirectionEventInputDto.class);
        redirectionEventService.createRedirectionEvent(createRedirectionEventInputDto);
    }

    // 2. 이벤트 정보 변경
    @PutMapping("/admin")
    public void updateRedirectionEvent(@RequestBody UpdateRedirectionEventInputVo updateRedirectionEventInputVo,
                                       @RequestParam(name = "id") Long eventListId){
        UpdateRedirectionEventDto updateRedirectionEventDto = modelMapper.map(updateRedirectionEventInputVo,
                UpdateRedirectionEventDto.class);
        redirectionEventService.updateRedirectionEvent(updateRedirectionEventDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회
     */

    // 1. 이벤트 조회
    @GetMapping("")
    public ResponseEntity<GetRedirectionEventOutputVo> getRedirectionEvent(@RequestParam(name = "id") Long eventListId) {
        GetRedirectionEventDto getRedirectionEventDto = redirectionEventService.getRedirectionEvent(eventListId);
        GetRedirectionEventOutputVo getRedirectionEventOutputVo = modelMapper.map(getRedirectionEventDto, GetRedirectionEventOutputVo.class);
        return new ResponseEntity<>(getRedirectionEventOutputVo, HttpStatus.OK);
    }
}
