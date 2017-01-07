package com.archius.cosmos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.archius.cosmos.model.DownTime;
import com.archius.cosmos.model.UtilizationVo;
/**
 * @author Thulasiram
 *
 */
public class BeanComparator implements Comparator<Object> {

	private String getter;

	public BeanComparator(String field) {
		this.getter = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	public BeanComparator() {
	}	

	public final static Comparator<UtilizationVo> utilizationMonthCompare = new Comparator<UtilizationVo>() {

		public int compare(UtilizationVo o1, UtilizationVo o2) {

			SimpleDateFormat s = new SimpleDateFormat("MMM");
			Date s1 = null;
			Date s2 = null;
			try {
				s1 = s.parse(o1.getMonths());
				s2 = s.parse(o2.getMonths());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return s1.compareTo(s2);
		}
	};
	
	public final static Comparator<DownTime> downTimeMonthCompare = new Comparator<DownTime>() {

		public int compare(DownTime o1, DownTime o2) {

			SimpleDateFormat s = new SimpleDateFormat("MMM");
			Date s1 = null;
			Date s2 = null;
			try {
				s1 = s.parse(o1.getMonths());
				s2 = s.parse(o2.getMonths());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return s1.compareTo(s2);
		}
	};
	public int compare(Object o1, Object o2) {
		try {
			if (o1 != null && o2 != null) {
				
				o1 = o1.getClass().getMethod(getter, new Class[0]).invoke(o1, new Object[0]);
				o2 = o2.getClass().getMethod(getter, new Class[0]).invoke(o2, new Object[0]);
			}
		} catch (Exception e) {
			// If this exception occurs, then it is usually a fault of the
			// developer.
			throw new RuntimeException("Cannot compare " + o1 + " with " + o2 + " on " + getter, e);
		}

		return (o1 == null) ? -1 : ((o2 == null) ? 1 : ((Comparable<Object>) o1).compareTo(o2));
	}

	/*
	 * public static void main(String args[]) throws Exception { Emp e1 = new
	 * Emp("May",21); Emp e2 = new Emp("Feb",45); Emp e3 = new Emp("Jun",11);
	 * Emp e4 = new Emp("Jan",87); List<Emp> empList = new ArrayList<Emp>();
	 * empList.add(e1); empList.add(e2); empList.add(e3); empList.add(e4);
	 * 
	 * System.out.println("Emp list befor sort "); empList.forEach( (Emp emp) ->
	 * {System.out.println(emp.name +"    "+emp.age);});
	 * Collections.sort(empList, new BeanComparator("name"));
	 * System.out.println("Emp list after sort "); empList.forEach( (Emp emp) ->
	 * {System.out.println(emp.name +"    "+emp.age);});
	 * 
	 * }
	 */
}
