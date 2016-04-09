package com.train.utils;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SystemConfig {
	private static final Logger logger = Logger.getLogger(SystemConfig.class);
	private static Properties props = new Properties();

	private static String RESOURCE_PATH = "classpath:common.properties";

	@PostConstruct
	public void loadProperties() {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		try {
			Resource[] resources = resolver.getResources(RESOURCE_PATH);
			for (Resource resource : resources) {
				props.load(resource.getInputStream());
				if (logger.isDebugEnabled()) {
					logger.debug("加载系统资源文件：{}" + resource.getFilename());
				}
			}
		} catch (IOException e) {
			logger.error("加载系统资源文件失败,失败原因：{}" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static String getValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
}
