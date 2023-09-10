package ssgssak.ssgpointappevent.domain.informationtypeevent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InformationTypeEvent { // 조회형 이벤트
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "event_list_id")
    private Long eventListId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "contents_image url")
    private String contentsImageUrl;

    public void updateInformationTypeEvent(String title, String contentsImageUrl){
        this.title = title;
        this.contentsImageUrl = contentsImageUrl;
    }
}