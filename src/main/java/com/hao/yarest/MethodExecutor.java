package com.hao.yarest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

public class MethodExecutor {
		
		private HttpClient httpClient;
		private MethodDefinition def;
		private HttpUriRequest httpRequest;
		
		public MethodExecutor(HttpClient httpClient, MethodDefinition def, HttpUriRequest httpRequest) {
			this.httpClient = httpClient;
			this.def = def;
			this.httpRequest = httpRequest;
		}
		
		public Object execute() {
			InputStream instream = null;
			HttpResponse response;
			try {
				response = httpClient.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					instream = entity.getContent();
					String str = convertStreamToString(instream);
					instream.close();
					Object obj = marshal(str);
					return obj;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
//				Log.e("libdroid", "rest client exception", e);
			} catch (IOException e) {
				e.printStackTrace();
//				Log.e("libdroid", "rest client exception", e);
			}
			return null;
		}

		private Object marshal(String str) {
			if(str.length() == 0) {
				return null;
			}
			JsonObject jsonObject = new JsonStreamParser(str).next().getAsJsonObject();
			JsonElement value = jsonObject.entrySet().iterator().next().getValue();
			Object obj = new Gson().fromJson(value, def.getReturnType());
			return obj;
		}
		
		private static String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
		
	}