package br.com.algaworks.algadelivery.deliverytracking.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemInput {

  @NotBlank
  private String name;

  @Min(1)
  private Integer quantity;

}
