package org.hfutec.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 排序
 * Created by DuFei on 2017/5/22.
 */
public class Sort {

  /**********
   * sort map by key
   * if reversed=0 sort increasingly otherwise sort by decreasingly*************/
  public static Map<Integer, String> sortMapByKey ( Map<Integer,String> unSortedMap, int reversed){

    Map<Integer,String> result = new LinkedHashMap<>();

    if( reversed == 0 ){
      unSortedMap.entrySet().stream()
            .sorted(Map.Entry.<Integer,String>comparingByKey().reversed())
            .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
    }else{
      unSortedMap.entrySet().stream()
              .sorted(Map.Entry.<Integer,String>comparingByKey())
              .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
    }

    return result;

  }

  /**********
   * sort map by value
   * if reversed=0 sort increasingly otherwise sort by decreasingly*************/
  public static Map<String,Integer> sortMapByValue ( Map<String,Integer> unSortedMap, int reversed){

    Map<String,Integer> result = new LinkedHashMap<>();

    if( reversed == 0 ){
      unSortedMap.entrySet().stream()
              .sorted(Map.Entry.<String,Integer> comparingByValue().reversed())
              .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
    }else{
      unSortedMap.entrySet().stream()
              .sorted(Map.Entry.<String,Integer> comparingByValue())
              .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
    }

    return result;

  }

}
