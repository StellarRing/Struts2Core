package com.klogle.opensymphony;

/**
 * action适配器
 * @author klogle
 *package:com.klogle.opensymphony
 *E-mail:klogle.one@qq.com
 */
public class ActionSupport implements Action{

	@Override
	//默认动作方法和视图名称
	public String execute() throws Exception {
		return SUCCESS;
	}
	
}
