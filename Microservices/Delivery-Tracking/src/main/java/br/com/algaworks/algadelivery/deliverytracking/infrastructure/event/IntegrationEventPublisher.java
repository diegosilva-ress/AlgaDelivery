package br.com.algaworks.algadelivery.deliverytracking.infrastructure.event;

public interface IntegrationEventPublisher {

  void publish(Object event, String key, String topic);

}
