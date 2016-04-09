package com.train.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
	private static Logger logger = Logger.getLogger(IndexController.class);

	/**
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @author 段丁阳
	 */
	@RequestMapping(value = "menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		logger.debug("menu");
		logger.debug(Thread.currentThread().getName());
		return "layout/menuTree";
	}

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @author 段丁阳
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		logger.debug("To--------train-upload.jsp");
		return "modules/train/train-upload";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @author 段丁阳
	 */
	@RequestMapping(value = "help", method = RequestMethod.GET)
	public String help(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		return "layout/help";
	}

}