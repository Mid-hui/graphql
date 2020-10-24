package com.cy.graphql.config;

import java.io.FileReader;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.errors.SchemaProblem;

@PropertySource(value = "classpath:/file/fileName.properties",encoding="UTF-8")
@Configuration
public class GraphqlConfig {

	@Value("${file.fileName}")
	private String fileName; 

	private  GraphQL graphQL;

	@Autowired
	private GraphQLDataFetchers graphQLDataFetchers;

	//实现对GraphQL对象初始化(加上@PostConstruct注解表示在spring初始化的时候执行此方法)
	@PostConstruct
	public void init() throws Exception{
		this.graphQL =GraphQL.newGraphQL(buildScheam())
				.build();
	}

	@Bean
	public GraphQLSchema buildScheam() {
		return new SchemaGenerator().makeExecutableSchema(buildTypeRegistry(), buildWiring());
		//buildTypeRegistry() 这个方法去读取graphql文件
		//buildWiring() 这个方法去读取配置文件里面的格式
	}

	private TypeDefinitionRegistry buildTypeRegistry() {
		try {
			return new SchemaParser().parse(new FileReader(new ClassPathResource(fileName).getFile()));//这里解析了第一个graphql文件
		} catch (SchemaProblem | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				//查询
				.type("Query", typeWiring -> typeWiring
						//根据id查询用户	 
						.dataFetcher("user", graphQLDataFetchers.getUserById())
						//查询所有用户
						.dataFetcher("users", graphQLDataFetchers.getAllUser())
						
						//查询所有用户
						//.dataFetcher("userAll", graphQLDataFetchers.getAllUser())
						//分页查询
						//.dataFetcher("usersPaging", graphQLDataFetchers.bookByPage())
						//模糊查询
						//.dataFetcher("userLikeByName",graphQLDataFetchers.userLikeByName())
						)

				//增删改
				.type("Mutation", typeWiring -> typeWiring
						//根据id删除用户
						.dataFetcher("user", graphQLDataFetchers.deleteUser())
						//添加用户
						.dataFetcher("createUser",graphQLDataFetchers.insertUser())
						//更改用户信息
						.dataFetcher("modifyUser", graphQLDataFetchers.updateUser())
						)

				//添加指令
				.directive("auth",new AuthorisationDirective())
				.build();
	}

	@Bean
	public GraphQL graphQL(){
		return this.graphQL;
	}
}
