package ssgssak.ssgpointappevent.domain.event.vo;

import lombok.Getter;
import ssgssak.ssgpointappevent.domain.event.entity.EventType;

import java.time.LocalDateTime;

@Getter
public class CreateNewEventListVo {
    private String startDate;
    private String endDate;
    private EventType eventType;
    private Boolean isOver;
}
