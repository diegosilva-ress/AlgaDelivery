package br.com.algaworks.algadelivery.deliverytracking.infrastructure.http.client;

import br.com.algaworks.algadelivery.deliverytracking.domain.service.CourierPayoutCalculationService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

  private final CourierAPIClient courierAPIClient;

  @Override
  public BigDecimal calculatePayout(Double distanceInKm) {
    try {
      var courierPayoutResultModel = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
      return courierPayoutResultModel.getPayoutFee();
    } catch (ResourceAccessException e) {
      throw new GatewayTimeoutException(e);
    } catch (HttpServerErrorException | CallNotPermittedException | IllegalArgumentException e) {
      throw new BadGatewayException(e);
    }
  }
}
