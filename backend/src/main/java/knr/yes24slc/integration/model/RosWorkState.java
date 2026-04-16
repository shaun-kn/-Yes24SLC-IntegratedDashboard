package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
public enum RosWorkState {
    IDLE(0), WORKING(1), DOCKING(2), PAUSE(3), ABORT(5), STAND_BY(7), DOCK_OUT(8), FOLLOW_ME(9), MAPPING(10);

    @JsonValue
    private final int work;

    RosWorkState(int work) {
        this.work = work;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RosWorkState findByWork(int work) {
        log.debug("##### RosWorkState findByWork({})", work);
        return Arrays.stream(RosWorkState.values()).filter(state -> state.work == work).findFirst().orElse(RosWorkState.IDLE);
    }
}
