package com.klogle.interceptor;

import com.klogle.invocation.ActionInvocation;

public interface Interceptor {

	public void init();
	public String interceptor(ActionInvocation invocation);
	public void destory();
}