package com.example.sbs.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board {
	private int id;
	private String regDate;
	private String name;
	private String code;
}
