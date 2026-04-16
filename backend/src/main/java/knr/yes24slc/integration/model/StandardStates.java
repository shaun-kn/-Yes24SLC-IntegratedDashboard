package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;


public class StandardStates {

    @Getter
    @Builder
    @ToString
    public static class ChangeStandardState  {

        private final double battery;
        private final RosWorkState workState;
        private final int pauseLevel;
        private final ChargeState chargeState;
        private final DockingState dockingState;
        private final DockingDirection dockingDirection;
    }
}