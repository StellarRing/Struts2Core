package com.klogle.interceptor;

import com.klogle.invocation.ActionInvocation;

/**
 * 拦截器接口
 * @author klogle
 *package:com.klogle.interceptor
 *E-mail:klogle.one@qq.com
 */
public interface Interceptor {

	public void init();
	public String interceptor(ActionInvocation invocation);
	public void destory();
}