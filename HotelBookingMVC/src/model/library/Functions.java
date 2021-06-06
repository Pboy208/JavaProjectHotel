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
