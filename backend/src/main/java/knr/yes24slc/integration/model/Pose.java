package knr.yes24slc.integration.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2D 좌표 위치
 * <ul>
 *     <li>X - 원점에서 X축으로 떨어진 거리(m)</li>
 *     <li>Y - 원점에서 Y축으로 떨어진 거리(m)</li>
 *     <li>Theta - 원점에서 바라보는 각도(rad)</li>
 * </ul>
 */
@Getter
@Builder
@ToString
//@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pose implements IPose {

    @EqualsAndHashCode.Include
    //@Column(name = "x", nullable = false)
    @JsonSerialize(using = DoubleRoundSerializer.class)
    private double x;

    @EqualsAndHashCode.Include
    //@Column(name = "y", nullable = false)
    @JsonSerialize(using = DoubleRoundSerializer.class)
    private double y;

    @EqualsAndHashCode.Include
    //@Column(name = "theta", nullable = false)
    @JsonSerialize(using = DoubleRoundSerializer.class)
    private double theta;

    /**
     * 좌표에 배율을 곱하여 정수부분만 가져와 문자열로 출력한다.
     * @param scale 배율
     * @return X|Y|Theta (각 값은 배율을 곱한 후의 정수값)
     */
    public String getCorrectionString(int scale) {
        return Stream.of((int)(x * scale)
                    , (int)(y * scale)
                    , (int)(theta * scale))
                .map(String::valueOf).collect(Collectors.joining("|"));
    }

    /**
     * 반올림 처리
     */
    private static class DoubleRoundSerializer extends JsonSerializer<Double> {

        private static final MathContext m = new MathContext(5);

        @Override
        public void serialize(Double value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider)
                throws IOException {
            BigDecimal bd = BigDecimal.valueOf(value);
            jsonGenerator.writeNumber(bd.round(m));
        }
    }
}