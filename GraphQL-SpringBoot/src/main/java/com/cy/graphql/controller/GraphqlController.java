package com.cy.graphql.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.graphql.config.SystemResult;
import com.cy.graphql.vo.User;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

@RestController
public class GraphqlController {

	@Autowired(required = false)
	private GraphQL graphQL;

	/**
	 * 根据id执行查询
	 * @param id
	 * @return
	 */
	@RequestMapping("/user/{id}")
	public SystemResult getUserById(@PathVariable Integer id){
		//String query = "query{user(user:{id:"++id"}){id,name,age}}";
		String query = "query{user(user:{id:"+id+"}){id,name,age}}";
		Map<String,String> authMap = new HashMap<String,String>();
		authMap.put("role","manager");
		ExecutionInput executionInput = ExecutionInput.newExecutionInput()
				.query(query)
				.context(authMap)
				.build();
		ExecutionResult result = graphQL.execute(executionInput);
		return SystemResult.success(result.toSpecification());
	}


//	@RequestMapping("/all/{pageCount}/{pageSize}")
	@RequestMapping("/all")
	public SystemResult getAllUser( ){
		//String query = "query{users(user:{}){id,name,age}}";//查询所有
		String query ="query{users(user:{page:0,pageSize:3}){id,name,age}}";//分页查询
		//String query ="query{users(user:{page:0,pageSize:3,name:\"六\"}){id,name,age}}";//分页+模糊查询
		Map<String,String> authMap = new HashMap<String,String>();
		authMap.put("role","manager");
		ExecutionInput executionInput = ExecutionInput.newExecutionInput()
				.query(query)
				.context(authMap)
				.build();
		ExecutionResult result = graphQL.execute(executionInput);
		return SystemResult.success(result.toSpecification());
	}

	/**
	 * 根据id执行删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public SystemResult deleteUserById(@PathVariable Integer id){
		String delete = "mutation{user(id:"+id+"){id}}";
		ExecutionResult result = graphQL.execute(delete); 
		//成功返回的信息
		return SystemResult.success(result.toSpecification());
	}

	/**
	 *添加用户 
	 * @param user
	 */
	@RequestMapping("/insert")
	public SystemResult insertUser(User user){
		String insert = "mutation{createUser(user:{name:"+user.getName()+",age:"+user.getAge()+"}){name,age}}";
		ExecutionResult result = graphQL.execute(insert);
		return SystemResult.success(result.toSpecification());
	}

	/**
	 * 更新用户 
	 *@param user
	 */
	@RequestMapping("/update")
	public SystemResult updateUser(User user){
		String update = "mutation{modifyUser(id:"+user.getId()+",name:"+user.getName()+",age:"+user.getAge()+"){id,name,age}}";
		ExecutionResult result = graphQL.execute(update);
		return SystemResult.success(result.toSpecification());
	}


}
