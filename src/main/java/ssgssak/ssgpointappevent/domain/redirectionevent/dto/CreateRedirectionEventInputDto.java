package ssgssak.ssgpointappevent.domain.redirectionevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRedirectionEventInputDto {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
    private String redirectionUrl;
}
