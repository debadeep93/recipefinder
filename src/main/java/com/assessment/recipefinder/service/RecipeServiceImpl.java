package com.assessment.recipefinder.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.recipefinder.dto.IngredientDto;
import com.assessment.recipefinder.dto.RecipeDto;
import com.assessment.recipefinder.entities.Ingredient;
import com.assessment.recipefinder.entities.Recipe;
import com.assessment.recipefinder.repositories.IngredientRepository;
import com.assessment.recipefinder.repositories.RecipeRepository;
import com.assessment.recipefinder.util.RecipeCriteria;
import com.assessment.recipefinder.util.RecipeSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Transactional
	public Recipe addNewRecipe(RecipeDto dto) {

		Recipe recipe = new Recipe();

		recipe.setName(dto.getName());
		recipe.setInstructions(dto.getInstructions());
		recipe.setServes(dto.getServes());
		recipe.setType(dto.getType());

		Recipe entity = recipeRepository.save(recipe);

		for (IngredientDto i : dto.getIngredients()) {
			Ingredient ingredient = new Ingredient();

			ingredient.setName(i.getName());
			ingredient.setAmount(i.getAmount());
			ingredient.setRecipe(entity);

			ingredientRepository.save(ingredient);
		}

		return entity;
	}

	public List<Recipe> getRecipes(RecipeCriteria criteria) {

		RecipeSpecification spec = new RecipeSpecification(criteria);

		return recipeRepository.findAll(spec);
	}

	@Transactional
	public Optional<Recipe> getRecipeById(long id) {
		// TODO Auto-generated method stub
		return recipeRepository.findById(id);
	}

	@Transactional
	public Recipe updateRecipe(Recipe entity, RecipeDto dto) {

		// TODO Auto-generated method stub
		entity.setName(dto.getName());
		entity.setInstructions(dto.getInstructions());
		entity.setServes(dto.getServes());
		entity.setType(dto.getType());

		entity = recipeRepository.save(entity);

		// If the ingredients list consists of entries, replace old set of ingredients
		// with new
		if (dto.getIngredients() != null && dto.getIngredients().size() > 0) {
			ingredientRepository.deleteAll(entity.getIngredients());

			for (IngredientDto i : dto.getIngredients()) {

				Ingredient ingredient = new Ingredient();
				ingredient.setName(i.getName());
				ingredient.setAmount(i.getAmount());
				ingredient.setRecipe(entity);

				ingredientRepository.save(ingredient);
			}
		}

		return entity;
	}

	@Transactional
	public void deleteRecipe(Recipe entity) {

		if (entity.getIngredients() != null && entity.getIngredients().size() > 0) {
			ingredientRepository.deleteAll(entity.getIngredients());
		}

		recipeRepository.delete(entity);
	}

}
