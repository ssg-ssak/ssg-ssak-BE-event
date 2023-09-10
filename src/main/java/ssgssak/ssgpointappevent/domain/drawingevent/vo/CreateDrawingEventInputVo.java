package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.Getter;

@Getter
public class CreateDrawingEventInputVo {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
}
