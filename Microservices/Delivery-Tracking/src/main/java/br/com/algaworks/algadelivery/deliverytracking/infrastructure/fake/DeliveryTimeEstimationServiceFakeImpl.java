package br.com.algaworks.algadelivery.deliverytracking.infrastructure.fake;

import br.com.algaworks.algadelivery.deliverytracking.domain.model.ContactPoint;
import br.com.algaworks.algadelivery.deliverytracking.domain.service.DeliveryEstimate;
import br.com.algaworks.algadelivery.deliverytracking.domain.service.DeliveryTimeEstimationService;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {
  @Override
  public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
    return new DeliveryEstimate(
        Duration.ofHours(3),
        3.1
    );
  }
}