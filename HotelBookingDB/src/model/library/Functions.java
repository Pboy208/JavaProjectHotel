package model.library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

	public static boolean checkPhoneNumber(String phoneNumber) {
		if(phoneNumber.length()!=10) {
			return false;
		}
		try {
			Integer.parseInt(phoneNumber);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean checkPrice(String price) {
		try {
			Integer.parseInt(price);
		} catch (Exception e) {
			try {
				String array[]=price.split(",");
				for(int i=1;i<array.length;i++) {
					array[0]=array[i-1].concat(array[i]);
				}
				Integer.parseInt(array[0]);
			} catch (Exception e2) {
				return false;
			}
			return true;
		}
		return true;
	}
	
	public static int priceToInt(String price) {
		int result;
		try {
			result = Integer.parseInt(price);
			return result;
		} catch (Exception e) {
			try {
				String array[]=price.split(",");
				String resultString="";
				for(int i=0;i<array.length;i++) 
					resultString=resultString.concat(array[i]);
				
				result = Integer.parseInt(resultString);
				return result;
			} catch (Exception e2) {
				return -1;
			}
		}
	}
	
	public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if(mat.matches()){
            return true;
        }else{
           return false;
        }   
	}
	
	
}
