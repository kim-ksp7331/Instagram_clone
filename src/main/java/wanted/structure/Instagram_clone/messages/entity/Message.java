package wanted.structure.Instagram_clone.messages.entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class Message {

    private final String token;
    private final String topic;
    private final String condition;
    private final Map<String, String> data;
    private final Notification notification;

    public Message(String title, String body) {
        this.token = "token";
        this.topic = "topic";
        this.condition = "condition";
        this.data = new HashMap<>();
        this.notification = new Notification(title, body);
    }

}
