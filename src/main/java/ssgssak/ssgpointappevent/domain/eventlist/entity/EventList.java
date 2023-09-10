package ssgssak.ssgpointappevent.domain.eventlist.entity;

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

    @Column(nullable = false, name = "remain_days")
    private Integer remainDays;

    @Column(nullable = false, name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false, name = "is_over")
    private Boolean isOver;

    @Column(nullable = false, name = "title_image_url")
    private String titleImageUrl;

    public void changeEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void updateRemainDays() {
        this.remainDays = LocalDate.now().until(endDate).getDays();
    }
    public void updateIsOverToTrue() {
        this.isOver = true;
    }
}
