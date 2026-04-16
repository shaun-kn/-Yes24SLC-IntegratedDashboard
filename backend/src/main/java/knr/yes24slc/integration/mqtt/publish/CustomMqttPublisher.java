package knr.yes24slc.integration.mqtt.publish;

import com.github.tocrhz.mqtt.autoconfigure.MqttConnector;
import com.github.tocrhz.mqtt.autoconfigure.MqttConversionService;
import com.github.tocrhz.mqtt.publisher.MqttPublisher;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * tocrhz.mqtt publish 시 exception handling 하도록 수정
 */
public class CustomMqttPublisher extends MqttPublisher {

    @SneakyThrows
    @Override
    public void send(String clientId, String topic, Object payload, int qos, boolean retained, IMqttActionListener callback) {
        Assert.isTrue(topic != null && !topic.trim().isEmpty(), "topic cannot be blank.");
        IMqttAsyncClient client = Objects.requireNonNull(MqttConnector.getClientById(clientId));
        byte[] bytes = MqttConversionService.getSharedInstance().toBytes(payload);
        if (bytes == null) {
            return;
        }
        MqttMessage message = toMessageForCustom(bytes, qos, retained);
        client.publish(topic, message, null, callback);
    }

    private MqttMessage toMessageForCustom(byte[] payload, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload);
        message.setQos(qos);
        message.setRetained(retained);
        return message;
    }
}
