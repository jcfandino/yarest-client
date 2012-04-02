package com.hao.yarest.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

@Path("/search")
public class RestServiceImpl implements RestService {

	private Map<String, Result> state = new HashMap<String, Result>();
	
	public Result searchByKeyword(String keyword) {
		if(state.containsKey(keyword)) {
			return state.get(keyword);
		}
		return new Result();
	}

	public Result searchNothing() {
		return new Result("Hello proyecto", 42);
	}

	public void delete(String id) {
		state.remove(id);
	}

	public void save(Result result) {
		state.put(result.getName(), result);
	}

	public void save(String id, Result result) {
		state.put(id, result);
	}

	public List<Result> getMany() {
		Result r1 = new Result("Hola", 6);
		Result r2 = new Result("Chau", 7);
		ArrayList<Result> list = new ArrayList<Result>();
		list.add(r1);
		list.add(r2);
		return list;
	}
}
