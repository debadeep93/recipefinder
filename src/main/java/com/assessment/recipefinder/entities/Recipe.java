package com.assessment.recipefinder.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Entity
@Table(name = "recipes")
public class Recipe {

	@Id
	@GeneratedValue
	private Long id;

	@Basic
	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type", nullable = false)
	private RecipeType type;

	@Basic
	@Column(name = "serves")
	private Integer serves;

	@Basic
	@Column(name = "instructions", nullable = true, length = 1024)
	private String instructions;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
	private Set<Ingredient> ingredients;

}