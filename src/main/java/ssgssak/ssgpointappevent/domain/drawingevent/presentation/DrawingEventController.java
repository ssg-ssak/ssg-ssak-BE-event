package ssgssak.ssgpointappevent.domain.drawingevent.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.drawingevent.application.DrawingEventServiceImpl;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.*;
import ssgssak.ssgpointappevent.domain.drawingevent.vo.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/event/drawing")
@RequiredArgsConstructor
@Slf4j
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
        CreateDrawingEventInputDto createDrawingEventInputDto = modelMapper.map(createDrawingEventInputVo,
                CreateDrawingEventInputDto.class);
        drawingEventService.createDrawingEvent(createDrawingEventInputDto);
    }

    // 2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    @PutMapping("/admin")
    public void updateDrawingEvent(@RequestBody UpdateDrawingEventInputVo updateDrawingEventInputVo){
        UpdateDrawingEventInputDto updateDrawingEventInputDto = modelMapper.map(updateDrawingEventInputVo,
                UpdateDrawingEventInputDto.class);
        drawingEventService.updateDrawingEvent(updateDrawingEventInputDto);
    }

    // 3. 유저 추첨하기(추첨 결과를 applicant에 저장한다.)
    @PutMapping("/admin/drawing-winner")
    public ResponseEntity<DrawUserOutputVo> drawUser(@RequestBody DrawUserInputVo drawUserInputVo) {
        log.info("drawUserInputVo : {}", drawUserInputVo);
        DrawUserInputDto drawUserInputDto = modelMapper.map(drawUserInputVo, DrawUserInputDto.class);
        log.info("drawUserInputDto : {}", drawUserInputDto);
        DrawUserOutputDto drawUserOutputDto = drawingEventService.drawUser(drawUserInputDto);
        log.info("drawUserOutputDto : {}", drawUserOutputDto);
        DrawUserOutputVo drawUserOutputVo = modelMapper.map(drawUserOutputDto, DrawUserOutputVo.class);
        log.info("drawUserOutputVo : {}", drawUserOutputVo);
        return new ResponseEntity<>(drawUserOutputVo, HttpStatus.OK);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회하기
    2. 이벤트 응모하기
    3. 마이이벤트 조회하기(참여한 이벤트, 당첨 확인)
     */

    // 1. 이벤트 조회하기
    @GetMapping("")
    public ResponseEntity<GetDrawingEventOutputVo> getDrawingEvent(@RequestParam(name = "id") Long drawingEventId){
        GetDrawingEventOutputDto getDrawingEventOutputDto = drawingEventService.getDrawingEvent(drawingEventId);
        GetDrawingEventOutputVo getDrawingEventOutputVo = modelMapper.map(getDrawingEventOutputDto,
                GetDrawingEventOutputVo.class);
        return new ResponseEntity<>(getDrawingEventOutputVo, HttpStatus.OK);
    }

    // 2. 이벤트 응모하기(응모를 하면 applicant에 유저 정보를 추가하는데, 이 때 필요한 uuid는 context holder에서 가져온다.)
    @PostMapping("")
    public void applyDrawingEvent(@RequestBody ApplyDrawingEventInputVo applyDrawingEventInputVo, Principal principal){
        ApplyDrawingEventInputDto applyDrawingEventInputDto = modelMapper.map(applyDrawingEventInputVo,
                ApplyDrawingEventInputDto.class);
        drawingEventService.applyDrawingEvent(applyDrawingEventInputDto, principal.getName());
    }

    // 3. 마이 이벤트 조회하기(참여한 이벤트)
    @GetMapping("/my-event/applied/ids")
    public ResponseEntity<GetAppliedEventsOutputVo> getAppliedEvents(Principal principal){
        GetAppliedEventsOutputDto getAppliedEventsOutputDto =
                drawingEventService.getAppliedEventsId(principal.getName());
        GetAppliedEventsOutputVo getAppliedEventsOutputVo = modelMapper.map(getAppliedEventsOutputDto,
                GetAppliedEventsOutputVo.class);
        return new ResponseEntity<>(getAppliedEventsOutputVo, HttpStatus.OK);
    }

    // 4. 마이 이벤트 조회하기(당첨 확인)
//    @GetMapping("/my-event/drawed")
//    public ResponseEntity<> getMyEventsDrawed(Principal principal){
//
//    }
}
