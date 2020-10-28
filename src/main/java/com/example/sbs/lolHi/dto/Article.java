package com.example.sbs.lolHi.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	private int id;
	private int memberId;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	
	private Map<String, Object> extra;
	
}
