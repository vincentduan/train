package com.train.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.train.controller.TrainController;
import com.train.entity.Train;
import com.train.exception.TrainException;
import com.train.service.TrainService;

@Service
public class TrainServiceImpl extends BaseServiceImpl<Train> implements TrainService {
	private static Logger logger = Logger.getLogger(TrainController.class);
	private final int POINTINTERVAL = 200;

	@Override
	public JSONArray uploadfile(HttpServletRequest request, HttpServletResponse response, CommonsMultipartFile[] file, String downLoadPath, Map<String, Object> param , int flag) throws TrainException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = null;
		Map<String, Object> hashMap = null;
		LinkedList<Double> speed = null;// 实际速度(km/h)
		LinkedList<Double> power = null;// 计算牵引功率
		LinkedList<Double> slope = null;// 当前坡度
		LinkedList<Double> force = null;// 计算牵引力
		String fileName = "";
		XSSFWorkbook wb = null;
		List<Train> trains = new LinkedList<>();
		// 时间格式小写hh表示12小时制，大写HH表示24小时制
		SimpleDateFormat datetemp = new SimpleDateFormat("HH:mm:ss");
		for (int i = 0; i < file.length; i++) {
			fileName = file[i].getFileItem().getName();
			double maxAcceleration = 0.0;// 最大正加速度
			double minusAcceleration = 0.0;//最大负加速度
			double maxAcceleration_rate = 0.0;// 最大正加速度变化率
			double minusAcceleration_rate = 0.0;// 最大负加速度变化率
			double temp1 = 0.0;
			double temp2 = 0.0;
			double temp3 = 0.0;
			String runingTime = "";//实际运行时间
			Long runingTime1 = 0L;
			double station_distance = 0.0;//站间实际长度
			int EBInum = 0;
			Train train = new Train();
			logger.debug(file[i].getFileItem().getName());
			if (!file[i].isEmpty()) {
				jsonObj = new JSONObject();
				hashMap = new HashMap<>();
				speed = new LinkedList<>();// 实际速度(km/h)
				power = new LinkedList<>();// 计算牵引力
				slope = new LinkedList<>();// 当前坡度
				force = new LinkedList<>();// 计算牵引力
				try {
					FileInputStream in = (FileInputStream) file[i].getInputStream();
					wb = new XSSFWorkbook(in);
					XSSFSheet sheet = wb.getSheetAt(1);
					if(flag==1){//以时间为依据
						for (int j = 11; j < sheet.getLastRowNum(); j++) {
						/*for (int j = 11; j < 14; j++) {*/
							XSSFRow row = sheet.getRow(j);
							// 记录数据
							speed.add(row.getCell(7).getNumericCellValue());
							slope.add(row.getCell(14).getNumericCellValue());
							force.add(row.getCell(16).getNumericCellValue());
							power.add(row.getCell(17).getNumericCellValue());
						}
						addLine(jsonArray, fileName + "-speed", speed, hashMap, jsonObj, 0);
						addLine(jsonArray, fileName + "-power", power, hashMap, jsonObj, 1);
						addLine(jsonArray, fileName + "-slope", slope, hashMap, jsonObj, 2);
						addLine(jsonArray, fileName + "-force", force, hashMap, jsonObj, 3);
					}else if(flag==2){//以距离为依据
						List<LinkedList<Double>> speedList = new LinkedList<>();
						List<LinkedList<Double>> slopeList = new LinkedList<>();
						List<LinkedList<Double>> forceList = new LinkedList<>();
						List<LinkedList<Double>> powerList = new LinkedList<>();
						station_distance = sheet.getRow(4).getCell(1).getNumericCellValue();
						for (int j = 11; j < sheet.getLastRowNum(); j++) {
						/*for (int j = 11; j < 19; j++) {*/
							XSSFRow row = sheet.getRow(j);
							speed = new LinkedList<>();
							slope = new LinkedList<>();
							force = new LinkedList<>();
							power = new LinkedList<>();
							//EBI触发次数
							EBInum = row.getCell(7).getNumericCellValue() > row.getCell(3).getNumericCellValue()?(EBInum+1):EBInum;
							// 记录数据
							speed.add(row.getCell(10).getNumericCellValue());
							speed.add(row.getCell(7).getNumericCellValue());
							slope.add(row.getCell(10).getNumericCellValue());
							slope.add(row.getCell(14).getNumericCellValue());
							force.add(row.getCell(10).getNumericCellValue());
							force.add(row.getCell(16).getNumericCellValue());
							power.add(row.getCell(10).getNumericCellValue());
							power.add(row.getCell(17).getNumericCellValue());
							speedList.add(speed);
							slopeList.add(slope);
							forceList.add(force);
							powerList.add(power);
							// 最大加速度计算
							double MaxAcceleration_temp = row.getCell(8) == null ? 0 : row.getCell(8).getNumericCellValue();
							maxAcceleration = MaxAcceleration_temp > maxAcceleration ? MaxAcceleration_temp : maxAcceleration;
							minusAcceleration = MaxAcceleration_temp < minusAcceleration ? MaxAcceleration_temp : minusAcceleration;
							// 最大加速度变化率
							if(j == 11){
								temp1 = MaxAcceleration_temp;
							}
							if(j == 12){
								temp2 = MaxAcceleration_temp;
							}
							if(j > 12 && j <= sheet.getLastRowNum()-1){
								BigDecimal b1 = new BigDecimal(Double.toString(temp1));
								BigDecimal b2 = new BigDecimal(Double.toString(temp2));
								BigDecimal b3 = new BigDecimal(Double.toString(MaxAcceleration_temp));
								b1.add(b2).doubleValue();
								temp3 = ((b3.subtract(b2)).subtract(b2.subtract(b1)).multiply(new BigDecimal(5))).doubleValue();//变化率
								//交换
								temp1 = temp2;
								temp2 = MaxAcceleration_temp;
								maxAcceleration_rate = temp3 > maxAcceleration_rate ? temp3 : maxAcceleration_rate;
								minusAcceleration_rate = temp3 < minusAcceleration_rate ? temp3 : minusAcceleration_rate;
								if(j<40){
									logger.debug("---------------------"+temp3);
								}
							}
							//计算实际运行时间
							if(j == 11){
								runingTime1 = row.getCell(0).getDateCellValue().getTime();
								logger.debug(datetemp.format(row.getCell(0).getDateCellValue()));
							}if(j == sheet.getLastRowNum()-1){
								//计算实际运行时间
								runingTime = (row.getCell(0).getDateCellValue().getTime()-runingTime1)/1000+"";
								logger.debug(runingTime);
								//计算停车精度
								BigDecimal b1 = new BigDecimal(Double.toString(station_distance));
								BigDecimal b2 = new BigDecimal(Double.toString(row.getCell(10).getNumericCellValue()));
								station_distance = b2.subtract(b1).doubleValue();
							}
							
						}
						// logger.debug(datetemp.format(row.getCell(0).getDateCellValue()));
						addLine2(jsonArray, fileName + "-speed", speedList, hashMap, jsonObj, 0);
						addLine2(jsonArray, fileName + "-power", slopeList, hashMap, jsonObj, 1);
						addLine2(jsonArray, fileName + "-slope", forceList, hashMap, jsonObj, 2);
						addLine2(jsonArray, fileName + "-force", powerList, hashMap, jsonObj, 3);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						wb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				train.setFile(fileName);
				train.setMaxAcceleration(maxAcceleration);
				train.setMinusAcceleration(minusAcceleration);
				train.setMaxAcceleration_rate(maxAcceleration_rate/0.2);
				train.setMinusAcceleration_rate(minusAcceleration_rate/0.2);
				train.setRuningTime(runingTime);
				train.setStationPrecision(station_distance);
				train.setEBInum(EBInum);
				trains.add(train);
			}
		}
		param.put("trains", trains);
		return jsonArray;
	}
	//以时间为依据
	public void addLine(JSONArray jsonArray, String name, List<Double> data, Map<String, Object> hashMap, JSONObject jsonObj, int yAxis) {
		// 清空
		hashMap.clear();
		jsonObj.clear();
		hashMap.put("name", name);
		hashMap.put("data", data);
		hashMap.put("yAxis", yAxis);
		hashMap.put("pointInterval", POINTINTERVAL);
		jsonObj.putAll(hashMap);
		jsonArray.add(jsonObj);
	}
	public void addLine2(JSONArray jsonArray, String name, List<LinkedList<Double>> data, Map<String, Object> hashMap, JSONObject jsonObj, int yAxis) {
		// 清空
		hashMap.clear();
		jsonObj.clear();
		hashMap.put("name", name);
		hashMap.put("data", data);
		hashMap.put("yAxis", yAxis);
		hashMap.put("pointInterval", POINTINTERVAL);
		jsonObj.putAll(hashMap);
		jsonArray.add(jsonObj);
	}
}
