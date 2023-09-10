package ssgssak.ssgpointappevent.domain.drawingevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDrawingEventInputDto {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
}
