package ssgssak.ssgpointappevent.domain.event.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.event.application.InformationTypeEventServiceImpl;
import ssgssak.ssgpointappevent.domain.event.dto.CreateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.GetInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.vo.CreateInformationTypeEventVo;
import ssgssak.ssgpointappevent.domain.event.vo.GetInformationTypeEventVo;
import ssgssak.ssgpointappevent.domain.event.vo.UpdateInformationTypeEventVo;

@RestController
@RequestMapping("/api/v1/event/information-type")
@RequiredArgsConstructor
public class InformationTypeEventController {
    private final InformationTypeEventServiceImpl informationTypeEventService;
    private final ModelMapper modelMapper;
    /*
    아래는 어드민 API
    //todo: 관리자 인증 방식 정하기
    1. 새로운 이벤트 생성
    2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
     */

    // 1. 새로운 이벤트 생성
    @PostMapping("/admin")
    public void createInformationTypeEvent(@RequestBody CreateInformationTypeEventVo createInformationTypeEventVo){
        CreateInformationTypeEventDto createInformationTypeEventDto = modelMapper.map(
                createInformationTypeEventVo, CreateInformationTypeEventDto.class
        );
        informationTypeEventService.createInformationTypeEvent(createInformationTypeEventDto);
    }

    // 2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    @PutMapping("/admin")
    public void updateInformationTypeEvent(@RequestBody UpdateInformationTypeEventVo updateInformationTypeEventVo,
                                           @RequestParam(name = "id") Long eventListId) {
        UpdateInformationTypeEventDto updateInformationTypeEventDto = modelMapper.map(
                updateInformationTypeEventVo, UpdateInformationTypeEventDto.class
        );
        informationTypeEventService.updateInformationTypeEvent(updateInformationTypeEventDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회
     */
    @GetMapping("")
    public ResponseEntity<GetInformationTypeEventVo> getInformationTypeEvent(@RequestParam(name = "id") Long eventListId){
        GetInformationTypeEventDto getInformationTypeEventDto =
                informationTypeEventService.getInformationTypeEvent(eventListId);
        GetInformationTypeEventVo getInformationTypeEventVo = modelMapper.map(
                getInformationTypeEventDto, GetInformationTypeEventVo.class
        );
        return new ResponseEntity<>(getInformationTypeEventVo, HttpStatus.OK);
    }
}
