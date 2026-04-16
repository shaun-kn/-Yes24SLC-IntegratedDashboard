package knr.yes24slc.integration.mqtt.publish;

import com.github.tocrhz.mqtt.publisher.MqttPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class CustomMqttPublisherConfig {

    @Bean
    @Order(1030)
    @ConditionalOnMissingBean(MqttPublisher.class)
    CustomMqttPublisher mqttPublisher() {
        return new CustomMqttPublisher();
    }

}
