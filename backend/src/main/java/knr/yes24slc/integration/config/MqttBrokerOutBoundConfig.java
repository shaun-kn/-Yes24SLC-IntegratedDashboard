package knr.yes24slc.integration.config;


import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@RequiredArgsConstructor
@IntegrationComponentScan
//@EnableConfigurationProperties(LegacyMqttConfig.class)
public class MqttBrokerOutBoundConfig {

//    @Bean("legacy")
//    @ConfigurationProperties(prefix = "broker.legacy")
//    public MqttProperties  getLegacyConfig() {
//        return new MqttProperties ();
//    }
//
//    @Bean("core")
//    @ConfigurationProperties(prefix = "broker.core")
//    public MqttProperties  getCoreConfig() {
//        return new MqttProperties ();
//    }
//
//    @Bean
//    public MessageChannel legacyMqttOutboundChannel() {
//        return new DirectChannel();
//    }
//    @Bean
//    public MessageChannel coreMqttOutboundChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "legacyMqttOutboundChannel")
//    //public MessageHandler legacyMqttOutbound(LegacyMqttConfig properties) {
//    public MessageHandler legacyMqttOutbound() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//
//        options.setServerURIs(getLegacyConfig().getUri());
//        factory.setConnectionOptions(options);
//
//        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(getCoreConfig().getClientId() + "_widis", factory);
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("testTopic");
//        return messageHandler;
//    }
//    @Bean
//    @ServiceActivator(inputChannel = "coreMqttOutboundChannel")
//    public MessageHandler coreMqttOutbound() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//
//        options.setServerURIs(getCoreConfig().getUri());
//        factory.setConnectionOptions(options);
//
//        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(getCoreConfig().getClientId() + "_sarics2", factory);
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("testTopic");
//        return messageHandler;
//    }
//
//
//
//
//
//    //불필요
////    @Bean
////    public MqttPahoClientFactory legacyMqttClientFactory(LegacyMqttConfig properties) {
////        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
////        MqttConnectOptions options = new MqttConnectOptions();
////        options.setServerURIs(properties.getUri());
////        factory.setConnectionOptions(options);
////        return factory;
////    }
////    @Bean
////    public MqttPahoClientFactory mqttClientFactory(LegacyMqttConfig properties) {
////        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
////        MqttConnectOptions options = new MqttConnectOptions();
////        options.setServerURIs(properties.getUri());
////        factory.setConnectionOptions(options);
////        return factory;
////    }
//
//
//
//    @MessagingGateway(defaultRequestChannel = "legacyMqttOutboundChannel")
//    public interface LegacyOutboundGateway {
//        void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);
//    }
//
//    @MessagingGateway(defaultRequestChannel = "coreMqttOutboundChannel")
//    public interface CoreOutboundGateway {
//        void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);
//    }
}
