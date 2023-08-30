package ssgssak.ssgpointappevent.domain.eventlist.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;

import java.util.List;

@Repository
public interface EventListRepository extends JpaRepository<EventList, Long> {
    // 1. 진행중인 이벤트 시작일 내림차순으로 조회(최신순)
    List<EventList> findAllByIsOverOrderByStartDateDesc(Boolean isOver);

    // 2. 진행중인 이벤트 마감일 오름차순으로 조회(마감임박순)
    List<EventList> findAllByIsOverOrderByEndDateAsc(Boolean isOver);

}
