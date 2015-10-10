package com.klogle.action;

import com.klogle.opensymphony.ActionSupport;

public class HelloAction extends ActionSupport {
	private String name;

	private String sex;

	private Integer age;

	public String hello() {
		if (age < 20) {
			return LOGIN;
		} else {
			System.out.println("name:" + name + " sex:" + sex + " age:" + age);
			return SUCCESS;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
