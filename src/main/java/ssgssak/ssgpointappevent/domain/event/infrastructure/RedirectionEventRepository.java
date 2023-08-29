package ssgssak.ssgpointappevent.domain.event.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappevent.domain.event.entity.RedirectionEvent;

@Repository
public interface RedirectionEventRepository extends JpaRepository<RedirectionEvent, Long> {
    RedirectionEvent findByEventListId(Long eventListId);
}
