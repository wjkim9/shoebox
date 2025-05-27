package com.test.shoebox.entity;

import com.test.shoebox.dto.CategoriesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_seq_generator")
	@SequenceGenerator(name = "categories_seq_generator", sequenceName = "categories_seq", allocationSize = 1)
	@Column(name = "categories_id")
	private Long categoriesId;

	@Column(name = "categories_name", nullable = false, length = 100)
	private String categoriesName;

	@Column(name = "pic_name", length = 200)
	private String picName;

	public CategoriesDTO toDTO() {
		return CategoriesDTO.builder().categoriesId(this.categoriesId).categoriesName(this.categoriesName)
				.picName(this.picName).build();
	}
}
