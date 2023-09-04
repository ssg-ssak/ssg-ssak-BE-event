package ssgssak.ssgpointappevent.domain.drawingevent.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.Applicant;
import ssgssak.ssgpointappevent.domain.drawingevent.entity.DrawingEvent;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Applicant findByDrawingEventIdAndUuid(Long drawingEventId, String uuid);
}
