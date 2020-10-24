package com.cy.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.graphql.mapper.GraphqlEmployeeMapper;
import com.cy.graphql.mapper.GraphqlMapper;
import com.cy.graphql.vo.Employee;
import com.cy.graphql.vo.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphqlMapperTest {
	
	@Autowired
	private GraphqlMapper graphqlMapper;	
	
	@Autowired
	private GraphqlEmployeeMapper graphqlEmployeeMapper;
	
	
	@Test
	public void findUser(){
		User user = graphqlMapper.findUser(5);
		System.out.println(user);
	}
	
	@Test
	public void findAllUser(){
		List<User> userList = graphqlMapper.findAllUser();
		for (User user : userList) {
			System.out.println(user);
		}
	}
	
	@Test
	public void findUserByName(){
		List<User> userr = graphqlMapper.findUserByName("六");
		for(User user :userr){
			System.out.println(user);
		}
	}
	
	@Test
	public void findEmployee(){
		Employee employee = graphqlEmployeeMapper.selectEmployeeById(1);
		System.out.println(employee);
	}
	
	/**
	 * 根据条件查询实现分页的效果
	 */
	
//	@Test
//	public void findNameByPage(){
//		String name ="六";
//		Integer page =0;
//		Integer pageSize =3;
//		List<User> userList = graphqlMapper.UserLikeByPage(name, page, pageSize);
//		for (User user : userList) {
//			System.out.println(user);
//		}
//	}
	
//	@Test
//	public void userLikeByName(){
//		List<User> userList = graphqlMapper.UserLikeByPage(null, 0, 3);
//		for (User user : userList) {
//			System.out.println(user);
//		}
//	}
	
}
