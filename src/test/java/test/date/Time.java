package test.date;

import java.text.SimpleDateFormat;

public class Time {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String [] times={
	
				"2014-06-21T03:25:53.000-0700"
			
		};
		
		String time="2015-02-05T23:32:32.000-0700";
		
		
//		Date date=new Date();
//		date.setTime(1423065600000L);
//		System.out.println(date.toString());
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		/*	Date date;
			for(String s:times){
				try {
					date = sdf.parse(s);
					System.out.println(date.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}*/
		String date=time.split("\\.")[0];
		String [] t=date.split("T");
		for(String s:t){
			System.out.println(s);
		}
		System.out.println(date);
	}

}
