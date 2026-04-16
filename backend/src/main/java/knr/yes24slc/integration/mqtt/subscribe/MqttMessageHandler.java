package knr.yes24slc.integration.mqtt.subscribe;

import com.github.tocrhz.mqtt.annotation.MqttSubscribe;
import com.github.tocrhz.mqtt.annotation.NamedValue;
import com.github.tocrhz.mqtt.annotation.Payload;
import knr.yes24slc.integration.config.CoreGatewayConfig;
import knr.yes24slc.integration.config.LegacyGatewayConfig;
import knr.yes24slc.integration.model.Pose;
import knr.yes24slc.integration.model.StandardStates;
import knr.yes24slc.service.CoreGateWayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SuppressWarnings("unused")
@Slf4j
@Component
@AllArgsConstructor
@ComponentScan(value = {"knr.yes24slc.service","knr.yes24slc.integration"})
@EnableConfigurationProperties({LegacyGatewayConfig.class , CoreGatewayConfig.class })
public class MqttMessageHandler {
    private final CoreGateWayService coreGateWayService;

//    private final CoreMqttPublisher coreMqttPublisher;
//    private final LegacyMqttPublisher legacyMqttPublisher;

    /*
    SARI to CORE / CORE GateWay
     */
    @MqttSubscribe(value = "dt/sari/state/{id}/pose", shared = true, qos = {1}, groups = {"sari_gateway"},clients = "core_gateway")
    public void subscribeCorePose(String topic, @NamedValue("id") String id, @Payload Pose pose) {
        coreGateWayService.toWidisPose(topic,pose);
    }
    @MqttSubscribe(value = "dt/sari/state/{id}/std", shared = true, qos = {1}, groups = {"sari_gateway"},clients = "core_gateway")
    public void subscribeCoreRobotInfo(String topic, @NamedValue("id") String id, @Payload StandardStates.ChangeStandardState state) {
        coreGateWayService.toWidisStandardState(topic,state);
    }

    /*
    WIDIS to CORE GateWay
     */
    @MqttSubscribe(value = "dt/widis/state/{id}/std", shared = true, qos = {1}, groups = {"widis"} , clients = "widis_gateway")
    public void subscribeWidisRobotInfo(String topic, @NamedValue("id") String id, @Payload StandardStates.ChangeStandardState state) {
        coreGateWayService.toCoreStandardState(topic,state);
    }
    @MqttSubscribe(value = "dt/widis/state/{id}/pose", shared = true, qos = {1}, groups = {"widis"},clients = "widis_gateway")
    public void subscribeWidisPose(String topic, @NamedValue("id") String id, @Payload Pose pose,String [] clientId) {
        coreGateWayService.toCorePose(topic,pose);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("MqttMessageHandler init");
    }
}