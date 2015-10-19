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

/**
 * 核心过滤器
 * @author klogle
 *package:com.klogle.filter
 *E-mail:klogle.one@qq.com
 */
public class PrepareAndExcuteFilter implements Filter {
	
	// 配置信息加载器
	private ConfigManager config;
	// 载入拦截器配置信息
	private List<String> interceptors;
	// 动作类访问后缀
	private String extension;
	// 动作类配置信息
	private Map<String, ActionConfig> actionConfigs;

	@Override
	//初始化过滤器
	public void init(FilterConfig arg0) throws ServletException {
		config = new ConfigManager("/struts.xml");
		// 准备过滤器链
		interceptors = config.getInterceptors();
		// 准备constant对象，并获取到extension
		extension = config.getConstant().get("struts.action.extension");
		// 获取到action配置
		actionConfigs = config.getActionConfig();
	}

	@Override
	//执行访问拦截
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
			//如果是以指定后缀结尾，读取action访问路径
			requestPath = requestPath.substring(1);
			requestPath = requestPath.replaceAll("." + extension, "");
			//获取到配置对象
			ActionConfig actionConfig = actionConfigs.get(requestPath);
			//判断是否存在请求的action对象
			if (actionConfig == null) {
				throw new RuntimeException("访问的action不存在！" + requestPath);
			}
			//准备调度中心
			ActionInvocation invocation = new ActionInvocation(interceptors, actionConfig, req, resp);
			String result = invocation.invoke(invocation);
			String dispatherPath = actionConfig.getResults().get(result).getTemplate();
			//判断是否已定义访问视图（主要供开发者使用）
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
