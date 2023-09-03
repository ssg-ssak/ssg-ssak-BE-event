package ssgssak.ssgpointappevent.domain.drawingevent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DrawingEvent {
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

    public void updateDrawingEvent(String title, String titleImageUrl, String contentsImageUrl){
        this.title = title;
        this.titleImageUrl = titleImageUrl;
        this.contentsImageUrl = contentsImageUrl;
    }
}
