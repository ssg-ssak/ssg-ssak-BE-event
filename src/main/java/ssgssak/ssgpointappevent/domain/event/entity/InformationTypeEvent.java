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
public class InformationTypeEvent { // 조회형 이벤트
    // 식별 관계 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "event_list_id")
    private Long eventListId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "title_image url")
    private String titleImageUrl;

    @Column(nullable = false, name = "contents_image url")
    private String contentsImageUrl;

    public void updateInformationTypeEvent(String title, String titleImageUrl, String contentsImageUrl){
        this.title = title;
        this.titleImageUrl = titleImageUrl;
        this.contentsImageUrl = contentsImageUrl;
    }
}