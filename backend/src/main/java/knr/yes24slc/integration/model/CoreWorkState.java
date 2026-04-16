package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
public enum CoreWorkState {
    IDLE(0), WORKING(1), DOCKING(2), PAUSE(3), CANCEL(4), ABORT(5), CHARGE(7), DOCK_OUT(8),MAPPING(10);

    @JsonValue
    private final int work;

    CoreWorkState(int work) {
        this.work = work;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CoreWorkState findByWork(int work) {
        log.debug("##### RosWorkState findByWork({})", work);
        return Arrays.stream(CoreWorkState.values()).filter(state -> state.work == work).findFirst().orElse(CoreWorkState.IDLE);
    }
}
