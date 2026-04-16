package knr.yes24slc.integration.config;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Setter
@Getter
@Configuration

@ConfigurationProperties(prefix = "broker.core")
public class CoreGatewayConfig{

//    mqtt.clients.multi_client_1.client-id: widis_gateway111111
//    mqtt.clients.multi_client_1.uri: tcp://127.0.0.1:1883

    private String clientId;
    private String uri;

    @PostConstruct
    public void postConstruct() {
        log.info("CoreGateway : {} {}", uri,clientId);
    }


}
