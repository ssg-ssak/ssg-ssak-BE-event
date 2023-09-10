package ssgssak.ssgpointappevent.domain.informationtypeevent.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.CreateInformationTypeEventInputDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.GetInformationTypeEventOutputDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.dto.UpdateInformationTypeEventInputDto;
import ssgssak.ssgpointappevent.domain.informationtypeevent.entity.InformationTypeEvent;
import ssgssak.ssgpointappevent.domain.informationtypeevent.infrastructure.InformationTypeEventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class InformationTypeEventServiceImpl {
    private final InformationTypeEventRepository informationTypeEventRepository;
    private final ModelMapper modelMapper;
    /*
    1. 새로운 이벤트 생성
    2. 이벤트 조회
    3. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
     */

    // 1. 새로운 이벤트 생성
    public void createInformationTypeEvent(CreateInformationTypeEventInputDto createInformationTypeEventInputDto){
        log.info("createInformationTypeEventInputDto : " + createInformationTypeEventInputDto);
        InformationTypeEvent informationTypeEvent = modelMapper.map(createInformationTypeEventInputDto, InformationTypeEvent.class);
        informationTypeEventRepository.save(informationTypeEvent);
    }

    // 2. 이벤트 조회
    @Transactional(readOnly = true)
    public GetInformationTypeEventOutputDto getInformationTypeEvent(Long eventListId){
        InformationTypeEvent informationTypeEvent = informationTypeEventRepository.findByEventListId(eventListId);
        return modelMapper.map(informationTypeEvent, GetInformationTypeEventOutputDto.class);
    }

    // 3. 이벤트 정보 변경(이벤트 이름, 이미지 변경)
    public void updateInformationTypeEvent(UpdateInformationTypeEventInputDto updateInformationTypeEventInputDto){
        InformationTypeEvent informationTypeEvent =
                informationTypeEventRepository.findByEventListId(updateInformationTypeEventInputDto.getEventListId());
        informationTypeEvent.updateInformationTypeEvent(
                updateInformationTypeEventInputDto.getTitle(),
                updateInformationTypeEventInputDto.getContentsImageUrl()
        );
    }
}
