package br.com.algaworks.algadelivery.deliverytracking.infrastructure.http.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutCalculationInput {
  private Double distanceInKm;
}
