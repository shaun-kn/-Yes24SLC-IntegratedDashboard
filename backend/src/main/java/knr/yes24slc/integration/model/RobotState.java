package knr.yes24slc.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class RobotState {
    private final String rid;
    private final String type;
    private final Pose2D pose;
    private final RosWorkState workstate;
}
