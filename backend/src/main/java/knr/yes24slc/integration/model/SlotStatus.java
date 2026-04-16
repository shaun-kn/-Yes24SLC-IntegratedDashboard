package knr.yes24slc.integration.model;

public enum SlotStatus {
    EMPTY(0),
    LOADED(1),
    MOVING_IN(2),
    MOVING_OUT(3),
    ERROR(4),
    DISABLED(5);

    private final int code;

    SlotStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}