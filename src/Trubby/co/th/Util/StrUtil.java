package Trubby.co.th.Util;

public class StrUtil {

	public static String getLevelText(int level){
		
		switch (level) {
		case 1:return "I";
		case 2:return "II";
		case 3:return "III";
		case 4:return "IV";
		case 5:return "V";
		default:break;
		}
		
		return null;
	}
	
}
