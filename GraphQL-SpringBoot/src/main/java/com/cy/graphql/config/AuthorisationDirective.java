package com.cy.graphql.config;



import java.util.Map;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 * 实现Derective指令拦截
 */
public class AuthorisationDirective implements SchemaDirectiveWiring {
	
	/*
	@Override
	public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
		String targetAuthRole = (String) environment.getDirective().getArgument("role").getValue();//获取Directive中的参数值（类似于获取注解中的参数值） 获取的是manage
        //GraphQLFieldDefinition field = environment.getElement();
        // build a data fetcher that first checks authorisation roles before then calling the original data fetcher
		//构建一个数据提取器，它首先检查授权角色，然后再调用原始数据提取器
        //DataFetcher originalDataFetcher = field.getDataFetcher();
        DataFetcher originalDataFetcher = environment.getFieldDataFetcher();
        DataFetcher authDataFetcher = new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
            	Map<String, Object> contextMap = dataFetchingEnvironment.getContext();
                //String authContext = (String)contextMap.get("authContext");
            	AuthorisationCtx authContext = (AuthorisationCtx) contextMap.get("authContext");
            	if (authContext.hasRole(targetAuthRole)) {
            		return null;//无权限返回null
                } else {
                	return originalDataFetcher.get(dataFetchingEnvironment);//如果有权限走原始数据
                	//return originalDataFetcher.get(dataFetchingEnvironment);
                }
            }
        };
       //return field.transform(builder -> builder.dataFetcher(authDataFetcher));
        return environment.setFieldDataFetcher(authDataFetcher);
	}
	 */

	@Override
	public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
		GraphQLFieldDefinition field = environment.getElement();
		System.out.println("field============================================="+field.getType().toString());//field==GraphQLScalarType{name='String', description='Built-in String', coercing=graphql.Scalars$3@3440e9cd}
		GraphQLFieldsContainer parentType = environment.getFieldsContainer();

		String targetAuthRole = (String) environment.getDirective().getArgument("role").getValue();
		DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);

		DataFetcher authDataFetcher = DataFetcherFactories.wrapDataFetcher(originalDataFetcher, (dataFetchingEnvironment, value) -> {
			Map<String,String> contextMap = dataFetchingEnvironment.getContext();

			if (contextMap.get("role").equals(targetAuthRole)) {//targetAuthRole=manager
				if(field.getType().toString().equals("String!")) {
					return "无权限";
				}else if(field.getType().toString().equals("Int!")) {
					return 0;
				}
			}else {
				return value;
			}
			return null;//什么都不返回
		});

		environment.getCodeRegistry().dataFetcher(parentType,field,authDataFetcher);
		return field;
	}
}
