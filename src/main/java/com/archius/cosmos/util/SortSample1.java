package com.archius.cosmos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
/**
 * @author Thulasiram
 *
 */
public class SortSample1 {

    public static void main(String[] args) {
   
      List<String> months = Arrays.asList( "Feb", "Jul", "Jan", "Apr", "Dec",       "Aug", "Oct", "May", "Sep", "Nov", "Jun","Mar");
  
      System.out.println(months);
  
      final Comparator<String> dateCompare = new Comparator<String>() {
 
         public int compare(String o1, String o2) {
     
           SimpleDateFormat s = new SimpleDateFormat("MMM");
           Date s1 = null;
           Date s2 = null;
           try {
             s1 = s.parse(o1);
             s2 = s.parse(o2);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           return s1.compareTo(s2);
         }
      };
  
     Collections.sort(months, dateCompare);
  
     System.out.print(months);
 } 
}
