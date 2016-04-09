package com.train.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.train.exception.TrainException;
import com.train.service.TrainService;

/**
 * 
 * Title: <br>
 * Description: <br>
 * Date: 2016年1月24日 <br>
 * 
 * @author 段丁阳
 */
@Controller
@RequestMapping(value = "/train")
public class TrainController {

	@Autowired
	TrainService trainService;

	public static final String path = "modules/train/";
	private static Logger logger = Logger.getLogger(TrainController.class);

	/**
	 * 
	 * @return
	 * @author 段丁阳
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadView(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("upload");
		return path + "train-upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") CommonsMultipartFile[] file, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		logger.debug("upload-post");
		String rootpath = request.getSession().getServletContext().getRealPath("/");
		JSONArray jsonArray = null;
		JSONArray jsonArray2 = null;
		String upLoadPath = rootpath + "statics\\upload\\doc\\template.xlsx";
		String fileName = "template.xlsx";
		String temp = "[{name: 'speed',data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],pointInterval: 200}"
				+ ",{name: 'power',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 176.0, 135.6],yAxis: 1,pointInterval: 200}]";
		String title = "数据分析图";
		Map<String, Object> param = new HashMap<>();
		try {
			jsonArray = trainService.uploadfile(request, response, file, upLoadPath, param, 1);
			jsonArray2 = trainService.uploadfile(request, response, file, upLoadPath, param, 2);
			//logger.debug(jsonArray2);
		} catch (TrainException e) {
			e.printStackTrace();
		}
		map.put("title", title);
		//以时间为依据
		map.put("data", jsonArray);
		//以距离为依据
		map.put("data2", jsonArray2);
		map.put("trains", param.get("trains"));
		return path + "train-result";
	}
}
