package knr.yes24slc.dto;

public class OsrWaveDto {

    private String wave;
    private int toteCount;

    public OsrWaveDto(String wave, int toteCount) {
        this.wave = wave;
        this.toteCount = toteCount;
    }

    public String getWave() {
        return wave;
    }

    public int getToteCount() {
        return toteCount;
    }
}