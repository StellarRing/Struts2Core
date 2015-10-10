package com.klogle.config;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig {

	private String name;
	private String method;
	private String className;

	private Map<String, Result> results = new HashMap<>();
	private Map<String, StaticParam> staticParams = new HashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, Result> getResults() {
		return results;
	}

	public void setResults(Map<String, Result> results) {
		this.results = results;
	}

	public Map<String, StaticParam> getStaticParams() {
		return staticParams;
	}

	public void setStaticParams(Map<String, StaticParam> staticParams) {
		this.staticParams = staticParams;
	}

	@Override
	public String toString() {
		return "ActionConfig [name=" + name + ", method=" + method + ", className=" + className + ", results=" + results
				+ ", staticParams=" + staticParams + "]";
	}

}
