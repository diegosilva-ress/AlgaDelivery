package br.com.algaworks.algadelivery.couriermanagement.infrastructure.kafka;

import br.com.algaworks.algadelivery.couriermanagement.domain.service.CourierDeliveryService;
import br.com.algaworks.algadelivery.couriermanagement.infrastructure.event.DeliveryFulfilledIntegrationEvent;
import br.com.algaworks.algadelivery.couriermanagement.infrastructure.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {"delivery.v1.tracking"}, groupId = "courier-management")
@Slf4j
@RequiredArgsConstructor
public class KafkaDeliveriesMessageHandler {

  private final CourierDeliveryService courierDeliveryService;

  @KafkaHandler(isDefault = true)
  public void defaultHandler(@Payload Object object) {
    log.info("Default handler: {}", object);
  }

  @KafkaHandler
  public void handle(@Payload DeliveryPlacedIntegrationEvent event) {
    log.info("Delivery placed: {}", event);
    courierDeliveryService.assign(event.getDeliveryId());
  }

  @KafkaHandler
  public void handle(@Payload DeliveryFulfilledIntegrationEvent event) {
    log.info("Delivery Fulfilled: {}", event);
    courierDeliveryService.fulfill(event.getDeliveryId());
  }

}
