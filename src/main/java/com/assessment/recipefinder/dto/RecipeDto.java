package com.assessment.recipefinder.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.assessment.recipefinder.entities.RecipeType;

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
public class RecipeDto {

	private String name;
	
	private RecipeType type;

	private List<IngredientDto> ingredients;

	private Integer serves;

	private String instructions;
	
	public RecipeDto(String name, RecipeType type, List<IngredientDto> ingredients, Integer serves, String instructions) {
		this.name = name;
		this.type = type;
		this.ingredients = ingredients;
		this.serves = serves;
		this.instructions = instructions;
	}

}
