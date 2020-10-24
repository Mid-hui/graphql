package com.cy.graphql.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = -867801836335189258L;
	
	private Integer id;
	private String name;
	private Integer age;
	
}
