package knr.yes24slc.integration.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ChargeState {
    NONE(0),
    STATION_CHARGING(1),   // 충전 스테이션에서 충전
    MANUAL_CHARGING(2)  // 유선 충전기로 수동 충전
    ;

    @JsonValue
    private final int state;

    ChargeState(int state) {
        this.state = state;
    }
}
