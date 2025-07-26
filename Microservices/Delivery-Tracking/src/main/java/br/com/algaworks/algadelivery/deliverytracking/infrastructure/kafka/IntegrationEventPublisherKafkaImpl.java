package br.com.algaworks.algadelivery.deliverytracking.infrastructure.kafka;

import br.com.algaworks.algadelivery.deliverytracking.infrastructure.event.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class IntegrationEventPublisherKafkaImpl implements IntegrationEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public void publish(Object event, String key, String topic) {
    SendResult<String, Object> result = kafkaTemplate.send(topic, key, event).join();
    log.info("Message published: \n\t Topic: {} \n\t Offset: {}", result.getRecordMetadata().topic(),
        result.getRecordMetadata().offset());
  }
}
