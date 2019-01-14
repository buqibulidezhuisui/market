package com.market.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 响应 值对象.
 */
@Getter
@Setter
@AllArgsConstructor
public class Response {
	
	
	private boolean success;
	private String message;
	private Object body;

	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}


}
