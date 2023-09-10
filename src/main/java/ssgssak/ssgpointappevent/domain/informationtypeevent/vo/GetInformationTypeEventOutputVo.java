package ssgssak.ssgpointappevent.domain.informationtypeevent.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInformationTypeEventOutputVo {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
}
