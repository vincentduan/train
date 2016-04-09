package com.train.entity;

public class Train extends BaseQuery<Train> {
	private Integer id;
	private String startStation;// 始发站
	private String terminalStation;// 终点站
	private String startTime;// 发车时刻
	private String actualLength;// 站间实际长度
	private String endTime;// 停站时刻
	private String runingTime;// 站间运行时间
	private String loadFactor;// 满载率
	private String file;
	private double maxAcceleration;//最大正加速度
	private double minusAcceleration;//最大负加速度
	private double maxAcceleration_rate;//最大正加速度变化率
	private double minusAcceleration_rate;//最大负加速度变化率

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
