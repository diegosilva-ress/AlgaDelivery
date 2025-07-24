package br.com.algaworks.algadelivery.deliverytracking.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeliveryStatusTest {

  @Test
  void draft_canChangeToWaitingForCourier() {
    assertTrue(DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER));
  }

  @Test
  void draft_canNotChangeToInTransit() {
    assertTrue(DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.IN_TRANSIT));
  }

}