package br.com.algaworks.algadelivery.couriermanagement.domain.repository;

import br.com.algaworks.algadelivery.couriermanagement.domain.model.Courier;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

  Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryAtAsc();

  Optional<Courier> findByPendingDeliveries_id(UUID deliveryId);
}
