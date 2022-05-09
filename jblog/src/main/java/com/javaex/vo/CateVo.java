package com.javaex.vo;

import lombok.Data;

@Data
public class CateVo {
	private int cateNo;
	private String id;
	private String cateName;
	private String description;
	private String regDate;
	
	private int userNo;
}
