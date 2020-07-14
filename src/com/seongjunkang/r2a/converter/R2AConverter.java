package com.seongjunkang.r2a.converter;

import java.util.HashMap;
import java.util.Map;

public class R2AConverter {

  protected static Map<String, String> romanValueMap = null;
  protected static Map<String, String> arabicValueMap = null;

  protected final static String[][] ROMAN_ARABIC_ARRAY =
      {{"M", "1000"}, {"D", "500"}, {"C", "100"}, {"L", "50"}, {"X", "10"}, {"V", "5"}, {"I", "1"}};

  protected static Map<String, String> getRomanValueMap() {
    if (romanValueMap == null) {
      romanValueMap = new HashMap<String, String>();
      for (String[] array : ROMAN_ARABIC_ARRAY) {
        romanValueMap.put(array[0], array[1]);
      }
    }
    return romanValueMap;
  }

  protected static Map<String, String> getArabicValueMap() {
    if (arabicValueMap == null) {
      arabicValueMap = new HashMap<String, String>();
      for (String[] array : ROMAN_ARABIC_ARRAY) {
        arabicValueMap.put(array[1], array[0]);
      }
    }
    return arabicValueMap;
  }


  /***
   * roman String convert arabic number
   * 
   * @param roman
   * @return
   * @throws Exception : could not found roman character
   */
  public static int convertRomanToArabicNumber(String roman) throws Exception {
    int result = 0;
    char c = 0;
    char nc = 0;
    try {
      for (int i = 0; i < roman.length(); i++) {
        c = roman.charAt(i);
        int value = Integer.parseInt(getRomanValueMap().get(String.valueOf(c)));
        if (i + 1 < roman.length()) {
          nc = roman.charAt(i + 1);
          int nValue = Integer.parseInt(getRomanValueMap().get(String.valueOf(nc)));
          result = (value < nValue) ? result - value : result + value;
        } else {
          result += value;
        }
      }
    } catch (NumberFormatException e) {
      throw new NoSuchRomanException("Could not find that character :" + String.valueOf(c));
    }
    return result;
  }

  /***
   * arabic number convert roman number.
   * 
   * @param number
   * @return
   */
  public static String convertArabicNumberToRoman(int number) {
    StringBuilder builder = new StringBuilder();
    int target = number;
    for (int i = 0; i < ROMAN_ARABIC_ARRAY.length; i++) {
      int division = Integer.parseInt(ROMAN_ARABIC_ARRAY[i][1]);
      while(target / division != 0) {
        int digit = (int) (Math.pow(10, (int) Math.log10(target)));
        int prefix = (int) (target / digit);
        String character = ROMAN_ARABIC_ARRAY[i][0];
        String integer = Integer.toString(((prefix + 1) * digit));
        if (prefix % 5 == 4) {
          String second = getArabicValueMap().get(integer);
          String first = getArabicValueMap().get(Integer.toString(digit));
          if (first.equals(character)) {
            builder.append(i == 0 ? second : first + second);
            target = target - prefix * digit;
          } else {
            break;
          }
        } else {
          builder.append(character);
          target = target - division;
        }
      }
    }
    return builder.toString();
  }

}
