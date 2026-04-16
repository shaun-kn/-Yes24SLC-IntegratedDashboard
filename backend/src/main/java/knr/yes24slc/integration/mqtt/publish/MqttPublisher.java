package knr.yes24slc.integration.mqtt.publish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("sariMqttPublisher")
@AllArgsConstructor
public class MqttPublisher {

    private final com.github.tocrhz.mqtt.publisher.MqttPublisher mqttPublisher;

    public void send(String clientId,String topic, Object payload) {
        log.debug("mqtt publish {} : {}", topic, payload);
        mqttPublisher.send(clientId,topic, payload);
    }

}
