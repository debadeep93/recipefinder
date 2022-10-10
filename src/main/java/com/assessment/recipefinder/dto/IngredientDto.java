package com.assessment.recipefinder.dto;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@Component
public class IngredientDto {
	
	private String name;
	
	private String amount;
	
	public IngredientDto(String name, String amount) {
		this.name = name;
		this.amount = amount;
	}
}
