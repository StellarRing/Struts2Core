package com.klogle.test;

import java.util.Map;

import org.junit.Test;

import com.klogle.config.ActionConfig;
import com.klogle.config.ConfigManager;

/**
 * 单元测试
 * @author klogle
 *package:com.klogle.test
 *E-mail:klogle.one@qq.com
 */
public class StrutsTest {

	@Test
	/**
	 * 测试读取配置信息
	 */
	public void demo1(){
		ConfigManager config=new ConfigManager("/struts.xml");
		Map<String, ActionConfig> actions = config.getActionConfig();
		String value=actions.get("helloAction").getStaticParams().get("count").getValue();
		System.out.println(actions);
		System.out.println(value);
	}
}
