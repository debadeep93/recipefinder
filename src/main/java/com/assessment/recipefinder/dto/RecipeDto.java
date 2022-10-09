package com.assessment.recipefinder.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.assessment.recipefinder.entities.RecipeType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Component
public class RecipeDto {

	private String name;
	
	private RecipeType type;

	private List<IngredientDto> ingredients;

	private Integer serves;

	private String instructions;

}
