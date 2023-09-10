package ssgssak.ssgpointappevent.domain.eventlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateEventListInputDto {
    private Long eventListId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
