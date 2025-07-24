package br.com.algaworks.algadelivery.couriermanagement.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Courier {

  @EqualsAndHashCode.Include
  private UUID id;

  @Setter(AccessLevel.PUBLIC)
  private String name;

  @Setter(AccessLevel.PUBLIC)
  private String phone;

  private Integer fulFilledDeliveriesQuantity;

  private Integer pendingDeliveriesQuantity;

  private OffsetDateTime lastFulfilledDeliveryAt;

  private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();

  public List<AssignedDelivery> getPendingDeliveries() {
    return Collections.unmodifiableList(this.pendingDeliveries);
  }

  public static Courier brandNew(String name, String phone) {
    Courier courier = new Courier();

    courier.setId(UUID.randomUUID());
    courier.setName(name);
    courier.setPhone(phone);
    courier.setFulFilledDeliveriesQuantity(0);
    courier.setPendingDeliveriesQuantity(0);

    return courier;
  }

  public void assign(UUID deliveryId) {
    this.pendingDeliveries.add(AssignedDelivery.pending(deliveryId));
    this.pendingDeliveriesQuantity++;
  }

  public void fulfilled(UUID deliveryId) {
    AssignedDelivery delivery = this.pendingDeliveries.stream()
        .filter(d -> d.getId().equals(deliveryId))
        .findFirst()
        .orElseThrow();

    this.pendingDeliveries.remove(delivery);
    this.pendingDeliveriesQuantity--;
    this.fulFilledDeliveriesQuantity++;
    this.lastFulfilledDeliveryAt = OffsetDateTime.now();
  }

}
