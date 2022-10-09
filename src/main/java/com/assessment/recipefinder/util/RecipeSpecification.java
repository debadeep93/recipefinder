package com.assessment.recipefinder.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.assessment.recipefinder.dto.IncludeType;
import com.assessment.recipefinder.entities.Recipe;
import com.assessment.recipefinder.entities.RecipeType;

public class RecipeSpecification implements Specification<Recipe>{

	private static final long serialVersionUID = -5460284147116719617L;
	private RecipeCriteria criteria;
	
	public RecipeSpecification(RecipeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        if(criteria.getIsVegeterian().isPresent()) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), criteria.getIsVegeterian().get() ? RecipeType.VEGETERIAN : RecipeType.NON_VEGETARIAN)));
        }
        
        if(criteria.getNumberOfServings().isPresent()) {
        	predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get("serves"), criteria.getNumberOfServings().get())));
        }
        
        if(criteria.getSearchTerm().isPresent()) {
        	predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("instructions")), "%"+criteria.getSearchTerm().get().toLowerCase()+ "%")));
        }
        
        if(criteria.getIngredients().isPresent() && criteria.getIncludeType().isPresent()) {
        	if(criteria.getIncludeType().get() == IncludeType.INCLUDE) {
        		predicates.add((criteriaBuilder.and(criteriaBuilder.lower(root.join("ingredients", JoinType.INNER).get("name")).in(criteria.getIngredientsAsList()))));
        	}
        	else {
        		predicates.add((criteriaBuilder.not(criteriaBuilder.lower(root.join("ingredients", JoinType.INNER).get("name")).in(criteria.getIngredientsAsList()))));
        	}
        }
        
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
