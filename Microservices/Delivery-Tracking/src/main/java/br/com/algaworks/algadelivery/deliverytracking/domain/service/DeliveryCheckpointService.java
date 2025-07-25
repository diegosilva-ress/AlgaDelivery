package br.com.algaworks.algadelivery.deliverytracking.domain.service;

import br.com.algaworks.algadelivery.deliverytracking.domain.exception.DomainException;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery;
import br.com.algaworks.algadelivery.deliverytracking.domain.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

  private final DeliveryRepository deliveryRepository;

  public void place(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(DomainException::new);
    delivery.place();
    deliveryRepository.saveAndFlush(delivery);
  }

  public void pickUp(UUID deliveryId, UUID courierId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(DomainException::new);
    delivery.pickUp(courierId);
    deliveryRepository.saveAndFlush(delivery);
  }

  public void complete(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(DomainException::new);
    delivery.markAsDelivered();
    deliveryRepository.saveAndFlush(delivery);
  }
}
