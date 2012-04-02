package com.hao.yarest;

import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


public class MethodDefinitionFactory {
	
	public MethodDefinition create(Method method) {
		MethodDefinition def = new MethodDefinition();
		
		def.setVerb(Verb.getVerbFor(method));
		
		
		if(method.isAnnotationPresent(Consumes.class)) {
			def.setConsumes(method.getAnnotation(Consumes.class).value()[0]);
		}
		
		if(method.isAnnotationPresent(Produces.class)) {
			def.setProduces(method.getAnnotation(Produces.class).value()[0]);
		}
		
		if(method.isAnnotationPresent(Path.class)) {
			def.setPath(method.getAnnotation(Path.class).value());
		}
		
		ParameterMapper parameterMapper = ParameterMapper.newParameterFor(method);
		
		for(int i = 0; i < method.getParameterTypes().length; i++) {
			if(parameterMapper.getParamOf(i) == null) {
				def.setBodyType(method.getParameterTypes()[i]);
			}
		}
		
		
		def.setParameterMapper(parameterMapper);
		
		def.setReturnType(method.getGenericReturnType());
		
		return def;
	}


	
}