package com.jostens.ytoconduit.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class YTODesignDefinition implements Serializable {

	private static final long serialVersionUID = -5573669467452311555L;
	
	private int index;
	private Long designId;
	private List<YTOImageDefinition> imageDefinition;
	
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

	public List<YTOImageDefinition> getImageDefinition() {
		return imageDefinition;
	}

	public void setImageDefinition(List<YTOImageDefinition> imageDefinition) {
		this.imageDefinition = imageDefinition;
	}


}
