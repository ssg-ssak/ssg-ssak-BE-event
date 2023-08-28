package ssgssak.ssgpointappevent.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappevent.domain.event.entity.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateNewEventListDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private EventType eventType;
    private Boolean isOver;
}
