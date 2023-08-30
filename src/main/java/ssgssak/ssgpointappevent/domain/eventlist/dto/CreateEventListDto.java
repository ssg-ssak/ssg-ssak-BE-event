package ssgssak.ssgpointappevent.domain.eventlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventType;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateEventListDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private EventType eventType;
    private Boolean isOver;
}
