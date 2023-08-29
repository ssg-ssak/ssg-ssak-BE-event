package ssgssak.ssgpointappevent.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRedirectionEventDto {
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
    private String redirectionUrl;
}
