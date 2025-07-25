package br.com.algaworks.algadelivery.couriermanagement.domain.service;

import br.com.algaworks.algadelivery.couriermanagement.api.model.CourierInput;
import br.com.algaworks.algadelivery.couriermanagement.domain.model.Courier;
import br.com.algaworks.algadelivery.couriermanagement.domain.repository.CourierRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

  private final CourierRepository courierRepository;

  public Courier create(@Valid CourierInput input) {
    Courier courier = Courier.brandNew(input.getName(), input.getPhone());
    return courierRepository.saveAndFlush(courier);
  }

  public Courier update(UUID courierId, @Valid CourierInput input) {
    Courier courier = courierRepository.findById(courierId).orElseThrow();
    courier.setPhone(input.getPhone());
    courier.setName(input.getName());
    return courierRepository.saveAndFlush(courier);
  }
}
