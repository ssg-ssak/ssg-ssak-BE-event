package ssgssak.ssgpointappevent.domain.drawingevent.dto;

import lombok.*;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DrawUserOutputDto {
    private List<Applicant> winnerList;
}
