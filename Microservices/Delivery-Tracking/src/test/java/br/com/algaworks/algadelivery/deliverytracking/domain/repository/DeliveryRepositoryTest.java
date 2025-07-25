package br.com.algaworks.algadelivery.deliverytracking.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.algaworks.algadelivery.deliverytracking.domain.model.ContactPoint;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery.PreparationDetails;
import java.math.BigDecimal;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {
  
  @Autowired
  private DeliveryRepository deliveryRepository;

  
  @Test
  public void shouldPersist() {
    Delivery delivery = Delivery.draft();
    
    delivery.editPreparationDetails(createValidPreparationDetails());
    
    delivery.addItem("Computador", 2);
    delivery.addItem("Notebook", 2);
    
    deliveryRepository.saveAndFlush(delivery);

    Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

    assertEquals(2, persistedDelivery.getItems().size());

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