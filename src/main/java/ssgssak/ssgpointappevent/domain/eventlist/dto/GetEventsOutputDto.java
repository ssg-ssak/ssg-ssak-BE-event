package ssgssak.ssgpointappevent.domain.eventlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEventsOutputDto {
    private List<EventList> events;
}
