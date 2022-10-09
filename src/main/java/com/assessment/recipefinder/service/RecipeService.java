package com.assessment.recipefinder.service;

import java.util.List;
import java.util.Optional;

import com.assessment.recipefinder.dto.RecipeDto;
import com.assessment.recipefinder.entities.Recipe;
import com.assessment.recipefinder.util.RecipeCriteria;

public interface RecipeService {

	public Recipe addNewRecipe(RecipeDto dto);
	List<Recipe> getRecipes(RecipeCriteria criteria);
	Optional<Recipe> getRecipeById(long id);
	Recipe updateRecipe(Recipe entity, RecipeDto dto);
	void deleteRecipe(Recipe entity);
}
