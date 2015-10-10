package com.klogle.config;

public class Result {

	private String name;
	private String template;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Result [name=" + name + ", template=" + template + ", type=" + type + "]";
	}

}
