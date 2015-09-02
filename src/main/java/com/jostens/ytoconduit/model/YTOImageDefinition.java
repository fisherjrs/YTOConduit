package com.jostens.ytoconduit.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

public class YTOImageDefinition implements Serializable {
	
	private static final long serialVersionUID = -3734695183680485787L;
	
	@JsonView(View.DefinitionSummary.class)
	private int index;
	@JsonView(View.DefinitionSummary.class)
	private Long imageId;
	@JsonView(View.DefinitionSummary.class)
	private String guid;
	@JsonView(View.DefinitionSummary.class)
	private String url;
	@JsonView(View.DefinitionSummary.class)
	private String imageName;
	@JsonView(View.DefinitionSummary.class)
	private Long categoryId;
	@JsonView(View.DefinitionSummary.class)
	private String categoryName;
	
	public YTOImageDefinition() {
		//for JIBX
	}
	
	public YTOImageDefinition(Map<String, Object> map) {
		//setImageId(getLongColumn(map, "IMAGE_ID"));
		//setImageName(getStringColumn(map, "IMAGE_NAME"));
		//setGuid(getStringColumn(map,"GUID"));
		//setCategoryId(getLongColumn(map, "CATEGORY_ID"));
		//setCategoryName(getStringColumn(map, "CATEGORY_NAME"));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

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
}
