package ssgssak.ssgpointappevent.domain.drawingevent.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.drawingevent.application.DrawingEventServiceImpl;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.ApplyDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.CreateDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.GetDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.UpdateDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.vo.ApplyDrawingEventInputVo;
import ssgssak.ssgpointappevent.domain.drawingevent.vo.CreateDrawingEventInputVo;
import ssgssak.ssgpointappevent.domain.drawingevent.vo.GetDrawingEventOutputVo;
import ssgssak.ssgpointappevent.domain.drawingevent.vo.UpdateDrawingEventInputVo;

@RestController
@RequestMapping("/api/v1/event/drawing")
@RequiredArgsConstructor
public class DrawingEventController { // 추첨 이벤트
    private final ModelMapper modelMapper;
    private final DrawingEventServiceImpl drawingEventService;
    /*
    아래는 어드민 API
    1. 이벤트 생성
    2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    3. 유저 추첨하기
     */

    // 1. 이벤트 생성
    @PostMapping("/admin")
    public void createDrawingEvent(@RequestBody CreateDrawingEventInputVo createDrawingEventInputVo){
        CreateDrawingEventDto createDrawingEventDto = modelMapper.map(createDrawingEventInputVo,
                CreateDrawingEventDto.class);
        drawingEventService.createDrawingEvent(createDrawingEventDto);
    }

    // 2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    @PutMapping("/admin")
    public void updateDrawingEvent(@RequestBody UpdateDrawingEventInputVo updateDrawingEventInputVo,
                                   @RequestParam(name = "id") Long eventListId){
        UpdateDrawingEventDto updateDrawingEventDto = modelMapper.map(updateDrawingEventInputVo,
                UpdateDrawingEventDto.class);
        drawingEventService.updateDrawingEvent(updateDrawingEventDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회하기
    2. 이벤트 응모하기
    3. 마이이벤트 조회하기
     */

    // 1. 이벤트 조회하기
    @GetMapping("")
    public ResponseEntity<GetDrawingEventOutputVo> getDrawingEvent(@RequestParam(name = "id") Long eventListId){
        GetDrawingEventDto getDrawingEventDto = drawingEventService.getDrawingEvent(eventListId);
        GetDrawingEventOutputVo getDrawingEventOutputVo = modelMapper.map(getDrawingEventDto,
                GetDrawingEventOutputVo.class);
        return new ResponseEntity<>(getDrawingEventOutputVo, HttpStatus.OK);
    }

    // 2. 이벤트 응모하기(응모를 하면 applicant에 유저 정보를 추가하는데, 이 때 필요한 uuid는 context holder에서 가져온다.)
    @PostMapping("")
    public void applyDrawingEvent(@RequestBody ApplyDrawingEventInputVo applyDrawingEventInputVo){
        ApplyDrawingEventDto applyDrawingEventDto = modelMapper.map(applyDrawingEventInputVo,
                ApplyDrawingEventDto.class);
        drawingEventService.applyDrawingEvent(applyDrawingEventDto);
    }
}
