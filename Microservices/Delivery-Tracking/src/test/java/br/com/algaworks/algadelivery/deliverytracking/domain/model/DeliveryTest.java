package br.com.algaworks.algadelivery.deliverytracking.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.algaworks.algadelivery.deliverytracking.domain.exception.DomainException;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery.PreparationDetails;
import java.math.BigDecimal;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class DeliveryTest {

  @Test
  public void shouldChangeToPlaced() {
    Delivery delivery = Delivery.draft();

    delivery.editPreparationDetails(createValidPreparationDetails());

    delivery.place();

    assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
    assertNotNull(delivery.getPlacedAt());
  }

  @Test
  public void shouldNotChangeToPlace() {
    Delivery delivery = Delivery.draft();

    assertThrows(DomainException.class, delivery::place);

    assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
    assertNull(delivery.getPlacedAt());
  }

  private PreparationDetails createValidPreparationDetails() {
    ContactPoint sender = ContactPoint.builder()
        .zipCode("12345-678")
        .street("Rua das Flores")
        .number("100")
        .complement("Casa 10")
        .name("John Doe")
        .phone("11999999999")
        .build();

    ContactPoint recipient = ContactPoint.builder()
        .zipCode("4841685-678")
        .street("Rua das Rosas")
        .number("05")
        .name("John Pizza")
        .phone("118888888888")
        .build();

    return Delivery.PreparationDetails.builder()
        .sender(sender)
        .recipient(recipient)
        .distanceFee(new BigDecimal("10.00"))
        .courierPayout(new BigDecimal("5.00"))
        .expectedDeliveryTime(Duration.ofHours(5))
        .build();
  }

}