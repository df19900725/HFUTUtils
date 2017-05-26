package org.hfutec.math;

import java.util.Arrays;

/**
 * @author DuFei
 * Created by DuFei on 2017/3/17.
 */
public class HFUTArrays {

  public static int[] subtract(int[] arr1, int[] arr2, int newArrayLength) {

    int[] arr1_new = Arrays.copyOf(arr1, newArrayLength);
    int[] arr2_new = Arrays.copyOf(arr2, newArrayLength);

    int[] newArray = new int[newArrayLength];
    for( int i=0; i<newArrayLength; i++ ){

      newArray[i] = arr1_new[i] - arr2_new[i];

    }

    return newArray;
  }

  public static int[] add(int[] arr1, int[] arr2, int newArrayLength) {

    int[] arr1_new = Arrays.copyOf(arr1, newArrayLength);
    int[] arr2_new = Arrays.copyOf(arr2, newArrayLength);

    int[] newArray = new int[newArrayLength];
    for( int i=0; i<newArrayLength; i++ ){

      newArray[i] = arr1_new[i] + arr2_new[i];

    }

    return newArray;
  }

  public static int[][] add(int[][] a, int[][] b, int newArrayRowLength){

    int[][] newArray = Arrays.copyOf(a,newArrayRowLength);

    for( int i=0; i<newArray.length; i++){
//      System.out.println("w");
      if( newArray[i] != null ){
        newArray[i] = add(newArray[i],b[i],b[i].length);
      }else{
        newArray[i] = new int[b[0].length];
      }

    }

    return newArray;
  }

  public static void main(String[] args) {

  }

}
