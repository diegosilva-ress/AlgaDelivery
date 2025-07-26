package br.com.algaworks.algadelivery.deliverytracking.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  public static final String DELIVERY_TRACKING_TOPIC = "delivery.v1.tracking";

  @Bean
  public NewTopic deliveryEventsTopic() {
    return TopicBuilder.name(DELIVERY_TRACKING_TOPIC)
        .partitions(3)
        .replicas(1)
        .build();
  }

}
