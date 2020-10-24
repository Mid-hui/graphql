package com.cy.graphql.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.graphql.config.SystemResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice //advice通知   返回的数据都是json串
@Slf4j	//添加日志
public class GlobalExceptionHandler {
	/*
	 * 添加通用异常返回的方法.
	 * 底层原理:AOP的异常通知.
	 * */
	@ExceptionHandler({RuntimeException.class}) //拦截运行时异常
	@ResponseBody
	public Object systemResultException(Exception exception) {
		
		//exception.printStackTrace(); //如果有问题,则直接在控制台打印
		log.error("{~~~~~~"+exception.getMessage()+"}", exception); //输出日志
		return SystemResult.fail(exception);	 //返回统一的失败数据
	}
	
	
}
