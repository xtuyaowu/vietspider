/***************************************************************************
 * Copyright 2001-2009 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package org.vietspider.content.nlp;

import java.util.ArrayList;
import java.util.List;

import org.vietspider.chars.CharsUtil;


/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Sep 24, 2009  
 */
public class NameCode {
  
  private int code;
  private List<char[]> names = new ArrayList<char[]>(10);
  
  public NameCode(String...values) {
    code = values[0].hashCode();
    for(int i  = 0; i < values.length; i++) {
      names.add(values[i].toCharArray());
    }
  }
  
  public int getCode() { return code; }
  public void setCode(int code) { this.code = code; }

  public  List<char[]>  getNames() { return names; }
  public void setNames( List<char[]> cities) { this.names = cities; }
  
  public  String getName() { return new String(names.get(0)); }
  
  public int isName(char [] chars, int from) {
    for(int i = 0; i < names.size(); i++) {
      char [] patterns = names.get(i);
      int index = CharsUtil.indexOfIgnoreCase(chars, patterns, from);
//      System.out.println(index + " ==>"+ new String(patterns));
      if(index < 0) continue;
      for(int k = 0; k < patterns.length; k++) {
        if(patterns[k] == ' ') return index + patterns.length;
      }
//      System.out.println(" thay co index "+ index+ " name "+ new String(names.get(i)) + " |"+ chars[index + names.get(i).length]+"|");
      if((index > 1 && Character.isLetterOrDigit(chars[index-1])) || 
          ( index + patterns.length < chars.length 
              && Character.isLetterOrDigit(chars[index + patterns.length]))) continue;
     return index + patterns.length;
    }
    return -1;
  }
  
  public int[] isName2(char [] chars, int from) {
    for(int i = 0; i < names.size(); i++) {
      char [] patterns = names.get(i);
      int index = CharsUtil.indexOfIgnoreCase(chars, patterns, from);
//      System.out.println(index + " ==>"+ new String(patterns));
      if(index < 0) continue;
      for(int k = 0; k < patterns.length; k++) {
        if(patterns[k] == ' ') return new int[]{index, index + patterns.length};
      }
//      System.out.println(" thay co index "+ index+ " name "+ new String(names.get(i)) + " |"+ chars[index + names.get(i).length]+"|");
      if((index > 1 && Character.isLetterOrDigit(chars[index-1])) || 
          ( index + patterns.length < chars.length 
              && Character.isLetterOrDigit(chars[index + patterns.length]))) continue;
     return new int[]{index, index + patterns.length};
    }
    return null;
  }
  
  
  
}
