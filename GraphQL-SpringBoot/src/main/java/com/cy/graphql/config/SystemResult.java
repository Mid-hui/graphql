package com.cy.graphql.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SystemResult {
	/*
	 * 策略说明: 
	 * 		属性1: status==200 调用正确 status==201 调用失败 
	 * 		属性2: msg 提交服务器相关说明信息 
	 * 		属性3: data 服务器返回页面的业务数据 一般都是对象的JSON.
	 */
	private Integer status; 
	private String msg;
	private Object data;
	
		//准备工具API 方便使用
	
		//失败方式1
		public static SystemResult  fail() {
			return new SystemResult(201, "业务调用失败", null);
		}
		
		//失败方式2
		public static SystemResult fail(Object data) {
			
			return new SystemResult(201, "业务调用失败!!!", data);
		}
		
		
		//成功方式1 只返回状态码信息
		public static SystemResult success() {
			
			return new SystemResult(200, "业务调用成功!!!", null);
		}
		
		//成功方式2 需要返回服务器数据 data
		public static SystemResult success(Object data) {
			
			return new SystemResult(200, "业务调用成功!!!", data);
		}
		
		//成功方式3  可能告知服务器信息及 服务器数据
		public static SystemResult success(String msg, Object data) {
				
				return new SystemResult(200, msg , data);
		}
}
