package com.klogle.test;

import java.util.Map;

import org.junit.Test;

import com.klogle.config.ActionConfig;
import com.klogle.config.ConfigManager;

public class StrutsTest {

	@Test
	/**
	 * 测试加载配置文件
	 */
	public void demo1(){
		ConfigManager config=new ConfigManager("/struts.xml");
		Map<String, ActionConfig> actions = config.getActionConfig();
		String value=actions.get("helloAction").getStaticParams().get("count").getValue();
		System.out.println(actions);
		System.out.println(value);
	}
}
