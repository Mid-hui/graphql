package com.cy.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.cy.graphql.config.SystemResult;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class GraphqlTest {
		//springboot测试路径 http://localhost:9999/graphiql    
	
	   	@Autowired
	    private GraphQLTestTemplate graphqlTemplate;
	   	
	   	@Autowired
	   	private GraphQL graphQL;
	   	
	   	public Map<String,String> initMap(String query){
		   	 Map<String,String> authMap = new HashMap<String,String>();
		   	 authMap.put("role","manager");
		   	 ExecutionInput executionInput = ExecutionInput.newExecutionInput()
		   			.query(query)
		            .context(authMap)
		            .build();
		   	ExecutionResult result = graphQL.execute(executionInput);
		   	System.out.println(SystemResult.success(result.toSpecification()));
		   	return authMap;
		  }
	 	/**
	 	 * 查询(一个请求完成四次查询)
	 	 * 
	 	 * 1.根据id查询
	 	 * 2.查询全部
	 	 * 3.分页查询
	 	 * 4.模糊查询
	 	 */
	    @Test
	    public void testByQuery(){
	        try {
	            GraphQLResponse response = this.graphqlTemplate.postForResource("graphql/user.graphql");
	            Assertions.assertNotNull(response);
	            Assertions.assertTrue(response.isOk());
	            System.out.println(response.get("$.data.user.id"));
	            //System.out.println(response.get("$.data.userAll[0].id"));
	            System.out.println(response.context().jsonString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
//	    /**
//	           *根据id查询用户
//	     */
//	    @Test
//	    public void queryUserById(){
//	    	String query = "query{user(id:1){id,name,age}}";
//	    	initMap(query);
//	    }
	    /**
	     *根据id查询用户
	     */
	    @Test
	    public void queryUserById(){
	    	String query = "query{user(user:{id:1}){id,name,age}}";
	    	initMap(query);
	    }
	    
	    /**
	            * 查询所有的用户
	     */
	    @Test
	    public void queryAllUser(){
	    	//String query = "query{users(user:{}){id,name,age}}";//查询所有（报错）
	    	//String query = "query{users(user:{page:0,pageSize:3}){id,name,age}}";//分页查询
	    	String query = "query{users(user:{page:0,pageSize:3,name:\"六\"}){id,name,age}}";//分页+模糊查询
	    	initMap(query);
	    }
	    
	    /**
	            *根据id删除用户
	     */
	    @Test
	    public void deleteUserById(){
	    	String delete = "mutation{user(id:11){id}}";
			ExecutionResult result = graphQL.execute(delete); 
			System.out.println("delete:" +delete);
			//成功返回的信息
			System.out.println(SystemResult.success(result.toSpecification()));
	     }
	    
	    /**
	            * 添加用户信息
	     */
	    @Test
	    public void insertUser(){
	    	//插入的方式1
			//String insert ="mutation{createUser(id:7777,name:\"七七七七\",age:7777){id,name,age}}";
	    	//插入的方式2
	    	String insert = "mutation{createUser(user:{name:\"哈哈2\",age:13000}){name,age}}";
			ExecutionResult result = graphQL.execute(insert);
			System.out.println("insert:" +insert);
			System.out.println(SystemResult.success(result.toSpecification()));
		}
	    
		/**
		  * 根据id更新用户信息
		 */
	    @Test
		public void update(){
	    	//String update = "mutation{createUser(user:{id:1111,name:\"意义衣衣\",age:1111}){id,name,age}}";
	    	String update = "mutation{modifyUser(id:10000,name:\"yiwangyi\",age:11111){id,name,age}}";
			ExecutionResult result = graphQL.execute(update);
			System.out.println("insert:" +update);
			System.out.println(SystemResult.success(result.toSpecification()));
		}
	
}
