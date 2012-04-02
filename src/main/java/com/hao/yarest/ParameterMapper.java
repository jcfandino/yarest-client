package com.hao.yarest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

public class ParameterMapper {
	public static ParameterMapper newParameterFor(Method method) {
		Annotation[][] paramsAnnotations = method.getParameterAnnotations();
		List<String> list = new ArrayList<String>();
		if(paramsAnnotations != null && paramsAnnotations.length > 0) {
			for(int i = 0; i < paramsAnnotations.length; i++) {
				for(Annotation annotation : paramsAnnotations[i]) {
					if(annotation instanceof PathParam) {
						PathParam param = (PathParam) annotation;
						list.add(i, param.value());
					}
				}
			}
		}
		ParameterMapper mapper = new ParameterMapper(list);
		return mapper;
	}
	private List<String> list;
	private ParameterMapper(List<String> list) {
		this.list = list;
	}
	
	public int getIndexOf(String param) {
		return list.indexOf(param);
	}
	
	public String getParamOf(int index) {
		if(list.size() > index) {
			return list.get(index);
		}
		return null;
	}
	
}