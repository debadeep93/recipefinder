package com.assessment.recipefinder;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.assessment.recipefinder.service.RecipeService;
import com.assessment.recipefinder.service.RecipeServiceImpl;

@TestConfiguration
public class RecipefinderTestConfiguration {

	@Bean
	public RecipeService recipeService() {
		return new RecipeServiceImpl();
	}
}
