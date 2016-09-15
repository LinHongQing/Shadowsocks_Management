package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeFormatUtil {
	public static String UnixTimeStamp2Date(int timeStamp, String formats) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Long timestamp = (long)(timeStamp) * 1000;
		String date = sd.format(new Date(timestamp));
		return date;
	}
	
	public static int getCurrentUnixTimeStamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static void main(String[] param) {
		System.out.println(getCurrentUnixTimeStamp());
		System.out.println(UnixTimeStamp2Date(getCurrentUnixTimeStamp(), "yyyy-MM-dd hh:mm:ss"));
	}
}
