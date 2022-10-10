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
import com.assessment.recipefinder.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipefinderApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RecipeService recipeService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void createNewRecipeIsSuccessful() throws Exception {
		
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
		
		Optional<Recipe> entity = recipeService.getRecipeById(Long.parseLong(location[location.length - 1].trim()));
		
		assertNotNull(entity);
		assertThat(entity.get().getName()).isEqualTo(dto.getName());
	}
	
	@Test
	public void getRecipeByIdReturnsCorrectRecipe() throws Exception {
		
		addRecipe();
		
		MvcResult result = mockMvc.perform(get("/recipes/1", 42L)
	            .contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		
		Optional<Recipe> entity = recipeService.getRecipeById(1);
		
		String response = result.getResponse().getContentAsString();
		
		Recipe got = objectMapper.readValue(response, Recipe.class);
		
		assertNotNull(got);
		assertThat(entity.get().getName()).isEqualTo(got.getName());
	}
	
	@Test
	public void updateRecipeReturnsUpdatedValue() throws Exception {
		
		addRecipe();
		
		RecipeDto dto = new RecipeDto("Malai Kofta Curry", RecipeType.VEGETERIAN, null, 5, "Mix potatoes and ricotta and fry in hot oil. Add to curry.");
		
		 mockMvc.perform(put("/recipes/1", 42L)
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(dto)))
	            .andExpect(status().isAccepted());
		
		Optional<Recipe> entity = recipeService.getRecipeById(1);
		
		assertNotNull(entity);
		assertThat(entity.get().getName()).isEqualTo(dto.getName());
		assertThat(entity.get().getServes()).isEqualTo(dto.getServes());
		
	}
	
	@Test
	public void deleteRecipeRemovesEntity() throws Exception {
		
		addRecipe();
		
		mockMvc.perform(delete("/recipes/1", 42L))
	            .andExpect(status().isNoContent());
		
		Optional<Recipe> entity = recipeService.getRecipeById(1);
		
		assertThat(entity.isEmpty()).isEqualTo(true);		
	}
	
	private void addRecipe() throws Exception {
		List<IngredientDto> ingredients = new ArrayList<IngredientDto>();
		ingredients.add(new IngredientDto("Ricotta", "1 cup"));
		ingredients.add(new IngredientDto("Mashed Potatoes", "1 cup"));
		ingredients.add(new IngredientDto("Oil", "4 tbsp"));
		
		RecipeDto dto = new RecipeDto("Malai Kofta", RecipeType.VEGETERIAN, ingredients, 4, "Mix potatoes and ricotta and fry in hot oil.");
		
		recipeService.addNewRecipe(dto);
	}
}
