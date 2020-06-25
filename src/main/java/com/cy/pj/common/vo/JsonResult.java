package com.cy.pj.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO:view Object/Value Object ,在当前项目中我们使用vo封装视图层要表现的数据
 *
 */
@Data
@NoArgsConstructor
public class JsonResult {
	//消息状态码
	private Integer state=1;
	//状态码对应的信息
	private String message="ok";
	//数据(业务层返回的数据)
	private Object data;
	public JsonResult(String message) {
		this.message=message;
	}
	public JsonResult(Object data) {
		this.data=data;
	}
	public JsonResult(Throwable e) {
		this.state=0;
		this.message=e.getMessage();
	}	
}
