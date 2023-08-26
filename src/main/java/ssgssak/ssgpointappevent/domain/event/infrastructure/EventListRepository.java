package ssgssak.ssgpointappevent.domain.event.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import ssgssak.ssgpointappevent.domain.event.entity.EventList;

public interface EventListRepository extends JpaRepository<EventList, Long> {
    
}
