package com.klogle.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klogle.config.ActionConfig;
import com.klogle.config.ConfigManager;
import com.klogle.context.ActionContext;
import com.klogle.invocation.ActionInvocation;

public class PrepareAndExcuteFilter implements Filter {
	/**
	 * 1、准备过滤器链 2、准备constant获取到extension 3、加载action配置
	 */
	// 配置信息中心
	private ConfigManager config;
	// 配置文件中的拦截器
	private List<String> interceptors;
	// 配置文件中后缀拦截器的拦截后缀
	private String extension;
	// 配置文件中的action配置信息
	private Map<String, ActionConfig> actionConfigs;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		config = new ConfigManager("/struts.xml");
		// 过滤器链
		interceptors = config.getInterceptors();
		// 准备constant对象，并获取到extension
		extension = config.getConstant().get("struts.action.extension");
		// 获取到action配置
		actionConfigs = config.getActionConfig();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String requestPath = req.getServletPath();
		if (!requestPath.endsWith(extension)) {
			// 如果访问不是以指定后缀结尾，直接放行
			chain.doFilter(request, response);
			return;
		} else {
			requestPath = requestPath.substring(1);
			requestPath = requestPath.replaceAll("." + extension, "");
			ActionConfig actionConfig = actionConfigs.get(requestPath);
			if (actionConfig == null) {
				throw new RuntimeException("访问的action不存在！" + requestPath);
			}
			ActionInvocation invocation = new ActionInvocation(interceptors, actionConfig, req, resp);
			String result = invocation.invoke(invocation);
			String dispatherPath = actionConfig.getResults().get(result).getTemplate();
			if (dispatherPath.equals("") || dispatherPath == null) {
				throw new RuntimeException("访问的视图不存在!" + dispatherPath);
			}
			req.getRequestDispatcher(dispatherPath).forward(req, resp);
			ActionContext.t.remove();
		}
	}

	@Override
	public void destroy() {

	}
}
