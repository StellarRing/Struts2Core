package com.klogle.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klogle.config.ActionConfig;
import com.klogle.context.ActionContext;
import com.klogle.interceptor.Interceptor;

/**
 * 调度中心
 * @author klogle
 *package:com.klogle.invocation
 *E-mail:klogle.one@qq.com
 */
public class ActionInvocation {

	// 准备invocation链
	private Iterator<Interceptor> interceptors;
	// 即将调用的action对象
	private Object action;
	// action配置信息
	private ActionConfig config;
	// 数据中心
	private ActionContext context;

	public ActionInvocation(List<String> interceptorClassNames, ActionConfig config, HttpServletRequest request,
			HttpServletResponse response) {
		List<Interceptor> interceptorList = null;
		//准备invocation链
		if (interceptorClassNames != null && interceptorClassNames.size() > 0) {
			interceptorList = new ArrayList<>();
			for (String className : interceptorClassNames) {
				Interceptor interceptor = null;
				try {
					interceptor = (Interceptor) Class.forName(className).newInstance();
					//初始化interceptor
					interceptor.init();
				} catch (InstantiationException e) {
					e.printStackTrace();
					throw new RuntimeException("实例化失败：" + className);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new RuntimeException("权限异常：" + className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException("未找到对应类：" + className);
				}
				//拦截器入链
				interceptorList.add(interceptor);
			}
			this.interceptors = interceptorList.iterator();
		}
		// 准备action实例
		this.config = config;
		String actionClassName = config.getClassName();
		try {
			action = Class.forName(actionClassName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("实例化失败：" + actionClassName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("权限异常：" + actionClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("未找到对应类：" + actionClassName);
		}
		// 准备数据中心ActionContext
		context = new ActionContext(request, response, action);
	}

	/**
	 * 
	 * @return
	 */
	public ActionContext getContext() {
		return context;
	}


	/**
	 * 函数调用
	 * @param invocation	invocation对象（采用递归执行）
	 * @return 函数调用返回值
	 */
	private String result;
	public String invoke(ActionInvocation invocation) {

		// 执行拦截器链，结果视图是否被赋值
		if (interceptors != null && interceptors.hasNext() && result == null) {
			// 如果还有下一个，遍历下一个（携带invocation信息）
			Interceptor it = interceptors.next();
			result = it.interceptor(invocation);
		} else {
			// 获取action的方法
			String methodName = config.getMethod();
			try {
				Method executeMethod = action.getClass().getMethod(methodName);
				result = (String) executeMethod.invoke(action);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return result;
	}
}