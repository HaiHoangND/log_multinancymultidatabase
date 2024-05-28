package com.kafka.kafkaconsumer.kafka;

import com.kafka.kafkaconsumer.entity.Log;
import com.kafka.kafkaconsumer.repository.LogRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@NoArgsConstructor
public class MessageListener {
    @Autowired
    private LogRepository logRepository;

    @KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group 'foo': " + message);
        Log log = new Log();
        log.setMessage(message);
        logRepository.insert(log);
    }
}
