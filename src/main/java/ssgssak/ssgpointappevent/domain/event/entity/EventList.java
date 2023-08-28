package ssgssak.ssgpointappevent.domain.event.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class EventList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false, name = "is_over")
    private Boolean isOver;

    public void changeEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
