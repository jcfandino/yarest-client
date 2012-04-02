package com.hao.yarest.mock;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

class RestServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6294757997141515592L;

	private RestServiceImpl service = new RestServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		Result result = null;
		String json = null;
		if(path.endsWith("/all") || path.endsWith("/all/")) {
			List<Result> list = service.getMany();
			json = new Gson().toJson(list);
			resp.getWriter().write("{results:" + json + "}");
		} else {
			if(path.equals("/")) {
			result = service.searchNothing();
			} else {
			result = service.searchByKeyword(getKeyFrom(path));
			}
			json = new Gson().toJson(result);
			resp.getWriter().write("{result:" + json + "}");
		}
	}
	private String getKeyFrom(String path) {
		if(path.endsWith("/")) {
			String string = path.substring(0, path.length() - 1);
			return string.substring(string.lastIndexOf('/') + 1);
		}
		return path.substring(path.lastIndexOf('/') + 1);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		String key = getKeyFrom(path);
		service.delete(key);
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doHead(req, resp);
		//TODO Missing feature
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String json = req.getReader().readLine();
		Result result = getResultFrom(json);
		service.save(result);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		String json = req.getReader().readLine();
		Result result = getResultFrom(json);
		service.save(getKeyFrom(path), result);
		
	}
	private Result getResultFrom(String json) {
		JsonObject jsonObject = new JsonStreamParser(json).next().getAsJsonObject();
		JsonElement value = jsonObject.entrySet().iterator().next().getValue();
		Result result = new Gson().fromJson(value, Result.class);
		return result;
	}
}