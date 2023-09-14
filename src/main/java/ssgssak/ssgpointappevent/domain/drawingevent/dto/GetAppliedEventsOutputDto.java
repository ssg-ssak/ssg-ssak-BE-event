package ssgssak.ssgpointappevent.domain.drawingevent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetAppliedEventsOutputDto {
    List<Long> appliedEventIds;
}
