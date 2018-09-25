package com.gimc.user.common.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//计算时间的类
public class DateAlgorithm {
	public long day;
	public long hours;
	public long minutes;
	public Long[] result;
	public long sec;
	
	//计算两个时间的差
	public  Long[] Datepoor(Date system,Date appointmentTime){
		
		String sysFormat=DateUtils.formatDateTime(system);
		String appTime=DateUtils.formatDateTime(appointmentTime);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    long ns = 1000;
		
		try {
			Date sys=df.parse(sysFormat);
			Date app=df.parse(appTime);
			
			long diff = sys.getTime() - app.getTime(); 
		    day= diff / (1000 * 60 * 60 * 24);  
		    hours = (diff-day*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		    minutes=(diff-day*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		    sec= diff % nd % nh % nm / ns;
		    result=new Long[4];
		    result[0]=Math.abs(day);
		    result[1]=Math.abs(hours);
		    result[2]=Math.abs(minutes);
		    result[3]=Math.abs(sec);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;	
	}
}
