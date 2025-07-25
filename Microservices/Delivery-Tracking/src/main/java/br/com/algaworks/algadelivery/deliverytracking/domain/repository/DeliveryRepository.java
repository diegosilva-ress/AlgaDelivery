package br.com.algaworks.algadelivery.deliverytracking.domain.repository;

import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

}
