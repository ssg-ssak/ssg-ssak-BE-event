package ssgssak.ssgpointappevent.domain.informationtypeevent.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappevent.domain.informationtypeevent.application.InformationTypeEventServiceImpl;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.CreateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.GetInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.UpdateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.vo.CreateInformationTypeEventInputVo;
import ssgssak.ssgpointappevent.domain.informationtypeevent.vo.GetInformationTypeEventOutputVo;
import ssgssak.ssgpointappevent.domain.informationtypeevent.vo.UpdateInformationTypeEventInputVo;

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
    public void createInformationTypeEvent(@RequestBody CreateInformationTypeEventInputVo createInformationTypeEventInputVo){
        CreateInformationTypeEventDto createInformationTypeEventDto = modelMapper.map(
                createInformationTypeEventInputVo, CreateInformationTypeEventDto.class
        );
        informationTypeEventService.createInformationTypeEvent(createInformationTypeEventDto);
    }

    // 2. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    @PutMapping("/admin")
    public void updateInformationTypeEvent(@RequestBody UpdateInformationTypeEventInputVo updateInformationTypeEventInputVo,
                                           @RequestParam(name = "id") Long eventListId) {
        UpdateInformationTypeEventDto updateInformationTypeEventDto = modelMapper.map(
                updateInformationTypeEventInputVo, UpdateInformationTypeEventDto.class
        );
        informationTypeEventService.updateInformationTypeEvent(updateInformationTypeEventDto, eventListId);
    }

    /*
    아래는 유저 API
    1. 이벤트 조회
     */
    @GetMapping("")
    public ResponseEntity<GetInformationTypeEventOutputVo> getInformationTypeEvent(@RequestParam(name = "id") Long eventListId){
        GetInformationTypeEventDto getInformationTypeEventDto =
                informationTypeEventService.getInformationTypeEvent(eventListId);
        GetInformationTypeEventOutputVo getInformationTypeEventOutputVo = modelMapper.map(
                getInformationTypeEventDto, GetInformationTypeEventOutputVo.class
        );
        return new ResponseEntity<>(getInformationTypeEventOutputVo, HttpStatus.OK);
    }
}
