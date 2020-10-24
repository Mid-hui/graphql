package com.cy.graphql.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.graphql.mapper.GraphqlEmployeeMapper;
import com.cy.graphql.mapper.GraphqlMapper;
import com.cy.graphql.util.ServiceException;
import com.cy.graphql.util.ToEntityUtil;
import com.cy.graphql.vo.Employee;
import com.cy.graphql.vo.User;

import graphql.schema.DataFetcher;

@Service
public class GraphQLDataFetchers {

	@Autowired
	private GraphqlMapper graphqlMapper;

	@Autowired
	private GraphqlEmployeeMapper graphqlEmployeeMapper;


	private List<User> users = new ArrayList<User>(Arrays.asList(
			new User(1, "user-1", 18),
			new User(2, "user-2", 20),
			new User(3, "user-3", 22),
			new User(4, "user-4", 24),
			new User(5, "user-5", 26)
			));




	public DataFetcher<User> getUser() {
		return environment -> {
			Integer userId = Integer.valueOf(environment.getArgument("id").toString());
			return users
					.stream()
					.filter(user -> user.getId().equals(userId))
					.findFirst()
					.orElse(null);
		};
	}

	/**
	 * 根据id查询员工
	 * @return
	 */
	public DataFetcher<Employee> getEmployeeById(){
		return environment -> {
			Integer employeeId = environment.getArgument("id");
			return graphqlEmployeeMapper.selectEmployeeById(employeeId);
		};
	}

	public DataFetcher<User> getUserById(){
		return environment -> {
			LinkedHashMap<?,?> userMap = environment.getArgument("user");
			User user = (User)ToEntityUtil.entity(new User(),userMap);
			Integer id = user.getId();
			if(StringUtils.isEmpty(id)|| id<0){
				throw new ServiceException("传入用户的id值异常");
			}
			return graphqlMapper.findUser(id);
		};
	}
	/**
	 * 查询所有用户
	 */
	public DataFetcher<List<User>> getAllUser(){
		return environment -> {
			LinkedHashMap<?,?> userMap = environment.getArgument("user");
			if(userMap.isEmpty()){
				throw new ServiceException("请求失败!!!");
			}
			Integer page = (Integer)userMap.get("page");
			Integer pageSize = (Integer)userMap.get("pageSize");
			if(page<0 || pageSize<0){
				throw new ServiceException("输入的页码值有误");
			}
			List<User> userList = graphqlMapper.UserLikeByPage(userMap);
			return userList;
		};
	}

	/**
	 * 根据id删除用户
	 * @return
	 */
	public DataFetcher<Boolean> deleteUser(){
		return environment -> {
			Integer userId = (Integer)environment.getArgument("id");
			if(StringUtils.isEmpty(userId) || userId<0){
				throw new ServiceException("传入的id有误");
			}else {
				graphqlMapper.deleteUserById(userId);
				System.out.println("删除用户成功");
				return true;
			}
		};
	}
	/**
	 *  插入用户信息
	 * @return
	 */
	public DataFetcher<Integer> insertUser(){
		return environment -> {
			LinkedHashMap<?,?> userMap = environment.getArgument("user");//这里这样获取的是一个map集合
			if(userMap.isEmpty()){
				System.out.println("集合为空");
			}else {
				System.out.println("集合有数据");
			}
			User user = (User)ToEntityUtil.entity(new User(),userMap);
			int rows = graphqlMapper.insertUser(user);
			return rows;
		};
	}

	/**
	 * 根据id修改用户信息
	 */
	public DataFetcher<Integer> updateUser(){
		return environment -> {
			Integer id = environment.getArgument("id");
			String name = environment.getArgument("name");
			Integer age = environment.getArgument("age");
			User user = new User(id, name, age);
			int rows = graphqlMapper.updateUser(user);
			return rows;
		};
	}

}
