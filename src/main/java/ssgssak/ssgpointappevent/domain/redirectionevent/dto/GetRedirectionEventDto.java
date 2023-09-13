package ssgssak.ssgpointappevent.domain.redirectionevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetRedirectionEventDto {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
    private String redirectionUrl;
}
