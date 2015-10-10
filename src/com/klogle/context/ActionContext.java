package com.klogle.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionContext {

	private Map<String, Object> context;
	public static ThreadLocal<ActionContext> t = new ThreadLocal<>();

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
		request.setAttribute(VALUE_STACK, vs);
		context.put(VALUE_STACK, vs);
		t.set(this);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) context.get(REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) context.get(RESPONSE);
	}

	public HttpSession getHttpSession() {
		return (HttpSession) context.get(SESSION);
	}

	public ServletContext getApplication() {
		return (ServletContext) context.get(APPLICATION);
	}

	public ValueStack getValueStack() {
		return (ValueStack) context.get(VALUE_STACK);
	}
	
	public  Map<String,String[]> getParam(){
		return (Map<String, String[]>) context.get(PARAM);
	}

	public  ActionContext getActionContext() {
		return ActionContext.t.get();
	}
}
