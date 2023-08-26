package ssgssak.ssgpointappevent.domain.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RedirectionEvent { // 페이지 이동형 이벤트
    // 식별 관계 매핑
    @Id
    private Long eventListId;
    @MapsId // EventList의 PK를 FK로 사용(EventListId로 매핑)
    @OneToOne
    @JoinColumn(name = "event_list_id")
    private EventList eventList;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "title_image_url")
    private String titleImageUrl;

    @Column(nullable = false, name = "contents_image_url")
    private String contentsImageUrl;

    @Column(nullable = false, name = "redirection_url")
    private String redirectionUrl;
}
