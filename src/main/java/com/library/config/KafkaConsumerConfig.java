package com.library.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String,Object> kafkaConsumer(ConsumerProperties properties){
        Map<String, Object>  config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.bootstrap);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, properties.groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.autoOffsetReset);
        return  config;
    }

    @Bean
    public Flux<ReceiverRecord<String, String>> receiverOptions (ConsumerProperties properties){
        ReceiverOptions<String,String> options = ReceiverOptions.create(kafkaConsumer(properties));
        options.subscription(Collections.singletonList(properties.topic));
        return KafkaReceiver.create(options).receive();
    }

}
