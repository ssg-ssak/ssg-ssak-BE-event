package ssgssak.ssgpointappevent.domain.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRedirectionEventVo {
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
    private String redirectionUrl;
}
