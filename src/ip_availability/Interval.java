package ip_availability;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Interval {
	Date start;
	Date end;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy­-MM-­dd'T'HH'_'mm'_'ss.SSSZ");
	Interval(Date startDate){
		start = startDate;
		end = null;
	}
	public void setLogout(Date logoutDate){
		end = logoutDate;
	}
	
	@Override
	public String toString(){
		String string = "";
		string += ":" + dateFormat.format(start);
		if(end!=null)
			string += ":" + dateFormat.format(end);
		return string;
	}
}
