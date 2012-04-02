package com.hao.yarest.mock;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Result {

	@SerializedName("name")
	private String name;
	
	@SerializedName("age")
	private int age;
	
	public Result() {
	}
	
	public Result(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "(Name=" + name + ", Age=" + age + ")";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Result)) {
			return false;
		}
		Result sr = (Result) obj;
		return (sr.getName() == name || (sr.getName() != null && sr.getName().equals(name))) &&
			sr.getAge() == age;
	}
	
	@Override
	public int hashCode() {
		return 11 + (name == null ? 0 : name.hashCode()) + age;
	}
}

