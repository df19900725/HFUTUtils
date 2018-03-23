package org.hfutec.math;

import java.util.stream.DoubleStream;

/**
 * 统计中的相关数值的计算
 * Created by DuFei on 2017/8/20.
 */

public class Statistic {

  public static double getMean ( double[] value ){

    double mean = 0;

    DoubleStream.of(value).average();

    return mean;
  }

  public static void main(String[] args) {




  }

}
