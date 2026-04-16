package knr.yes24slc.service;

import knr.yes24slc.integration.config.CoreGatewayConfig;
import knr.yes24slc.integration.config.LegacyGatewayConfig;
import knr.yes24slc.integration.model.Pose;
import knr.yes24slc.integration.model.StandardStates;
import knr.yes24slc.integration.mqtt.publish.MqttPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreGateWayService {
    @Autowired
    private LegacyGatewayConfig legacyGatewayConfig;
    @Autowired
    private CoreGatewayConfig coreGatewayConfig;

    private final MqttPublisher mqttPublisher;

    public void toWidisPose(String topic, Pose pose){
        mqttPublisher.send(legacyGatewayConfig.getClientId(),topic.replace("sari","widis"),pose);
    }

    public void toWidisStandardState (String topic , StandardStates.ChangeStandardState state){

        mqttPublisher.send(legacyGatewayConfig.getClientId(),topic.replace("sari","widis"),state);
    }
//
    public void toCorePose(String topic, Pose pose){
        mqttPublisher.send(coreGatewayConfig.getClientId(),topic.replace("widis","gateway"),pose);
    }
    public void toCoreStandardState (String topic , StandardStates.ChangeStandardState state){
        //coreMqttPublisher.send(topic.replace("widis","gateway"),state);
        mqttPublisher.send(coreGatewayConfig.getClientId(),topic.replace("widis","gateway"),state);
    }
}
