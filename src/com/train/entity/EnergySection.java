package com.train.entity;

public class EnergySection {
	String start;
	String end;
	double energe;
	String info;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public double getEnerge() {
		return energe;
	}
	public void setEnerge(double energe) {
		this.energe = energe;
	}
	@Override
	public String toString() {
		return "EnergySection [start=" + start + ", end=" + end + ", energe="
				+ energe + ", info=" + info + "]";
	}
	
}
