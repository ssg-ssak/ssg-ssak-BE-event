package ssgssak.ssgpointappevent.domain.event.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappevent.domain.event.entity.InformationTypeEvent;

@Repository
public interface InformationTypeEventRepository extends JpaRepository<InformationTypeEvent, Long> {
    InformationTypeEvent findByEventListId(Long eventListId);
}
