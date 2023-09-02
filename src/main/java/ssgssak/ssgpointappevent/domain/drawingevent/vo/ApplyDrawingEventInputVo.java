package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.Getter;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;

@Getter
//todo: uuid를 임시로 vo로 받음 추후에 수정요망(security쪽의 token 통해 받아오기)
public class ApplyDrawingEventInputVo {
    private Long drawingEventId;
    private String uuid;
    private Boolean isWinner;
}
