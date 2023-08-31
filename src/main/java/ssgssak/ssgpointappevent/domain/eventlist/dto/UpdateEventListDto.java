package ssgssak.ssgpointappevent.domain.eventlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateEventListDto {
    private LocalDate endDate;
}
