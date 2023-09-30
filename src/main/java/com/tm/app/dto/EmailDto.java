package com.tm.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDto {
	private String toAddress;
	private String htmlCondent;
}
