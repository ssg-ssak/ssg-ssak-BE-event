package ssgssak.ssgpointappevent.domain.drawingevent.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;

@Repository
public interface DrawingEventRepository extends JpaRepository<DrawingEvent, Long> {
    DrawingEvent findByEventListId(Long eventListId);
}
