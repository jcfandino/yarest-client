package com.hao.yarest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.apache.http.client.methods.HttpUriRequest;

public enum Verb {
	GET {
		@Override
		public HttpUriRequest createHttpRequest(HttpRequestFactory factory) {
			return factory.createGet();
		}
	}, PUT {
		@Override
		public HttpUriRequest createHttpRequest(HttpRequestFactory factory) {
			return factory.createPut();
		}
	}, POST {
		@Override
		public HttpUriRequest createHttpRequest(HttpRequestFactory factory) {
			return factory.createPost();
		}
	}, DELETE {
		@Override
		public HttpUriRequest createHttpRequest(HttpRequestFactory factory) {
			return factory.createDelete();
		}
	}, HEAD {
		@Override
		public HttpUriRequest createHttpRequest(HttpRequestFactory factory) {
			return factory.createHead();
		}
	};
	
	static {
		Verb.initMapping();
	}
	
	static HashMap<Class<? extends Annotation>, Verb> verbMap;
	static void initMapping() {
		Verb.verbMap = new HashMap<Class<? extends Annotation>, Verb>();
		Verb.verbMap.put(GET.class, GET);
		Verb.verbMap.put(PUT.class, PUT);
		Verb.verbMap.put(POST.class, POST);
		Verb.verbMap.put(HEAD.class, HEAD);
		Verb.verbMap.put(DELETE.class, DELETE);
	}
	
	protected static Verb getVerbFor(Class<?> annotation) {
		return Verb.verbMap.get(annotation);
	}
	
	
	public abstract HttpUriRequest createHttpRequest(HttpRequestFactory factory);
	
	public static Verb getVerbFor(Method method) {
		Verb verb = null;
		for(Class<? extends Annotation> annotation : verbMap.keySet()) {
			verb = returnIfPresent(method, annotation);
			if(verb != null) {
				return verb;
			}
		}
		throw new RuntimeException("Unknown verb exception");
	}
	
	private static Verb returnIfPresent(Method method, Class<? extends Annotation> annotation) {
		return method.isAnnotationPresent(annotation) ? Verb.getVerbFor(annotation) : null;
	}
	
}