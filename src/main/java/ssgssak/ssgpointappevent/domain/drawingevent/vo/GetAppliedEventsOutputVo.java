package ssgssak.ssgpointappevent.domain.drawingevent.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAppliedEventsOutputVo {
    List<Long> appliedEventIds;
}
