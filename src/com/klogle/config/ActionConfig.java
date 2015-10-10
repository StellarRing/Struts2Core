package com.klogle.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于承载Action配置信息的Bean
 * @author klogle
 *package:com.klogle.config
 *E-mail:klogle.one@qq.com
 */
public class ActionConfig {

	//action name
	private String name;
	//action method
	private String method;
	//action className
	private String className;

	//action results
	private Map<String, Result> results = new HashMap<>();
	//静态注入参数
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
