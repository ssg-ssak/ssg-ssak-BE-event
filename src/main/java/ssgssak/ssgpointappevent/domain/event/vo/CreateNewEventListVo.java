package ssgssak.ssgpointappevent.domain.event.vo;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import ssgssak.ssgpointappevent.domain.event.entity.EventType;

import java.time.LocalDate;

@Getter
public class CreateNewEventListVo {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private EventType eventType;
    private Boolean isOver;
}
