package ssgssak.ssgpointappevent.domain.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class RedirectionEvent { // 페이지 이동형 이벤트
    // 식별 관계 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "event_list_id")
    private Long eventListId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "title_image_url")
    private String titleImageUrl;

    @Column(nullable = false, name = "contents_image_url")
    private String contentsImageUrl;

    @Column(nullable = false, name = "redirection_url")
    private String redirectionUrl;

    public void updateRedirectionEvent(String title, String titleImageUrl, String contentsImageUrl, String redirectionUrl) {
        this.title = title;
        this.titleImageUrl = titleImageUrl;
        this.contentsImageUrl = contentsImageUrl;
        this.redirectionUrl = redirectionUrl;
    }
}
