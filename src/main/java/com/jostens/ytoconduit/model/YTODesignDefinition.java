package com.jostens.ytoconduit.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

public class YTODesignDefinition implements Serializable {

	private static final long serialVersionUID = -5573669467452311555L;
	
	@JsonView(View.DefinitionSummary.class)
	private int index;
	@JsonView(View.DefinitionSummary.class)
	private Long designId;
	@JsonView(View.DefinitionSummary.class)
	private List<YTOCategoryDefinition> categoryDefinition;
	
	public YTODesignDefinition() {
		//for JIBX
	}
	
	public YTODesignDefinition(Map<String, Object> map) {
		//setDesignId(getLongColumn(map, "image_id"));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public List<YTOCategoryDefinition> getCategoryDefinition() {
		return categoryDefinition;
	}

	public void setCategoryDefinition(List<YTOCategoryDefinition> categoryDefinition) {
		this.categoryDefinition = categoryDefinition;
	}


}
