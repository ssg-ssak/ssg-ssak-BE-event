package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.*;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DrawUserOutputVo {
    private List<Applicant> winnerList;
}
