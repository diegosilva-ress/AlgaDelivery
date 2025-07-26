package br.com.algaworks.algadelivery.deliverytracking.infrastructure.event;

import br.com.algaworks.algadelivery.deliverytracking.domain.event.DeliveryFulfilledEvent;
import br.com.algaworks.algadelivery.deliverytracking.domain.event.DeliveryPickUpEvent;
import br.com.algaworks.algadelivery.deliverytracking.domain.event.DeliveryPlacedEvent;
import br.com.algaworks.algadelivery.deliverytracking.infrastructure.kafka.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

  private final IntegrationEventPublisher eventPublisher;

  @EventListener
  public void handle(DeliveryPlacedEvent event) {
    log.info(event.toString());
    eventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.DELIVERY_TRACKING_TOPIC);
  }

  @EventListener
  public void handle(DeliveryPickUpEvent event) {
    log.info(event.toString());
    eventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.DELIVERY_TRACKING_TOPIC);
  }

  @EventListener
  public void handle(DeliveryFulfilledEvent event) {
    log.info(event.toString());
    eventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.DELIVERY_TRACKING_TOPIC);
  }

}
