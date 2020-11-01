package com.example.sbs.lolHi.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int id;
	private int relId;
	private int memberId;
	private String regDate;
	private String updateDate;
	private String body;
	private String relTypeCode;
	
	private Map<String, Object> extra;
}
