package ssgssak.ssgpointappevent.domain.redirectionevent.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRedirectionEventOutputVo {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
    private String redirectionUrl;
}
