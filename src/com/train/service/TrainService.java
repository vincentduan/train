package com.train.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.train.entity.Train;
import com.train.exception.TrainException;

public interface TrainService extends BaseService<Train> {

	public JSONArray uploadfile(HttpServletRequest request, HttpServletResponse response, CommonsMultipartFile[] file, String downLoadPath, Map<String, Object> param, int i) throws TrainException;

}
