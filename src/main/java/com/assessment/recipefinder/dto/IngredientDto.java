package com.assessment.recipefinder.dto;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Component
public class IngredientDto {
	
	private String name;
	
	private String amount;
}
