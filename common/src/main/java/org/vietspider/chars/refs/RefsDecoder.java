/***************************************************************************
 * Copyright 2004-2006 The VietSpider All rights reserved.  *
 **************************************************************************/
package org.vietspider.chars.refs;

import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.vietspider.chars.CharsUtil;
import org.vietspider.chars.XMLDataDecoder;

/**
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * May 8, 2006
 */
public class RefsDecoder implements XMLDataDecoder {

  private Lock lock = new ReentrantLock();

  public synchronized char[] decode(char [] chars) { 
    try {
      lock.lock();
      return decodeChars(chars);
    }finally{
      lock.unlock();
    }    
  }

  private char[] decodeChars(char [] chars) {    
    CharRefs charRefs = new CharRefs();
    if(!charRefs.isSorted()) charRefs.sort(new Comparator<CharRef>(){
      public int compare(CharRef o1, CharRef o2){
        return o1.getName().compareTo(o2.getName());
      }
    });
    int index = CharsUtil.indexOf(chars,'&', 0) ;
    if(index < 0 || CharsUtil.indexOf(chars, ';', index) < 0)  {
//      System.out.println(" da xay ra "+ index);
      return chars;
    }
    CharsSequence decode  = new CharsSequence(chars.length);
    int temp = 0 ;
    int end = 0;
    boolean ref = true;
    char[] ckey = null;
    while (index < chars.length) {      
      if(temp < index) copy(chars, decode, temp, index);
      end = CharsUtil.indexOf(chars, ';', index);
      if(end < 0) break;
      if(chars[index+1] == '#') {
        ckey = new char[end - index - 2];
        System.arraycopy(chars, index + 2, ckey, 0, ckey.length);
        ref = false;
      } else {
        ckey = new char[end - index - 1]; 
        System.arraycopy(chars, index + 1, ckey, 0, ckey.length);        
        ref = true;
      }      
      if(!ref) {
        ref = decode(ckey, decode);
      } else {
        if (ckey.length > 0 && Character.isLetter(ckey[0])) {  
          CharRef item = charRefs.searchByName(new String(ckey), new Comparator<CharRef>(){
            public int compare(CharRef o1, CharRef o2){
              return o1.getName().compareTo(o2.getName());
            }
          });
          if (item != null) {
            decode.append ((char)item.getValue ());
          }else{
            copy(chars, decode, index, end + 1);
          }
        } else {
          copy(chars, decode, index, end);
        }      
      }      
      temp = end+1; 
      index = CharsUtil.indexOf(chars,'&', temp) ;
      if(index < 0) break;        
    } 
    copy(chars, decode, temp, chars.length);
    return decode.getValues();
  }

  private void copy(char [] chars, CharsSequence decode, int start, int end){
    while(start < chars.length){
      if(start == end) break;
      decode.append (chars[start]);
      start++;
    }
  }

  private boolean decode(char [] values, CharsSequence decode){
    char character;
    int number = 0;
    int radix = 0;    
    boolean done = false; 
    int i = 0;
    while (i < values.length && !done) {
      character = values[i]; 
      if(Character.isDigit(character)){
        if (radix == 0) radix = 10;
        number = number * radix + (character - '0');
      }else{
        switch (character) {      
        case 'A': case 'B': case 'C':  case 'D': case 'E':  case 'F':
          if (radix == 16)
            number = number * radix + (character - 'A' + 10);
          else
            done = true;
          break;
        case 'a': case 'b': case 'c': case 'd': case 'e':   case 'f':
          if (radix == 16)
            number = number * radix + (character - 'a' + 10);
          else
            done = true;
          break;
        case 'x': case 'X':
          if (radix == 0) radix = 16;  else done = true;
          break;
        case ';':
          done = true;
          i++;
          break;
        default:
          done = true;
        break;
        }
      }
      if (!done) i++;
    }
    if (number == 0) return true;
    decode.append((char)number);
    return false;
  }
}
