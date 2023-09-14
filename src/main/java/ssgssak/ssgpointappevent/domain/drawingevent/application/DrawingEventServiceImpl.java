package ssgssak.ssgpointappevent.domain.drawingevent.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappevent.domain.drawingevent.dto.*;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.ApplicantRepository;
import ssgssak.ssgpointappevent.domain.drawingevent.infrastructure.DrawingEventRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
    public void applyDrawingEvent(ApplyDrawingEventInputDto applyDrawingEventInputDto, String uuid) {
        // drawingEventId에 해당하는 DrawingEvent가 존재하는지 먼저 체크.
        if(!checkDrawingEventExist(applyDrawingEventInputDto.getDrawingEventId())){
            throw new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.");
        }
        // 중복 참여 체크(uuid가 일치하는 참여자가 있는지 체크)
        if(checkDuplicateApplicant(
                applyDrawingEventInputDto.getDrawingEventId(), uuid) != null
        ){
            throw new IllegalArgumentException("이미 응모한 이벤트입니다.");
        }
        else {
            Applicant applicant = Applicant.builder()
                    .drawingEventId(applyDrawingEventInputDto.getDrawingEventId())
                    .uuid(uuid)
                    .isWinner(false)
                    .build();
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

    // 5. 유저 추첨하기(추첨 결과를 applicant에 저장한다.)
    public DrawUserOutputDto drawUser(DrawUserInputDto drawUserInputDto) {
        List<Applicant> applicantList = applicantRepository.findAllByDrawingEventId(
                drawUserInputDto.getDrawingEventId());
        Collections.shuffle(applicantList);
        int winnerCnt = drawUserInputDto.getNumberOfWinners(); // 뽑아야 할 당첨자 수
        ArrayList<Applicant> winnerList = new ArrayList<>(); // 당첨자가 들어갈 리스트
        // n명의 당첨자 선별하기
        for(int i = 0; i < winnerCnt; i++){
            Applicant applicant = applicantList.get(i);
            applicant.applicantIsWinner(); // 당첨여부 true로 변경
            winnerList.add(applicant);
        }
        return DrawUserOutputDto.builder()
                .winnerList(winnerList)
                .build();
    }

    // 6.
    public GetAppliedEventsOutputDto getAppliedEventsId(String uuid) {
        List<Long> appliedEventsId = applicantRepository.findAllByUuid(uuid);
        return GetAppliedEventsOutputDto.builder()
                .appliedEventIds(appliedEventsId)
                .build();
    }
}
