package com.assessment.recipefinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.assessment.recipefinder.dto.IngredientDto;
import com.assessment.recipefinder.dto.RecipeDto;
import com.assessment.recipefinder.entities.Recipe;
import com.assessment.recipefinder.entities.RecipeType;
import com.assessment.recipefinder.repositories.IngredientRepository;
import com.assessment.recipefinder.repositories.RecipeRepository;
import com.assessment.recipefinder.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipefinderIntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@AfterEach
	private void clear() {
		ingredientRepository.deleteAll();
		recipeRepository.deleteAll();
	}
	
	@Test
	public void addingNewRecipe_thenGettingReturnsSuccess() throws Exception {
		
		List<IngredientDto> ingredients = new ArrayList<IngredientDto>();
		ingredients.add(new IngredientDto("Chicken", "200g"));
		ingredients.add(new IngredientDto("Butter", "50 g"));
		ingredients.add(new IngredientDto("Onions", "2 medium"));
		ingredients.add(new IngredientDto("Assorted spices", "NA"));
		
		RecipeDto dto = new RecipeDto("Chicken Butter Masala", RecipeType.NON_VEGETARIAN, ingredients, 3, "Fry chicken with butter. Make curry with onions and spices. Mix.");
		
		MvcResult result = mockMvc.perform(post("/recipes/", 42L)
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(dto)))
	            .andExpect(status().isCreated())
	            .andReturn();
		
		String[] location = result.getResponse().getHeader("location").split("/");
		
		/** GET BY ID */
		MvcResult getResult = mockMvc.perform(get("/recipes/"+location[location.length - 1], 42L)
	            .contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		
		
		String response = getResult.getResponse().getContentAsString();
		
		Recipe got = objectMapper.readValue(response, Recipe.class);
		
		assertNotNull(got);
		assertThat(got.getName()).isEqualTo(dto.getName());
	}
	
	@Test
	public void deleteRecipe_thenGetReturnsNotFound() throws Exception {
		
		addRecipe();
		long id = findFirstId();
		
		mockMvc.perform(delete("/recipes/"+id, 42L))
	            .andExpect(status().isNoContent());
		
		/** GET BY ID */
		mockMvc.perform(get("/recipes/"+id, 42L)
	            .contentType("application/json"))
	            .andExpect(status().isNotFound());
	}
	
	@Test
	public void getAllRecipesReturnsAllResults() throws Exception {
		addRecipes();
		MvcResult result = mockMvc.perform(get("/recipes/", 42L)
	            .contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		List<Recipe> recipes = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Recipe.class));
		
		assertThat(recipes.size()).isEqualTo(2);
		
	}
	
	@Test
	public void filterByVegetarianTypeReturnsOnlyVegeterianRecipe() throws Exception {
		addRecipes();
		MvcResult result = mockMvc.perform(get("/recipes/", 42L)
	            .contentType("application/json")
	            .param("isVegeterian", "true"))
	            .andExpect(status().isOk())
	            .andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		List<Recipe> recipes = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Recipe.class));
		
		assertThat(recipes.size()).isEqualTo(1);
		assertThat(recipes.get(0).getType()).isEqualTo(RecipeType.VEGETERIAN);
	}
	
	private void addRecipe() throws Exception {
		List<IngredientDto> ingredients = new ArrayList<IngredientDto>();
		ingredients.add(new IngredientDto("Ricotta", "1 cup"));
		ingredients.add(new IngredientDto("Mashed Potatoes", "1 cup"));
		ingredients.add(new IngredientDto("Oil", "4 tbsp"));
		
		RecipeDto dto = new RecipeDto("Malai Kofta", RecipeType.VEGETERIAN, ingredients, 4, "Mix potatoes and ricotta and fry in hot oil.");
		
		recipeService.addNewRecipe(dto);
	}
	
	private void addRecipes() throws Exception {
		List<IngredientDto> i1 = new ArrayList<IngredientDto>();
		i1.add(new IngredientDto("Chicken", "200g"));
		i1.add(new IngredientDto("Butter", "50 g"));
		i1.add(new IngredientDto("Onions", "2 medium"));
		i1.add(new IngredientDto("Assorted spices", "NA"));
		
		List<IngredientDto> i2 = new ArrayList<IngredientDto>();
		i2.add(new IngredientDto("Ricotta", "1 cup"));
		i2.add(new IngredientDto("Mashed Potatoes", "1 cup"));
		i2.add(new IngredientDto("Oil", "4 tbsp"));
		
		RecipeDto r1 = new RecipeDto("Chicken Butter Masala", RecipeType.NON_VEGETARIAN, i1, 3, "Fry chicken with butter. Make curry with onions and spices. Mix.");
		RecipeDto r2 = new RecipeDto("Malai Kofta", RecipeType.VEGETERIAN, i2, 4, "Mix potatoes and ricotta and fry in hot oil.");
		
		recipeService.addNewRecipe(r1);
		recipeService.addNewRecipe(r2);
	}
	
	private Long findFirstId() throws Exception {
		return recipeRepository.findAll().get(0).getId();
	}
}
