package com.klogle.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * strus核心数据中心
 * @author klogle
 *package:com.klogle.context
 *E-mail:klogle.one@qq.com
 */
public class ActionContext {

	private Map<String, Object> context;
	public static ThreadLocal<ActionContext> t = new ThreadLocal<>();

	//常量，用于数据中心存放数据的key
	private static final String REQUEST = "com.klogle.struts.request";
	private static final String RESPONSE = "com.klogle.struts.response";
	private static final String SESSION = "com.klogle.struts.session";
	private static final String PARAM = "com.klogle.struts.param";
	private static final String APPLICATION = "com.klogle.struts.application";
	private static final String VALUE_STACK = "com.klogle.struts.valuestack";

	public ActionContext(HttpServletRequest request, HttpServletResponse response, Object action) {
		context = new HashMap<>();
		// 存放request域
		context.put(REQUEST, request);
		// 存放response
		context.put(RESPONSE, response);
		// 存放session
		context.put(SESSION, request.getSession());
		// 存放application
		context.put(APPLICATION, request.getSession().getServletContext());
		// 存放param
		context.put(PARAM, request.getParameterMap());
		// 存放valueStack
		ValueStack vs = new ValueStack();
		vs.push(action);
		//向HttpServletRequest域存放valustack
		request.setAttribute(VALUE_STACK, vs);
		context.put(VALUE_STACK, vs);
		t.set(this);
	}

	/**
	 * 提供各项API访问接口
	 */
	//对外提供HttpServletRequest调用接口
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) context.get(REQUEST);
	}

	//对外提供HttpServletResponse调用接口
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) context.get(RESPONSE);
	}

	//对外提供HttpSession调用接口
	public HttpSession getHttpSession() {
		return (HttpSession) context.get(SESSION);
	}

	//对外提供ServletContext调用接口
	public ServletContext getApplication() {
		return (ServletContext) context.get(APPLICATION);
	}

	//对外提供ValueStack调用接口
	public ValueStack getValueStack() {
		return (ValueStack) context.get(VALUE_STACK);
	}
	
	//对外提供参数调用接口
	public  Map<String,String[]> getParam(){
		return (Map<String, String[]>) context.get(PARAM);
	}

	//对外提供数据中心访问接口
	public  ActionContext getActionContext() {
		return ActionContext.t.get();
	}
}
