package com.tm.app.dto;

import org.springframework.data.domain.Sort.Direction;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DataFilter {
	private static final String COLUMN_UPDATED_AT = "updatedAt";
	private static final String SEARCH_STRING = "%";
	@NotNull
	private Integer page;
	@NotNull
	private Integer size;
	private String search;
	private String sortByField;
	@NotNull
	private Direction sortBy;

	/**
	 * Get search is empty  
	 * @return SEARCH_STRING
	 */
	public String getSearch() {
		if (StringUtils.isEmpty(search)) {
			return SEARCH_STRING;
		} else {
			return SEARCH_STRING+ search + SEARCH_STRING;
		}
	}

	/**
	 * Get sortByField is empty
	 * @return COLUMN_UPDATED_AT
	 */
	public String getSortByField() {
		if (StringUtils.isEmpty(sortByField)) {
			return COLUMN_UPDATED_AT;
		} else {
			return sortByField;
		}
	}
}