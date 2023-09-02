package ssgssak.ssgpointappevent.domain.drawingevent.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.ApplyDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.CreateDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.GetDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.UpdateDrawingEventDto;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.ApplicantRepository;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.DrawingEventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DrawingEventServiceImpl {
    private final DrawingEventRepository drawingEventRepository;
    private final ModelMapper modelMapper;
    private final ApplicantRepository applicantRepository;

    // 1. 이벤트 생성
    public void createDrawingEvent(CreateDrawingEventDto createDrawingEventDto){
        DrawingEvent drawingEvent = modelMapper.map(createDrawingEventDto, DrawingEvent.class);
        drawingEventRepository.save(drawingEvent);
    }

    // 2. 이벤트 정보 수정(이벤트 이름, 이미지 변경)
    public void updateDrawingEvent(UpdateDrawingEventDto updateDrawingEventDto, Long eventListId) {
        DrawingEvent drawingEvent = drawingEventRepository.findByEventListId(eventListId);
        drawingEvent.updateDrawingEvent(
                updateDrawingEventDto.getTitle(),
                updateDrawingEventDto.getTitleImageUrl(),
                updateDrawingEventDto.getContentsImageUrl()
        );
    }

    // 3. 이벤트 조회하기
    @Transactional(readOnly = true)
    public GetDrawingEventDto getDrawingEvent(Long eventListId) {
        DrawingEvent drawingEvent = drawingEventRepository.findByEventListId(eventListId);
        return modelMapper.map(drawingEvent, GetDrawingEventDto.class);
    }

    // 4. 이벤트 응모하기(applicant 데이터 생성)
    //todo: uuid 불러오는 로직 추가하기(지금은 임시로 입력), 시큐리티에서 어떻게 불러오는지 알아보기.
    public void applyDrawingEvent(ApplyDrawingEventDto applyDrawingEventDto) {
        // drawingEventId에 해당하는 DrawingEvent가 존재하는지 먼저 체크.
        if(!checkDrawingEventExist(applyDrawingEventDto.getDrawingEventId())){
            throw new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.");
        };
        // 해당 이벤트의 첫 응모자일 경우, 이벤트 응모 처리(해당 이벤트의 참여자 목록 리스트가 비어있으면 첫 응모자).
        List<Applicant> applicants = getApplicants(applyDrawingEventDto.getDrawingEventId());
        if(applicants.isEmpty()){
            Applicant applicant = modelMapper.map(applyDrawingEventDto, Applicant.class);
            applicantRepository.save(applicant);
            return;
        }
        // 첫 응모자가 아닐 경우, 중복 참여 체크(uuid가 일치하는 참여자가 있는지 체크)
        else {
            if(checkDuplicateApplicant(applicants, applyDrawingEventDto.getUuid())){
                throw new IllegalArgumentException("이미 응모한 이벤트입니다.");
            }
            else {
                Applicant applicant = modelMapper.map(applyDrawingEventDto, Applicant.class);
                applicantRepository.save(applicant);
            };
        }
    }

    // 4-1. 이벤트 응모하기 전, 이벤트 존재 여부 체크
    public Boolean checkDrawingEventExist(Long drawingEventId) {
        Optional<DrawingEvent> drawingEvent = drawingEventRepository.findById(drawingEventId);
        return drawingEvent.isPresent();
    }

    // 4-2. 해당 이벤트의 응모자 목록 가져오기
    public List<Applicant> getApplicants(Long drawingEventId) {
        return applicantRepository.findByDrawingEventId(drawingEventId);
    }

    // 4-3. 이벤트 중복 응모 여부 체크
    public Boolean checkDuplicateApplicant(List<Applicant> applicants, String uuid) {
        // applicants 리스트에서 uuid가 일치하는 참여자가 있는지 체크
        Optional<Applicant> matchingApplicant = applicants.stream()
                .filter(applicant -> applicant.getUuid().equals(uuid))
                .findFirst();
        return matchingApplicant.isPresent();
    }
}
