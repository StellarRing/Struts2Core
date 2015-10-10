package com.klogle.config;

/**
 * 承载静态参数各项配置信息
 * @author klogle
 *package:com.klogle.config
 *E-mail:klogle.one@qq.com
 */
public class StaticParam {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "StaticParam [name=" + name + ", value=" + value + "]";
	}
	
}
