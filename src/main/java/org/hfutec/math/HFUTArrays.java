package org.hfutec.math;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * @author DuFei
 * Created by DuFei on 2017/3/17.
 */
public class HFUTArrays {

  public static int[][] subtract(int[][] arr1, int[][] arr2, int newRowLength, int newColumnLength) {

//    int[][] arr1_new = Arrays.copyOf(arr1, newArrayLength);
    int[][] arr2_new = new int[newRowLength][newColumnLength];

    int[][] newArray = new int[newRowLength][newColumnLength];

    System.arraycopy(arr2,0, arr2_new,0, newRowLength<arr2.length?newRowLength:arr2.length);
    for( int i=0; i<newRowLength; i++ ){

      newArray[i] = subtract(arr1[i], arr2_new[i], newColumnLength);

    }

    return newArray;
  }

  public static int[] subtract(int[] arr1, int[] arr2, int newArrayLength) {

    int[] arr1_new = Arrays.copyOf(arr1, newArrayLength);

    int[] arr2_new = new int[newArrayLength];
    System.arraycopy(arr2,0, arr2_new,0, arr2.length<newArrayLength?arr2.length:newArrayLength);


    int[] newArray = new int[newArrayLength];
    for( int i=0; i<newArrayLength; i++ ){

      newArray[i] = arr1_new[i] - arr2_new[i];

    }

    return newArray;
  }

  public static int[] add(int[] arr1, int[] arr2, int newArrayLength) {

    int[] arr1_new = Arrays.copyOf(arr1, newArrayLength);

    int[] arr2_new = new int[newArrayLength];
    System.arraycopy(arr2,0, arr2_new,0, arr2.length<newArrayLength?arr2.length:newArrayLength);

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

  public static double[] multiply ( double[] array, double number ){

    double[] result = new double[array.length];
    for( int i=0; i<array.length; i++ ){
      result[i] = array[i] * number;
    }

    return result;

  }

  public static void main(String[] args) {

    int[][] a = {{1,2,3,4},{3,4,1,1},{3,4,1,1},{3,4,1,1}};
    int[][] b = {{2,2},{2,3},{2,3},{2,3},{2,3}};

    int[] c = {1,2,3};
    int[] d = {2,3,5,4};


    System.out.println(ArrayUtils.toString(subtract(a, b,a.length,a[0].length)));
    System.out.println(ArrayUtils.toString(subtract(c,d,c.length)));
    System.out.println(ArrayUtils.toString(add(c,d,c.length)));

  }

}

