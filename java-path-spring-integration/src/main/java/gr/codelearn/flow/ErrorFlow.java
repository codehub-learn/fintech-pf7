package gr.codelearn.flow;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.Map;

@Configuration
@EnableIntegration
@Slf4j
@AllArgsConstructor
public class ErrorFlow {

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow errorInternalFlow() {
        return IntegrationFlows
                .from(errorChannel())
                .log()
                .handle(message -> log.error("Payment cancelled with reason: '{}'", ((Map<String, Object>) message.getPayload()).get("error")))
                .get();
    }
}
