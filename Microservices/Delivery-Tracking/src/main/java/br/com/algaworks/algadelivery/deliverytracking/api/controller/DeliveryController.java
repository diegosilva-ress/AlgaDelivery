package br.com.algaworks.algadelivery.deliverytracking.api.controller;

import br.com.algaworks.algadelivery.deliverytracking.api.model.CourierIdInput;
import br.com.algaworks.algadelivery.deliverytracking.api.model.DeliveryInput;
import br.com.algaworks.algadelivery.deliverytracking.domain.model.Delivery;
import br.com.algaworks.algadelivery.deliverytracking.domain.repository.DeliveryRepository;
import br.com.algaworks.algadelivery.deliverytracking.domain.service.DeliveryCheckpointService;
import br.com.algaworks.algadelivery.deliverytracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryPreparationService deliveryPreparationService;
  private final DeliveryCheckpointService deliveryCheckpointService;
  private final DeliveryRepository deliveryRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Delivery draft(@RequestBody @Valid DeliveryInput deliveryInput) {
    return deliveryPreparationService.draft(deliveryInput);
  }

  @PutMapping("/{deliveryId}")
  public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput deliveryInput) {
    return deliveryPreparationService.edit(deliveryId, deliveryInput);
  }

  @GetMapping
  public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable) {
    return new PagedModel<>(
        deliveryRepository.findAll(pageable));
  }

  @GetMapping("/{deliveryId}")
  public Delivery findById(@PathVariable UUID deliveryId) {
    return deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResponseStatusException(HttpStatus
        .NOT_FOUND));
  }

  @PostMapping("/{deliveryId}/placement")
  public void place(@PathVariable UUID deliveryId) {
    deliveryCheckpointService.place(deliveryId);
  }

  @PostMapping("/{deliveryId}/pickups")
  public void pickup(@PathVariable UUID deliveryId,
      @Valid @RequestBody CourierIdInput input) {
    deliveryCheckpointService.pickUp(deliveryId, input.getCourierId());
  }

  @PostMapping("/{deliveryId}/completion")
  public void complete(@PathVariable UUID deliveryId) {
    deliveryCheckpointService.complete(deliveryId);
  }

}
