package com.seongjunkang.r2a.converter;

import java.util.HashMap;
import java.util.Map;

public class R2AConverter {

  protected static Map<String, String> romanValueMap = null;
  protected static Map<String, String> arabicValueMap = null;

  protected final static String[][] ROMAN_ARABIC_ARRAY =
      {{"M", "1000"}, {"D", "500"}, {"C", "100"}, {"L", "50"}, {"X", "10"}, {"V", "5"}, {"I", "1"}};
  protected final static String[][] exceptCase = 
    {{"XC","LXXXX"},{"XL","XXXX"},{"IX","VIIII"},{"IV","IIII"}};

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
   * @param roman
   * @return
   * @throws Exception : could not found roman character
   */
  public static int convertRomanToArabicNumber(String roman) throws Exception {
    int result = 0;
    char c = 0;
    try {
      for (String[] eCase : exceptCase) {
        roman = roman.replace(eCase[0], eCase[1]);
      }
      
      for (int i = 0; i < roman.length(); i++) {
        c = roman.charAt(i);
        int value = Integer.parseInt(getRomanValueMap().get(String.valueOf(c)));
        result += value;
      }
    } catch (NumberFormatException e) {
      throw new NoSuchRomanException("Could not find that character :" + String.valueOf(c));
    }
    return result;
  }

  /***
   * arabic number convert roman number.
   * cannot convert upper line (\u0305) yet.
   * @param number
   * @return
   */
  public static String convertArabicNumberToRoman(int number) {
    int count[] = new int[ROMAN_ARABIC_ARRAY.length];
    int target = number;
    for (int i = 0 ; i < ROMAN_ARABIC_ARRAY.length ; i++) {
      int division = Integer.parseInt(ROMAN_ARABIC_ARRAY[i][1]); 
      count[i] = (int)Math.floor(target/division);
      target = target % division;
    }
    StringBuilder builder = new StringBuilder();
    for(int j = 0 ; j < count.length ; j++) {
      int value = count[j];
      for (int k = 0 ; k < value; k++) {
        builder.append(ROMAN_ARABIC_ARRAY[j][0]);
      }
    }
    String result = builder.toString();
    for (String[] eCase : exceptCase) {
      result = result.replace(eCase[1], eCase[0]);
    }
    return result;
  }

}
