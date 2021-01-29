package com.library.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("kafka")
@Data
public class ConsumerProperties {

    public String bootstrap;
    public String groupId;
    public String autoOffsetReset;
    public String topic;
}
