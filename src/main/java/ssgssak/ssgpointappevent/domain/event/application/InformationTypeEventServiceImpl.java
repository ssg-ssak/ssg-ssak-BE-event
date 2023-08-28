package ssgssak.ssgpointappevent.domain.event.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ssgssak.ssgpointappevent.domain.event.dto.CreateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.GetInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.dto.UpdateInformationTypeEventDto;
import ssgssak.ssgpointappevent.domain.event.entity.InformationTypeEvent;
import ssgssak.ssgpointappevent.domain.event.infrastructure.InformationTypeEventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class InformationTypeEventServiceImpl {
    private final InformationTypeEventRepository informationTypeEventRepository;
    private final ModelMapper modelMapper;
    /*
    1. 새로운 이벤트 생성
    2. 이벤트 조회
    3. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
     */

    // 1. 새로운 이벤트 생성
    public void createInformationTypeEvent(CreateInformationTypeEventDto createInformationTypeEventDto){
        log.info("createInformationTypeEventDto : " + createInformationTypeEventDto);
        InformationTypeEvent informationTypeEvent = modelMapper.map(createInformationTypeEventDto, InformationTypeEvent.class);
        informationTypeEventRepository.save(informationTypeEvent);
    }

    // 2. 이벤트 조회
    public GetInformationTypeEventDto getInformationTypeEvent(Long eventListId){
        InformationTypeEvent informationTypeEvent = informationTypeEventRepository.findByEventListId(eventListId);
        return modelMapper.map(informationTypeEvent, GetInformationTypeEventDto.class);
    }

    // 3. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    public void updateInformationTypeEvent(UpdateInformationTypeEventDto updateInformationTypeEventDto, Long eventListId){
        InformationTypeEvent informationTypeEvent = informationTypeEventRepository.findByEventListId(eventListId);
        InformationTypeEvent updatedInformationTypeEvent = informationTypeEvent.toBuilder()
                .title(updateInformationTypeEventDto.getTitle())
                .titleImageUrl(updateInformationTypeEventDto.getTitleImageUrl())
                .contentsImageUrl(updateInformationTypeEventDto.getContentsImageUrl())
                .build();
        informationTypeEventRepository.save(updatedInformationTypeEvent);
    }
}
