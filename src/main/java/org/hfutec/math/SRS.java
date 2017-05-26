/* 
 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hfutec.math;

import java.util.Arrays;

/**
 * Simple Random Sampling class.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 */
public class SRS {
    
/**
 * Samples an observation based on a probability Table.
 *
 * @param probabilityTable  The probability table
 * @return  The id that was selected based on sampling
 */
  public static int weightedProbabilitySampling(double[] probabilityTable) {
    int sampledId=0;
    double randomNumber = Math.random();

    //This value is used to determine the outcome of the random assignment.
    //This is the analogous of having a line of length 1, separate the line
    //sublines of length equal to their probability, selecting a random
    //number which is the position on this line and then start summing up
    //the positions until you hit the point where subline where the random
    //number lies.
    double probabilitySumSelector = 0.0;

    //loop through all the table and find their weight then see if the
    //random number lies.
    for(int i=0;i<probabilityTable.length;++i) {
      probabilitySumSelector+=probabilityTable[i];
      if(randomNumber<=probabilitySumSelector) {
        sampledId=i;
        break;
      }
    }

    return sampledId;
  }

  public static double[] normalizeProbabilities( double[] probabilities){

    double max = -Double.MAX_VALUE;
    for( int i=0; i<probabilities.length; i++){
      if( probabilities[i] > max ){
        max = probabilities[i];
      }
    }

//    System.out.println(max);

    double sum = 0.0;
    for( int i=0; i<probabilities.length; i++){
      probabilities[i] = Math.exp(probabilities[i]-max);

      sum += probabilities[i];

    }

    System.out.println("sum:"+sum);

    if( sum !=0 ) {
      for (int i = 0; i < probabilities.length; i++) {
        probabilities[i] /= sum;
      }
    }

    return probabilities;

  }

  public static void main(String[] args) {

    double[] test = new double[]{-17871.410238802786, -21377.755573660485, -28649.792942138964};
    System.out.println(Math.exp(18563.2984872072));
    normalizeProbabilities(test);
    System.out.println(Arrays.toString(test));

  }
}
