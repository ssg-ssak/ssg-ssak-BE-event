package ssgssak.ssgpointappevent.domain.drawingevent.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.ApplyDrawingEventInputDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.CreateDrawingEventInputDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.GetDrawingEventOutputDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.UpdateDrawingEventInputDto;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.ApplicantRepository;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.DrawingEventRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DrawingEventServiceImpl {
    private final DrawingEventRepository drawingEventRepository;
    private final ModelMapper modelMapper;
    private final ApplicantRepository applicantRepository;

    // 1. 이벤트 생성
    public void createDrawingEvent(CreateDrawingEventInputDto createDrawingEventInputDto){
        DrawingEvent drawingEvent = modelMapper.map(createDrawingEventInputDto, DrawingEvent.class);
        drawingEventRepository.save(drawingEvent);
    }

    // 2. 이벤트 정보 수정(이벤트 이름, 이미지 변경)
    public void updateDrawingEvent(UpdateDrawingEventInputDto updateDrawingEventInputDto) {
        DrawingEvent drawingEvent = drawingEventRepository.findById(updateDrawingEventInputDto.getDrawingEventId())
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        drawingEvent.updateDrawingEvent(
                updateDrawingEventInputDto.getTitle(),
                updateDrawingEventInputDto.getTitleImageUrl(),
                updateDrawingEventInputDto.getContentsImageUrl()
        );
    }

    // 3. 이벤트 조회하기
    @Transactional(readOnly = true)
    public GetDrawingEventOutputDto getDrawingEvent(Long drawingEventId) {
        DrawingEvent drawingEvent = drawingEventRepository.findById(drawingEventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));
        return GetDrawingEventOutputDto.builder()
                .drawingEvent(drawingEvent)
                .build();
    }

    // 4. 이벤트 응모하기(applicant 데이터 생성)
    //todo: uuid 불러오는 로직 추가하기(지금은 임시로 입력), 시큐리티에서 어떻게 불러오는지 알아보기.
    public void applyDrawingEvent(ApplyDrawingEventInputDto applyDrawingEventInputDto) {
        // drawingEventId에 해당하는 DrawingEvent가 존재하는지 먼저 체크.
        if(!checkDrawingEventExist(applyDrawingEventInputDto.getDrawingEventId())){
            throw new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.");
        }
        // 중복 참여 체크(uuid가 일치하는 참여자가 있는지 체크)
        if(checkDuplicateApplicant(
                applyDrawingEventInputDto.getDrawingEventId(), applyDrawingEventInputDto.getUuid()) != null
        ){
            throw new IllegalArgumentException("이미 응모한 이벤트입니다.");
        }
        else {
            Applicant applicant = modelMapper.map(applyDrawingEventInputDto, Applicant.class);
            applicantRepository.save(applicant);
        };
    }

    // 4-1. 이벤트 응모하기 전, 이벤트 존재 여부 체크
    public Boolean checkDrawingEventExist(Long drawingEventId) {
        Optional<DrawingEvent> drawingEvent = drawingEventRepository.findById(drawingEventId);
        return drawingEvent.isPresent();
    }

    // 4-2. 이벤트 중복 응모 여부 체크
    public Applicant checkDuplicateApplicant(Long drawingEventId, String uuid) {
        // applicant 데이터 중에 uuid/drawingEventListId가 일치하는 참여자가 있는지 체크
        return applicantRepository.findByDrawingEventIdAndUuid(drawingEventId, uuid);
    }
}
