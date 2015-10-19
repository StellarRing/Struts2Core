package com.klogle.interceptor;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.klogle.context.ActionContext;
import com.klogle.context.ValueStack;
import com.klogle.invocation.ActionInvocation;

/**
 * 静态参数拦截器
 * @author klogle
 *package:com.klogle.interceptor
 *E-mail:klogle.one@qq.com
 */
public class StaticParametersInterceptor implements Interceptor{

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
				 * 在核心过滤器PrepareAndExcuteFilter初始化的时候会将ActionConfig（子类ConfigManager）
				 * 实例化，并同时实例化ActionInvocation调度中心
				 * 实例化ActionInvocation调度中心完成后将ActionConfig对象传递给调度中心
				 * 可以从调度中心获取到ActionConfig对象，从而也就可以获取到静态属性
				 */
				BeanUtils.populate(action, invocation.getConfig().getStaticParams());
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
