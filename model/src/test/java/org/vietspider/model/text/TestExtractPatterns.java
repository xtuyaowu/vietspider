/***************************************************************************
 * Copyright 2001-2008 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package org.vietspider.model.text;

import static org.vietspider.link.pattern.LinkPatternFactory.createPatterns;

import java.util.ArrayList;
import java.util.List;

import org.vietspider.link.pattern.LinkPatternExtractor;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Mar 25, 2008  
 */
public class TestExtractPatterns {
  
  public static void main(String[] args) throws Exception {
    String [] elements = {
        "http://blog.360.yahoo.com/blog-*?cq=*",
        "http://blog.360.yahoo.com/blog-*",
        "http://blog.360.yahoo.com/*/",
        "http://blog.360.yahoo.com/*",
    };
    LinkPatternExtractor linkPatterns = createPatterns(
        LinkPatternExtractor.class, elements);
    String url = "http://blog.360.yahoo.com/mylove24122006";
//    url +=  "blog mình là : http://blog.360.yahoo.com/buddy_cherry06 <- vô và add thoải mái hén ^^\" >_^";
    List<String> list = new ArrayList<String>();
    String value = linkPatterns.extract(url, list);
    for(String ele : list) {
      System.out.println("===== > "+ ele);
    }
    System.out.println(" con lai "+ value);
    
    url = "http://blog.360.yahoo.com/blog-CPx5S84yc6cuTos0CO6jZGJoTs4-?cq=1&";
    System.out.println(" cuoi cung ta co " + linkPatterns.extract(url));
  }
  
}
