package com.train.entity;

import java.util.List;

import net.sf.json.JSONObject;

public class Train {
	private Integer id;
	private String startStation;// 始发站
	private String terminalStation;// 终点站
	private String startTime;// 发车时刻
	private String actualLength;// 站间实际长度
	private String endTime;// 停站时刻
	private String runingTime;// 站间运行时间
	private String loadFactor;// 满载率
	private String file;// 上传文件名
	private double maxAcceleration;// 最大正加速度
	private double minusAcceleration;// 最大负加速度
	private double maxAcceleration_rate;// 最大正加速度变化率
	private double minusAcceleration_rate;// 最大负加速度变化率
	private double stationPrecision;// 停车精度
	private int EBInum;// EBI触发次数
	private List<JSONObject> times_json; // 以时间为依据
	private List<JSONObject> distance_json; // 以距离为依据
	private List<EnergySection> energySection; //区间能耗


	public List<EnergySection> getEnergySection() {
		return energySection;
	}

	public void setEnergySection(List<EnergySection> energySection) {
		this.energySection = energySection;
	}

	public List<JSONObject> getTimes_json() {
		return times_json;
	}

	public void setTimes_json(List<JSONObject> times_json) {
		this.times_json = times_json;
	}

	public List<JSONObject> getDistance_json() {
		return distance_json;
	}

	public void setDistance_json(List<JSONObject> distance_json) {
		this.distance_json = distance_json;
	}

	public int getEBInum() {
		return EBInum;
	}

	public void setEBInum(int eBInum) {
		EBInum = eBInum;
	}

	public double getStationPrecision() {
		return stationPrecision;
	}

	public void setStationPrecision(double stationPrecision) {
		this.stationPrecision = stationPrecision;
	}

	public double getMaxAcceleration_rate() {
		return maxAcceleration_rate;
	}

	public void setMaxAcceleration_rate(double maxAcceleration_rate) {
		this.maxAcceleration_rate = maxAcceleration_rate;
	}

	public double getMinusAcceleration_rate() {
		return minusAcceleration_rate;
	}

	public void setMinusAcceleration_rate(double minusAcceleration_rate) {
		this.minusAcceleration_rate = minusAcceleration_rate;
	}

	public double getMinusAcceleration() {
		return minusAcceleration;
	}

	public void setMinusAcceleration(double minusAcceleration) {
		this.minusAcceleration = minusAcceleration;
	}

	public String getFile() {
		return file;
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(double maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getTerminalStation() {
		return terminalStation;
	}

	public void setTerminalStation(String terminalStation) {
		this.terminalStation = terminalStation;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getActualLength() {
		return actualLength;
	}

	public void setActualLength(String actualLength) {
		this.actualLength = actualLength;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRuningTime() {
		return runingTime;
	}

	public void setRuningTime(String runingTime) {
		this.runingTime = runingTime;
	}

	public String getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(String loadFactor) {
		this.loadFactor = loadFactor;
	}

}
