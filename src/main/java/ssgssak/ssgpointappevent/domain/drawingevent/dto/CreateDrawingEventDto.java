package ssgssak.ssgpointappevent.domain.drawingevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDrawingEventDto {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
}
