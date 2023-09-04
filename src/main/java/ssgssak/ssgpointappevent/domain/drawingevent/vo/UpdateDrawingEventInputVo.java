package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.Getter;

@Getter
public class UpdateDrawingEventInputVo {
    private Long drawingEventId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
}
