package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDrawingEventOutputVo {
    private Long eventListId;
    private String title;
    private String titleImageUrl;
    private String contentsImageUrl;
}
