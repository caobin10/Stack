package org.chuck.bean;

import java.util.Calendar;

public class IdentNum {
	private int sex;
	private int year;
	private int month;
	private int day;
	private String checkBit;
	private String birthdate;
	private String yearStr;
	private String monthStr;
	private String dayStr;
	
	public IdentNum(String identNum) {
		if(identNum.length()==15){
//			identNum=iden15ToIden18(identNum);
		}
		this.birthdate=identNum.substring(6, 14);
		this.checkBit=identNum.substring(17);
		this.yearStr=birthdate.substring(0, 4);
		this.monthStr=birthdate.substring(4, 6);
		this.dayStr=birthdate.substring(6, 8);	
		this.year= Integer.parseInt(yearStr);
		this.month= Integer.parseInt(monthStr);
		this.day= Integer.parseInt(dayStr);
		this.sex= Integer.parseInt(identNum.substring(16, 17));
	}
	public int getSex() {
		return sex; 
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public String getCheckBit() {
		return checkBit;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public int getAge(){
		Calendar calendar= Calendar.getInstance();
		int currYear=calendar.get(Calendar.YEAR);
		return currYear-getYear();
	}
	public String getYearStr() {
		return yearStr;
	}
	public String getMonthStr() {
		return monthStr;
	}
	public String getDayStr(){
		return dayStr;
	}
	
	public String getBithdateStr(){
		return getYearStr()+"-"+getMonthStr()+"-"+getDayStr();
	}
	
	public String iden15ToIden18(String iden15){
		return "";
	}
}
