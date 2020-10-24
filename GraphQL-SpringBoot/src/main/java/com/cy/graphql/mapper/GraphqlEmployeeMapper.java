package com.cy.graphql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.graphql.vo.Employee;

@Mapper
public interface GraphqlEmployeeMapper {
		
	@Select("select * from employee where id=#{id} ")
	Employee selectEmployeeById(@Param("id") Integer id);
}
