package ssgssak.ssgpointappevent.domain.informationtypeevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInformationTypeEventOutputDto {
    private Long eventListId;
    private String title;
    private String contentsImageUrl;
}
