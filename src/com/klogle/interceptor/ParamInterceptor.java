package com.klogle.interceptor;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.klogle.context.ActionContext;
import com.klogle.context.ValueStack;
import com.klogle.invocation.ActionInvocation;

/**
 * 参数拦截器
 * @author klogle
 *package:com.klogle.interceptor
 *E-mail:klogle.one@qq.com
 */
public class ParamInterceptor implements Interceptor {

	@Override
	public void init() {
	}

	@Override
	public String interceptor(ActionInvocation invocation) {
		ActionContext ac = invocation.getContext();
		ValueStack vs = ac.getValueStack();
		// 从栈顶获取action
		Object action = vs.seek();
		try {
			/**
			 * 通过ActionContext提供的request接口获取到当前访问的HttpRequest对象
			 * 从栈顶可以获取到action对象
			 */
			BeanUtils.populate(action, ac.getRequest().getParameterMap());
		} catch (IllegalAccessException e) {
			throw new RuntimeException("非法访问！"+e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RuntimeException("目标调用失败！"+e.getMessage());
		}
		return invocation.invoke(invocation);
	}

	@Override
	public void destory() {

	}
}
