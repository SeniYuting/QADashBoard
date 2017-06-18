package test.arrays;

import java.util.ArrayList;
import java.util.List;

public class testArrary {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Long> list=new ArrayList<Long>();
		list.add(3124214123412341L);
		list.add(5435454354356384L);
		list.add(3654564365465846L);
		list.add(68546354575465L);
		System.out.println(list.get(2)-list.get(3));
		
	}

}
