package ssgssak.ssgpointappevent.domain.redirectionevent.vo;

import lombok.Getter;

@Getter
public class CreateRedirectionEventInputVo {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
    private String redirectionUrl;
}
