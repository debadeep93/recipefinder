package com.assessment.recipefinder.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue
	private Long id;

	@Basic
	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Basic
	@Column(name = "amount", nullable = false, length = 256)
	private String amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	@JsonIgnore
	private Recipe recipe;

	@Override
	public boolean equals(Object object) {

		Ingredient i = (Ingredient) object;

		if (i.name.equalsIgnoreCase(this.name) && i.amount.equalsIgnoreCase(this.amount)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.amount.hashCode();
	}
}
