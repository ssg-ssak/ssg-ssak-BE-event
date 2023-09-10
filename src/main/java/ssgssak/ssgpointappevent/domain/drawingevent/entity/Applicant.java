package ssgssak.ssgpointappevent.domain.drawingevent.entity;

import jakarta.persistence.*;
import lombok.*;
import ssgssak.ssgpointappevent.global.common.entity.BaseTimeEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Applicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "drawing_event_id")
    private Long drawingEventId;

    @Column(nullable = false, name = "uuid")
    private String uuid;

    @Column(nullable = false, name = "is_winner", columnDefinition = "boolean default false")
    private Boolean isWinner;

    // 당첨자 발표 시에 해당 유저 당첨자로 변경
    public void applicantIsWinner() {
        this.isWinner = true;
    }
}
