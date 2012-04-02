package com.hao.yarest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.Path;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class HttpRequestFactory {
	private MethodDefinition def;
	private String baseUrl;
	private Object[] args;
	private Class<?> clazz;
	
	public HttpRequestFactory(Class<?> clazz, MethodDefinition def, Object[] args, String baseUrl) {
		this.clazz = clazz;
		this.def = def;
		this.baseUrl = baseUrl;
		this.args = args;
	}
	
	public HttpUriRequest create() {
		HttpUriRequest msg = def.getVerb().createHttpRequest(this);
		configureRequestHeaders(msg);
		return msg;
	}

	private String getUrl() {
		return baseUrl + getPath() + getQuery();
	}
	
	/**
	 * TODO
	 */
	private String getQuery() {
		return "";
	}

	private String getPath() {
		String path = getServicePath() + def.getPath();
		
		if(args != null) {
			for(int i = 0; i < args.length; i++) {
				if(def.getParameterMapper().getParamOf(i) != null) {
					path = path.replace(
						getParamTagFor(i),
						getEncodedArg(i)
					);
				}
			}
		}
		
		return path;
	}

	private String getServicePath() {
		if(clazz.getAnnotation(Path.class) != null) {
			return clazz.getAnnotation(Path.class).value();
		}
		return "";
	}

	private String getEncodedArg(int i) {
		try {
			return URLEncoder.encode(args[i].toString(), "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private String getParamTagFor(int i) {
		return "{" + def.getParameterMapper().getParamOf(i) + "}";
	}
	
	/**
	 * TODO Rework
	 * @return
	 */
	private HttpEntity getBody() {
		try {
			Object obj = null;
			
			if(args != null) {
				for(int i = 0; i < args.length; i++) {
					if(def.getParameterMapper().getParamOf(i) == null) {
						obj = args[i];
					}
				}
			}
			String consumes = def.getConsumes();
			HttpEntity entity = null;
			
			if(consumes != null) {
				if(consumes.equals("application/json")) {
					JsonObject json = new JsonObject();
					json.add(getBodyElementName(), new Gson().toJsonTree(obj));
//					String json = new Gson().toJson(obj);
					String str = new Gson().toJson(json);
					entity = new StringEntity(str);
				}
			}
			return entity;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported encoding", e);
		}
	}

	private String getBodyElementName() {
		String className = def.getBodyType().getSimpleName();
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}
	
	public HttpUriRequest createGet() {
		return new HttpGet(getUrl());
	}
	
	public HttpUriRequest createPut() {
		HttpPut httpPut = new HttpPut(getUrl());
		httpPut.setEntity(getBody());
		return httpPut;
	}
	
	public HttpUriRequest createPost() {
		HttpPost httpPost = new HttpPost(getUrl());
		httpPost.setEntity(getBody());
		return httpPost;
	}
	
	public HttpUriRequest createDelete() {
		return new HttpDelete(getUrl());
	}
	
	public HttpUriRequest createHead() {
		return new HttpHead(getUrl());
	}
	
	private void configureRequestHeaders(HttpUriRequest httpReq) {
		if(def.getConsumes() != null) {
			httpReq.setHeader("Content-Type", def.getConsumes());
		}
	}

}