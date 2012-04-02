package com.hao.yarest;

import java.lang.reflect.Type;


public class MethodDefinition {
	
	private Verb verb;
	private String path;
	private String consumes;
	private String produces;
	private Type returnType;
	private Class<?> bodyType;
	private ParameterMapper parameterMapper;
	
	public void setVerb(Verb verb) {
		this.verb = verb;
	}
	public Verb getVerb() {
		return verb;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setConsumes(String consumes) {
		this.consumes = consumes;
	}
	public String getConsumes() {
		return consumes;
	}
	public void setProduces(String produces) {
		this.produces = produces;
	}
	public String getProduces() {
		return produces;
	}
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	public Type getReturnType() {
		return returnType;
	}
	public void setParameterMapper(ParameterMapper parameterMapper) {
		this.parameterMapper = parameterMapper;
	}
	
	public ParameterMapper getParameterMapper() {
		return parameterMapper;
	}
	public void setBodyType(Class<?> bodyType) {
		this.bodyType = bodyType;
	}
	public Class<?> getBodyType() {
		return bodyType;
	}
	
}