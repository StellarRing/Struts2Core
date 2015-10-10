package com.klogle.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigManager {

	List<String> interceptors;
	Document doc = null;
	InputStream in;

	public ConfigManager(String path) {
		// 加载配置文件获取document对象
		in = ConfigManager.class.getResourceAsStream(path);
		// 创建解析器
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("配置文件加载失败！");
		}
	}

	// 1、读取interceptor
	public List<String> getInterceptors() {

		// 编写Xpath
		String Xpath = "//interceptor";
		// 根据Xpath获得拦截器配置
		List<Element> list = doc.selectNodes(Xpath);
		// 将配置信息封装到list
		if (list != null && list.size() > 0) {
			interceptors = new ArrayList<>();
			for (Element element : list) {
				String className = element.attributeValue("class");
				interceptors.add(className);
			}
		}
		// 返回
		return interceptors;
	}

	// 2、读取constant
	public Map<String, String> getConstant() {
		Map<String, String> map = new HashMap<>();
		String Xpath = "//constant";
		List<Element> list = doc.selectNodes(Xpath);
		if (list != null) {
			for (Element ele : list) {
				String name = ele.attributeValue("name");
				String value = ele.attributeValue("value");
				map.put(name, value);
			}
		}
		return map;
	}

	// 3、读取action
	public Map<String, ActionConfig> getActionConfig() {
		Map<String, ActionConfig> actionMap = new HashMap<>();
		ActionConfig config;
		String Xpath = "//action";
		List<Element> actionList = doc.selectNodes(Xpath);
		if (actionList != null) {
			for (Element ele : actionList) {
				config = new ActionConfig();
				String name = ele.attributeValue("name");
				String className = ele.attributeValue("class");
				String method = ele.attributeValue("method");
				// 考虑到method的默认值是execute
				method = method == null || method.equals("") ? "execute" : method;
				config.setClassName(className);
				config.setMethod(method);
				config.setName(name);
				List<Element> resultList = ele.elements("result");
				if (resultList != null && resultList.size() > 0) {
					Result result;
					for (Element element : resultList) {
						result = new Result();
						String resultName = element.attributeValue("name");
						resultName = resultName == null ? "success" : resultName;
						String type = element.attributeValue("type");
						String template = element.getText();
						result.setName(resultName);
						result.setTemplate(template);
						result.setType(type);
						config.getResults().put(resultName, result);
					}
				}
				Xpath = "//param";
				List<Element> paramList = doc.selectNodes(Xpath);
				if (paramList != null && paramList.size() > 0) {
					StaticParam staticParam;
					for (Element element : paramList) {
						staticParam=new StaticParam();
							String paramName=element.attributeValue("name");
							String paramValue=element.getText();
							staticParam.setName(paramName);
							staticParam.setValue(paramValue);
							config.getStaticParams().put(paramName, staticParam);
					}
				}
				actionMap.put(name, config);
			}
		}
		return actionMap;
	}
}