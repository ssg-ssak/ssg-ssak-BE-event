package ssgssak.ssgpointappevent.domain.eventlist.vo;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/*
이벤트 시작일, 종료일, 이벤트타입 중 수정 가능한 것은 종료일 뿐이기 때문에 종료일만 받는다.
종료 여부는 관리자가 직접 수정하는 것이 아닌 종료일이 임박하면 따로 종료처리하는 로직 구현.
 */
@Getter
public class UpdateEventListVo {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}