package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum DockingDirection {
    NONE(0),
    FRONT(1),
    BACK(-1)
    ;

    @JsonValue
    private final int direction;

    DockingDirection(int direction) {
        this.direction = direction;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DockingDirection findByCode(int direction) {
        return Stream.of(DockingDirection.values()).filter(v -> v.direction == direction).findFirst().orElse(DockingDirection.NONE);
    }
}
