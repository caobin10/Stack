package org.chuck.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.data;
import static android.view.View.Z;

public class CharSeqUtil {
	public static boolean isEmpty(CharSequence str){
		if(TextUtils.isEmpty(str)|| TextUtils.getTrimmedLength(str)==0){
			return true;
		}else {
			return false;
		}
	}

	public static boolean isNullOrEmpty(CharSequence str){
		if(str==null||isEmpty(str)){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 验证是否含有汉字
	 * @param str
	 * @return
	 */
	public static boolean isHaveChinese(CharSequence str){
		Pattern pattern= Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher=pattern.matcher(str);
		if(matcher.find()){
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isNumber(CharSequence str){
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证是否为正整数
	 * @param str
	 * @return
	 */
	public static boolean isPositiveNumber(CharSequence str){
		Pattern pattern = Pattern.compile("^[1-9]{1}[0-9]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static boolean isChLetterNum(CharSequence str){
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z\u4e00-\u9fa5]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 *
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String s, int defaultValue){
		try{
			if(s==null||s.isEmpty()){
				return defaultValue;
			}
			return Integer.parseInt(s);
		}catch (Exception e){
//			e.printStackTrace();
			return defaultValue;
		}
	}


	public static boolean isPostCode(CharSequence inputStr){
		String postCode = "[1-9]\\d{5}(?!\\d)";
		Pattern pattern= Pattern.compile(postCode);
		Matcher matcher=pattern.matcher(inputStr);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}

	public static Double parseDouble(String s, Double defaultValue){
		try{
			if(s==null||s.isEmpty()){
				return defaultValue;
			}
			return Double.parseDouble(s);
		}catch (Exception e){
//			e.printStackTrace();
			return defaultValue;
		}
	}

	public static boolean isAllChinese(String str){
		char[] charArr=str.toCharArray();
		for(char c:charArr){
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (!(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDate(CharSequence inputStr){
		String date="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$";
		Pattern pattern = Pattern.compile(date);
		Matcher matcher = pattern.matcher(inputStr);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	public static boolean isTelephone(CharSequence inputStr){
		String tele = "^((0[0-9]{2}\\-)+([2-9][0-9]{7}))|((0[0-9]{3}\\-)+([2-9][0-9]{6}))$";
		Pattern pattern= Pattern.compile(tele);
		Matcher matcher=pattern.matcher(inputStr);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
	public static boolean isMobilePhone(CharSequence inputStr){
		String mobile = "^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
		Pattern pattern= Pattern.compile(mobile);
		Matcher matcher=pattern.matcher(inputStr);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
	public static boolean isTeleMobilePhone(CharSequence inputStr){
		if(isTelephone(inputStr)||isMobilePhone(inputStr)){
			return true;
		}
		return false;
	}

	public static boolean isIdentNum(String identNum) {
		//二代身份证的长度
		if (identNum.length() != 18) {
			return false;
		}
		//验证二代身份证的格式
		Pattern pattern = Pattern.compile("[0-9]{10}[0,1]{1}[0-9]{1}[0,1,2,3]{1}[0-9]{4}([0-9]|[X]){1}");
		if (!pattern.matcher(identNum).matches()) {
			return false;
		}
		int year = Integer.parseInt(identNum.substring(6, 10));
		int month = Integer.parseInt(identNum.substring(10, 12));
		int day = Integer.parseInt(identNum.substring(12, 14));
		//验证年份是否超出常规
		if (year < 1800 || year > 2100) {
			return false;
		}
		//验证月份是否有效
		if (month < 1 || month > 12) {
			return false;
		}
		//验证天数是否有效
		int[] monthDayNum;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			monthDayNum = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,30, 31 };
		} else {
			monthDayNum = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31,30, 31 };
		}
		if (day < 1 || day > monthDayNum[month - 1]) {
			return false;
		}
		//验证因子是否有效
		int[] factor = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,8, 4, 2, 1 };
		String[] parity = new String[] { "1", "0", "X", "9", "8", "7", "6","5", "4", "3", "2" };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += Integer.parseInt(identNum.substring(i, i + 1)) * factor[i];
		}
		int bitIndex = sum % 11;
		String checkBit=identNum.substring(17);;
		if (!checkBit.equals(parity[bitIndex])) {
			return false;
		}
		return true;
	}

	public static boolean isLegalPassword(CharSequence str){
		Pattern pattern = Pattern.compile("^(?!\\D+$)(?!\\d+$)[a-zA-Z0-9]{6,16}$");
		Matcher matcher = pattern.matcher(str);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
	/**
	 * 计算字符串orig中有多少个same
	 * 例如: ssss 字符串中有多少个ss 其结果是2而非3
	 * @param orig 要计算的字符串
	 * @param same 相同字符串
	 * @return
	 */
	public static int computeSame(String orig, String same){
		int count=0;
		if(!TextUtils.isEmpty(orig)&&!TextUtils.isEmpty(same)){
			int i=-1;
			while ((i=orig.indexOf(same))>=0){
				count++;
				orig=orig.substring(i+same.length());
			}
		}
		return count;
	}
	
	/*public static boolean isLegalPassword(CharSequence str){
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{6,15}$");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}*/
	//*****
//	public static boolean isChar(String data)
//	{
//		if ( data.length() != 0 )
//		{
//			for (int i = data.length() - 1;;i -- )
//			{
//				char c = data.charAt(i);
//				if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')))
//				{
//					while ( i >= -1 )
//					{
//						i--;
//						if (!((data.charAt(i) >= 'a' && data.charAt(i) <= 'z') || (data.charAt(i) >= 'A' && data.charAt(i) <= 'Z')))
//						{
//							return false;
//						}
//					}
//					return true;
//				}
//				return false;
//			}
//		}
//		return false;
//
////		if (data.length() != 0 )
////		{
////			if (((data.charAt(data.length() - 1) >= 'a' && data.charAt(data.length() - 1) <= 'z') || (data.charAt(data.length() - 1) >= 'A' && data.charAt(data.length() - 1) <= 'Z')))
////				return true;
////			else
////				return false;
////		}
////		return false;
//	}
	//****
	public static boolean stringFilter(String str)throws PatternSyntaxException
	{// 只允许字母、数字和汉字
		//String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
		Pattern p = Pattern.compile("[^a-zA-Z0-9\u4E00-\u9FA5]");
		Matcher matcher = p.matcher(str);
		if (!matcher.matches())
			return false;
		return true;
		//matcher.replaceAll("").trim();
	}

	public static boolean isNaN(String brreedIncome)
	{
		if (brreedIncome.toString().length() != 0 )
		{
			for (int i = 0 ; i< brreedIncome.toString().length();i++)
			{
				char c = brreedIncome.charAt(i);
				if ( !(c >= '0' && c <= '9') )
					return true;
			}
		}
		return false;
	}

	//***********
	//***********
}
