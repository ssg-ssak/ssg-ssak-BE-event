package ssgssak.ssgpointappevent.domain.informationtypeevent.vo;

import lombok.Getter;

@Getter
public class CreateInformationTypeEventInputVo {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
}
