package ssgssak.ssgpointappevent.domain.event.vo;

import lombok.Getter;

@Getter
public class CreateRedirectionEventVo {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
    private String redirectionUrl;
}
