package ssgssak.ssgpointappevent.domain.eventlist.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEventsVo {
    List<EventList> events;
}
