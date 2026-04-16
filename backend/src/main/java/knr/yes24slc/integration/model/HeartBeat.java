package knr.yes24slc.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class HeartBeat {
    private final Boolean alive;
    private final String systemTime;
}





