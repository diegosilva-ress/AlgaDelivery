package br.com.algaworks.algadelivery.deliverytracking.domain.service;

import br.com.algaworks.algadelivery.deliverytracking.api.model.ContactPointInput;
import br.com.algaworks.algadelivery.deliverytracking.api.model.DeliveryInput;
import br.com.algaworks.algadelivery.deliverytracking.domain.exception.DomainException;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.ContactPoint;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery;
import br.com.algaworks.algadelivery.deliverytracking.domain.repository.DeliveryRepository;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

  private final DeliveryRepository deliveryRepository;

  @Transactional
  public Delivery draft(DeliveryInput input) {
    Delivery delivery = Delivery.draft();

    handlePreparation(input, delivery);

    return deliveryRepository.saveAndFlush(delivery);
  }

  @Transactional
  public Delivery edit(UUID deliveryId, DeliveryInput input) {
    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);

    delivery.removeItems();
    handlePreparation(input, delivery);

    return deliveryRepository.saveAndFlush(delivery);
  }

  private void handlePreparation(DeliveryInput input, Delivery delivery) {
    ContactPointInput senderInput = input.getSender();
    ContactPointInput recipientInput = input.getRecipient();

    ContactPoint sender = ContactPoint.builder()
        .phone(senderInput.getPhone())
        .name(senderInput.getName())
        .complement(senderInput.getComplement())
        .number(senderInput.getNumber())
        .zipCode(senderInput.getZipCode())
        .street(senderInput.getStreet())
        .build();

    ContactPoint recipient = ContactPoint.builder()
        .phone(recipientInput.getPhone())
        .name(recipientInput.getName())
        .complement(recipientInput.getComplement())
        .number(recipientInput.getNumber())
        .zipCode(recipientInput.getZipCode())
        .street(recipientInput.getStreet())
        .build();

    Duration expectedDeliveryTime = Duration.ofHours(3);

    BigDecimal payout = new BigDecimal("10.00");
    BigDecimal distanceFee = new BigDecimal("10.00");

    var preparationDetails = Delivery.PreparationDetails.builder()
        .recipient(recipient)
        .sender(sender)
        .expectedDeliveryTime(expectedDeliveryTime)
        .courierPayout(payout)
        .distanceFee(distanceFee)
        .build();

    delivery.editPreparationDetails(preparationDetails);

    input.getItems().forEach(item -> {
      delivery.addItem(item.getName(), item.getQuantity());
    });

  }


}
