package com.jostens.ytoconduit.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class YTOCategoryDefinition implements Serializable {

	private static final long serialVersionUID = -4590574195419544210L;
	
	@JsonView(View.DefinitionSummary.class)
	private Long categoryId;
	@JsonView(View.DefinitionSummary.class)
	private String categoryName;
	@JsonView(View.DefinitionSummary.class)
	private List<YTOImageDefinition> imageDefinition;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<YTOImageDefinition> getImageDefinition() {
		return imageDefinition;
	}

	public void setImageDefinition(List<YTOImageDefinition> imageDefinition) {
		this.imageDefinition = imageDefinition;
	}

	public YTOCategoryDefinition() {
		// TODO Auto-generated constructor stub
	}

}
