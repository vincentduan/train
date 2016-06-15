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
import com.train.entity.EnergySection;
import com.train.entity.Train;
import com.train.exception.TrainException;
import com.train.service.TrainService;

@Service
public class TrainServiceImpl implements TrainService {
	private static Logger logger = Logger.getLogger(TrainController.class);
	private final int POINTINTERVAL = 200;

	@Override
	public JSONArray uploadfile(HttpServletRequest request, HttpServletResponse response, CommonsMultipartFile[] file, String upLoadPath, Map<String, Object> param , int flag) throws TrainException {
		JSONArray jsonArray = new JSONArray();
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
							speed.add(row.getCell(9).getNumericCellValue());
							slope.add(row.getCell(18).getNumericCellValue());
							force.add(row.getCell(20).getNumericCellValue());
							power.add(row.getCell(21).getNumericCellValue());
						}
						addLine(jsonArray, fileName + "-speed", speed, 0);
						addLine(jsonArray, fileName + "-power", power, 1);
						addLine(jsonArray, fileName + "-slope", slope, 2);
						addLine(jsonArray, fileName + "-force", force, 3);
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
							EBInum = row.getCell(9).getNumericCellValue() > row.getCell(3).getNumericCellValue()?(EBInum+1):EBInum;
							// 记录数据
							speed.add(row.getCell(11).getNumericCellValue());
							speed.add(row.getCell(7).getNumericCellValue());
							slope.add(row.getCell(11).getNumericCellValue());
							slope.add(row.getCell(14).getNumericCellValue());
							force.add(row.getCell(11).getNumericCellValue());
							force.add(row.getCell(16).getNumericCellValue());
							power.add(row.getCell(11).getNumericCellValue());
							power.add(row.getCell(17).getNumericCellValue());
							speedList.add(speed);
							slopeList.add(slope);
							forceList.add(force);
							powerList.add(power);
							// 最大加速度计算
							double MaxAcceleration_temp = row.getCell(10) == null ? 0 : row.getCell(10).getNumericCellValue();
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
						addLine2(jsonArray, fileName + "-speed", speedList, 0);
						addLine2(jsonArray, fileName + "-power", slopeList, 1);
						addLine2(jsonArray, fileName + "-slope", forceList, 2);
						addLine2(jsonArray, fileName + "-force", powerList, 3);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						wb.close();
					} catch (IOException e) {
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
	public void addLine(JSONArray jsonArray, String name, List<Double> data, int yAxis) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", name);
		hashMap.put("data", data);
		hashMap.put("yAxis", yAxis);
		hashMap.put("pointInterval", POINTINTERVAL);
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(hashMap);
		jsonArray.add(jsonObj);
	}
	public void addLine2(JSONArray jsonArray, String name, List<LinkedList<Double>> data, int yAxis) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", name);
		hashMap.put("data", data);
		hashMap.put("yAxis", yAxis);
		//hashMap.put("pointInterval", POINTINTERVAL);
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(hashMap);
		jsonArray.add(jsonObj);
	}
	@Override
	public List<Train> uploadfiles(HttpServletRequest request, HttpServletResponse response, CommonsMultipartFile[] file, String upLoadPath) throws TrainException {
		List<Train> trains = new LinkedList<>();
		// 时间格式小写hh表示12小时制，大写HH表示24小时制
		SimpleDateFormat datetemp = new SimpleDateFormat("HH:mm:ss");
		for (int i = 0; i < file.length; i++) {
			XSSFWorkbook wb = null;
			Train train = new Train();
			LinkedList<Double> speed = null;// 实际速度(km/h)
			LinkedList<Double> speed_distance = null;// 实际速度(km/h)-距离依据
			LinkedList<Double> power = null;// 计算牵引功率
			LinkedList<Double> power_distance = null;// 计算牵引功率-距离依据
			LinkedList<Double> slope = null;// 当前坡度
			LinkedList<Double> slope_distance = null;// 当前坡度-距离依据
			LinkedList<Double> force = null;// 计算牵引力
			LinkedList<Double> force_distance = null;// 计算牵引力-距离依据
			String fileName = file[i].getFileItem().getName();
			double maxAcceleration = 0.0;// 最大正加速度
			double minusAcceleration = 0.0;//最大负加速度
			double maxAcceleration_rate = 0.0;// 最大正加速度变化率
			double minusAcceleration_rate = 0.0;// 最大负加速度变化率
			double temp1 = 0.0; //加速度变化率temp
			double temp2 = 0.0; //加速度变化率temp
			double temp3 = 0.0; //加速度变化率temp
			String runingTime = "";//实际运行时间
			Long runingTime1 = 0L;
			double station_distance = 0.0;//站间实际长度
			int EBInum = 0; //EBI触发次数
			String flag = ""; //区间标记 1:牵引2:制动3:惰行
			List<EnergySection> energeSections = new LinkedList<>();
			if (!file[i].isEmpty()) {
				speed = new LinkedList<>();// 实际速度(km/h)
				power = new LinkedList<>();// 计算牵引力
				slope = new LinkedList<>();// 当前坡度
				force = new LinkedList<>();// 计算牵引力
				try {
					FileInputStream in = (FileInputStream) file[i].getInputStream();
					wb = new XSSFWorkbook(in);
					XSSFSheet sheet = wb.getSheetAt(1);
					List<LinkedList<Double>> speedList = new LinkedList<>();
					List<LinkedList<Double>> slopeList = new LinkedList<>();
					List<LinkedList<Double>> forceList = new LinkedList<>();
					List<LinkedList<Double>> powerList = new LinkedList<>();
					String start_station = sheet.getRow(2).getCell(1).getStringCellValue();
					String end_station = sheet.getRow(2).getCell(2).getStringCellValue();
					int last_rowNum = 0;
					for (int j = 11; j < sheet.getLastRowNum(); j++) {
						XSSFRow row = sheet.getRow(j);
						if(!(row.getCell(27).getStringCellValue().equals(start_station)&&row.getCell(28).getStringCellValue().equals(end_station))){
							last_rowNum = j;
							break;
						}
					}
					//last_rowNum = 30;
					for (int j = 11; j < last_rowNum; j++) {
						XSSFRow row = sheet.getRow(j);
						speed_distance = new LinkedList<>();// 实际速度(km/h)-距离依据
						power_distance = new LinkedList<>();// 计算牵引力-距离依据
						slope_distance = new LinkedList<>();// 当前坡度-距离依据
						force_distance = new LinkedList<>();// 计算牵引力-距离依据
						// 记录数据
						double speed_temp = row.getCell(9).getNumericCellValue();
						double slope_temp = row.getCell(18).getNumericCellValue();
						double distance_temp = "".equals(row.getCell(11))?0.0:row.getCell(11).getNumericCellValue();
						double force_temp = "".equals(row.getCell(20).getStringCellValue())?0.0:row.getCell(20).getNumericCellValue();
						double power_temp = "".equals(row.getCell(21).getStringCellValue())?0.0:row.getCell(21).getNumericCellValue();
						speed.add(speed_temp);
						speed_distance.add(distance_temp);
						speed_distance.add(speed_temp);
						slope.add(slope_temp);
						slope_distance.add(distance_temp);
						slope_distance.add(slope_temp);
						force.add(force_temp);
						force_distance.add(distance_temp);
						force_distance.add(force_temp);
						power.add(power_temp);
						power_distance.add(distance_temp);
						power_distance.add(power_temp);
						speedList.add(speed_distance);
						slopeList.add(slope_distance);
						forceList.add(force_distance);
						powerList.add(power_distance);
						/************************/
						// 最大加速度计算
						double MaxAcceleration_temp = "".equals(row.getCell(10).getStringCellValue())?0.0 : row.getCell(10).getNumericCellValue();
						maxAcceleration = MaxAcceleration_temp > maxAcceleration ? MaxAcceleration_temp : maxAcceleration;
						minusAcceleration = MaxAcceleration_temp < minusAcceleration ? MaxAcceleration_temp : minusAcceleration;
						// 最大加速度变化率
						if(j == 11){
							temp1 = MaxAcceleration_temp;
						}
						if(j == 12){
							temp2 = MaxAcceleration_temp;
						}
						if(j > 12 && j <= last_rowNum-1){
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
						}
						//计算实际运行时间
						if(j == 11){
							runingTime1 = datetemp.parse(row.getCell(0)+"").getTime();
						}if(j == last_rowNum-1){
							//计算实际运行时间
							runingTime = (datetemp.parse(row.getCell(0)+"").getTime()-runingTime1)/1000+"";
							//计算停车精度
							BigDecimal b1 = new BigDecimal(Double.toString(station_distance));
							BigDecimal b2 = new BigDecimal(Double.toString("".equals(row.getCell(10).getStringCellValue())?0.0 : row.getCell(10).getNumericCellValue()));
							station_distance = b2.subtract(b1).doubleValue();
						}
						// 计算区间能耗
						if(j == 11){
							if("0x105".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
								flag = "2";//制动
							}
							if("0x103".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
								flag = "1";//牵引
							}
							if("25.0".equals(row.getCell(17).toString())){
								flag = "3";//惰行
							}
							EnergySection energySection = new EnergySection();
							energySection.setStart(row.getCell(0)+"");
//							double d1 = row.getCell(13).getNumericCellValue();
//							double d2 = row.getCell(14).getNumericCellValue();
//							energySection.setEnerge(d1*d2);
							energySection.setInfo(flag);
							energySection.setEnd(row.getCell(0)+"");
							energeSections.add(energySection);
						}else{
							String flag2 = "";
							if("0x105".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
								flag2 = "2";//制动
							}
							if("0x103".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
								flag2 = "1";//牵引
							}
							if("25.0".equals(row.getCell(17).toString())){
								flag2 = "3";//惰行
							}
							if(flag.equals(flag2)){
								EnergySection energySection = energeSections.get(energeSections.size()-1);//得到上一次的能量
//								double d1 = row.getCell(13).getNumericCellValue();
//								double d2 = row.getCell(14).getNumericCellValue();
//								energySection.setEnerge((d1*d2)+energySection.getEnerge());
								energySection.setInfo(flag);
								energySection.setEnd(row.getCell(0)+"");
							}else{
								if("0x105".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
									flag = "2";//制动
								}
								if("0x103".equals(row.getCell(16).getStringCellValue()) && !"25.0".equals(row.getCell(17).toString())){
									flag = "1";//牵引
								}
								if("25.0".equals(row.getCell(17).toString())){
									flag = "3";//惰行
								}
								EnergySection energySection = new EnergySection();
								energySection.setStart(row.getCell(0)+"");
								energySection.setEnerge(0.0);
//								double d1 = row.getCell(13).getNumericCellValue();
//								double d2 = row.getCell(14).getNumericCellValue();
//								energySection.setEnerge(d1*d2);
								energySection.setEnd(row.getCell(0)+"");
								energySection.setInfo(flag);
								energeSections.add(energySection);
							}
						}
					
					}
					List<JSONObject> times_json = new LinkedList<>();
					List<JSONObject> distance_json = new LinkedList<>();
					times_json.add(addLine3(fileName + "-speed", speed, 0));
					times_json.add(addLine3(fileName + "-power", power, 1));
					times_json.add(addLine3(fileName + "-slope", slope, 2));
					times_json.add(addLine3(fileName + "-force", force, 3));
					distance_json.add(addLine4(fileName + "-speed", speedList, 0));
					distance_json.add(addLine4(fileName + "-power", slopeList, 1));
					distance_json.add(addLine4(fileName + "-slope", forceList, 2));
					distance_json.add(addLine4(fileName + "-force", powerList, 3));
					train.setFile(fileName);
					train.setMaxAcceleration(maxAcceleration);
					train.setMinusAcceleration(minusAcceleration);
					train.setMaxAcceleration_rate(maxAcceleration_rate/0.2);
					train.setMinusAcceleration_rate(minusAcceleration_rate/0.2);
					train.setRuningTime(runingTime);
					train.setStationPrecision(station_distance);
					train.setEBInum(EBInum);
					train.setTimes_json(times_json);
					train.setDistance_json(distance_json);
					train.setEnergySection(energeSections);
					trains.add(train);
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug(e);
				}
				
			}
		}
		return trains;
	}
	
	//以时间为依据
	public JSONObject addLine3(String name, List<Double> data, int yAxis) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", name);
		hashMap.put("data", data);
		hashMap.put("yAxis", yAxis);
		hashMap.put("pointInterval", POINTINTERVAL);
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(hashMap);
		return jsonObj;
	}
	
	public JSONObject addLine4(String name, List<LinkedList<Double>> data, int yAxis) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", name);
		hashMap.put("data", data);
		//logger.debug("**********************"+data);
		hashMap.put("yAxis", yAxis);
		//hashMap.put("pointInterval", POINTINTERVAL);
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(hashMap);
		return jsonObj;
	}
	
}
