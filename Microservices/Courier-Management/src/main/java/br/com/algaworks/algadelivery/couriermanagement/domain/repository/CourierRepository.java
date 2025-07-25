package br.com.algaworks.algadelivery.couriermanagement.domain.repository;

import br.com.algaworks.algadelivery.couriermanagement.domain.model.Courier;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

}
