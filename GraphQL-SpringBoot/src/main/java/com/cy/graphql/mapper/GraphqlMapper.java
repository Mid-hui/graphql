package com.cy.graphql.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.graphql.vo.User;

@Mapper
public interface GraphqlMapper {
	
	//@Select("select * from user where id=#{id}")
	User findUser(@Param("id") Integer id);
	
	/**
	 * 获取用户总记录条数
	 * @return
	 */
	@Select("select count(*) from user")
	int getRowCount();
	
	/**
	 * 查询所有的用户
	 * @return
	 */
	@Select("select * from user")
	List<User> findAllUser();
	
	/**
	 * 模糊查询
	 * @param name
	 * @return
	 */
	
	@Select("select * from user where name like concat(\"%\",#{username},\"%\")")
	List<User> findUserByName(String name);
	
	
	@Delete("delete from user where id=#{id}")
	void deleteUserById(@Param("id") Integer id);
	
	@Insert("insert into user(name,age) values(#{name},#{age})") 
	int insertUser(User user);//多个参数直接放User对象
	
	
	@Update("update user set name=#{name},age=#{age} where id=#{id} ") 
	int updateUser(User user);
	
	
	//limit (页码-1)*每页显示记录数, 每页显示记录数
	
	/*
	--页码是我们要在第几页显示
	select * from emp limit 0,3;  --每页显示3条,查询第1页
	select * from emp limit 3,3;  --每页显示3条,查询第2页
 	select * from emp limit 6,3;  --每页显示3条,查询第3页
	*/
	
	//@Select("select * from user where name like concat(\"%\",#{name},\"%\") limit #{page},#{pageSize}")
	
	//List<User> UserLikeByPage(@Param("name")String name,@Param("page")Integer page,@Param("pageSize")Integer pageSize,@Param("id")Integer id);
    //List<User> UserLikeByPage(@Param("user") List<User> user);
	//List<User> UserLikeByPage(Map<String,Object> map);
	List<User> UserLikeByPage(LinkedHashMap<?,?> map);
	
}
