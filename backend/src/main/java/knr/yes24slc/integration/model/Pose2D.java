package knr.yes24slc.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class Pose2D implements IPose {
    private final double x;
    private final double y;
    private final double theta;
}
