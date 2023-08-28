package ssgssak.ssgpointappevent.domain.event.vo;

import lombok.Getter;

@Getter
public class CreateInformationTypeEventVo {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
}
