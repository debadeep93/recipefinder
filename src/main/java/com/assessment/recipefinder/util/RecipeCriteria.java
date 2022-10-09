package com.assessment.recipefinder.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.assessment.recipefinder.dto.IncludeType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RecipeCriteria {
	
	private Optional<Integer> numberOfServings;
	private Optional<String> ingredients;
	private Optional<IncludeType> includeType;
	private Optional<String> searchTerm;
	private Optional<Boolean> isVegeterian;
	
	public RecipeCriteria(
			Optional<Integer> numberOfServings,
			Optional<String> ingredients,
			Optional<IncludeType> includeType,
			Optional<String> searchTerm,
			Optional<Boolean> isVegeterian
			) {
		this.numberOfServings = numberOfServings;
		this.ingredients = ingredients;
		this.includeType = includeType;
		this.searchTerm = searchTerm;
		this.isVegeterian = isVegeterian;
	}
	
	public List<String> getIngredientsAsList(){
		List<String> parts = this.ingredients.isPresent() ? Arrays.asList(this.ingredients.get().toLowerCase().split(",")) : Collections.emptyList();
		
		return parts;
	}
}
