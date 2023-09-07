package wanted.structure.Instagram_clone.messages.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wanted.structure.Instagram_clone.messages.entity.Message;

@Component
@Slf4j
public class FCMClient {

    private final WebClient webClient;

    public FCMClient() {
        this.webClient = WebClient.builder()
            .baseUrl("http://localhost:4004")
            .defaultHeader("Authorization", "Bearer token")
            .build();
    }

    public Mono<Void> sendMessage(Message message) {
        return webClient.post()
            .uri("/v1/projects/1/messages:send")
            .bodyValue(message)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<Message[]> getHistory() {
        return webClient.get()
            .uri("/api/messages")
            .retrieve()
            .bodyToMono(Message[].class);
    }
}