package ssgssak.ssgpointappevent.domain.drawingevent.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DrawUserInputDto {
    private Long drawingEventId;
    private int numberOfWinners;
}
