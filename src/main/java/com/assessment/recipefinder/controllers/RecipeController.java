package com.assessment.recipefinder.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assessment.recipefinder.dto.IncludeType;
import com.assessment.recipefinder.dto.RecipeDto;
import com.assessment.recipefinder.entities.Recipe;
import com.assessment.recipefinder.service.RecipeService;
import com.assessment.recipefinder.util.RecipeCriteria;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("recipes")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;

	@Operation(summary = "Get a list of recipes based on params.")
	@GetMapping("/")
	public List<Recipe> getRecipes(
			@RequestParam Optional<Integer> numberOfServings,
			@RequestParam Optional<String> ingredients,
			@RequestParam(defaultValue = "INCLUDE") Optional<IncludeType> includeType,
			@RequestParam Optional<String> searchTerm,
			@RequestParam Optional<Boolean> isVegeterian
			) {
		
		RecipeCriteria filter = new RecipeCriteria(numberOfServings, ingredients, includeType, searchTerm, isVegeterian);
		
		return recipeService.getRecipes(filter);
	}
	
	@Operation(summary = "Get a recipe by id.")
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
		
		Optional<Recipe> entity = recipeService.getRecipeById(id);
		
		if(entity.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(entity.get());
	}
	
	@Operation(summary = "Adds a new recipe and returns the location in header.")
	@PostMapping("/")
	public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeDto recipe) {
		Recipe entity = recipeService.addNewRecipe(recipe);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entity.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@Operation(summary = "Update a recipe by its id.")
	@PutMapping("/{id}")
	public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeDto recipe, @PathVariable long id){
		
		Optional<Recipe> entity = recipeService.getRecipeById(id);
		
		if(entity.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		recipeService.updateRecipe(entity.get(), recipe);
		
		return ResponseEntity.accepted().build();
	}
	
	@Operation(summary = "Removes the recipe.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteRecipe(@PathVariable long id){
		
		Optional<Recipe> entity = recipeService.getRecipeById(id);
		
		if(entity.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		recipeService.deleteRecipe(entity.get());
		
		return ResponseEntity.noContent().build();
	}

}