package knr.yes24slc.integration.config;

import com.github.tocrhz.mqtt.autoconfigure.MqttConfigurer;
import com.github.tocrhz.mqtt.subscriber.TopicPair;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@EnableConfigurationProperties({LegacyGatewayConfig.class , CoreGatewayConfig.class })
public class MqttBrokerInitial extends MqttConfigurer {

    @Autowired
    private LegacyGatewayConfig legacyGatewayConfig;

    @Autowired
    private CoreGatewayConfig coreGatewayConfig;

    /**
     * 在创建客户端之前, 增删改客户端配置.
     * <p>清除的原有客户端, 增加客户端 "client01" </p>
     */
    public void beforeCreate(ClientRegistry registry) {
        registry.clear();

        registry.add(coreGatewayConfig.getClientId(), coreGatewayConfig.getUri());
        registry.add(legacyGatewayConfig.getClientId(), legacyGatewayConfig.getUri());
    }


    /**
     * 创建客户端.
     *
     * @param clientId 客户端ID
     * @param options  MqttConnectOptions
     */
    public IMqttAsyncClient postCreate(String clientId, MqttConnectOptions options) throws MqttException {
        return new MqttAsyncClient(options.getServerURIs()[0], clientId, new MemoryPersistence());
    }

    /**
     * 在创建客户端后, 订阅主题前, 修改订阅的主题.
     * <p>清除 client01 的原有订阅, 增加订阅 "/test/abc"</p>
     *
     */
    public void beforeSubscribe(String clientId, Set<TopicPair> topicPairs) {
        if("client01".equals(clientId)){
            topicPairs.clear();
            topicPairs.add(TopicPair.of("/test/abc", 0));
        }
    }

}
