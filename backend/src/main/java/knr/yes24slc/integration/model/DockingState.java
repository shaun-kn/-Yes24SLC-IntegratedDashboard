package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum DockingState {
    NONE(0),
    DOCKING(1)
    ;

    @JsonValue
    private final int state;

    DockingState(int state) {
        this.state = state;
    }

    public boolean isDocking() {
        return Stream.of(DOCKING)
                .anyMatch(docking -> state == docking.getState());
    }
}
