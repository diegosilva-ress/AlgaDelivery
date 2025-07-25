package br.com.algaworks.algadelivery.deliverytracking.infrastructure.http.client;

import br.com.algaworks.algadelivery.deliverytracking.domain.service.CourierPayoutCalculationService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

  private final CourierAPIClient courierAPIClient;

  @Override
  public BigDecimal calculatePayout(Double distanceInKm) {
    var courierPayoutResultModel = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
    return courierPayoutResultModel.getPayoutFee();
  }
}
