package org.hfutec.io;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataIOUtils {

  public static void main(String[] args) throws IOException {


    String inputFile = "test.txt";
    String someColumnDataPath = "some_columns.txt";
    String outputPath = "dummies";

    int[] cols = {4,5,11,13};
    readColumnData(inputFile, " ", cols, someColumnDataPath);
    getDummies(someColumnDataPath, outputPath, " ");

  }


  //convert categorical variable into dummy/indicator variables
  public static void getDummies( String inputFile, String outputPath, String delimiter ) throws IOException {

    //columnVariable saves variable->index for different columns
    //the key is the column index, the value is the variable index map
    Map<String,Integer> columnVariableIndex = Maps.newLinkedHashMap();


    //variable set saves all variables and their indexes, the key is the column index, the value is the variable index and their
    Map<Integer,Set<Integer>> variableSet = Maps.newHashMap();
    Map<Integer,Set<Integer>> newColumnSort = Maps.newLinkedHashMap();


    BufferedReader bufferedReader = HFUTFileUtils.read(inputFile);

    String lineTxt;
    int lineNumber = 0;
    assert bufferedReader != null;
    while ( (lineTxt=bufferedReader.readLine()) != null ){

      variableSet.put(lineNumber, Sets.newHashSet());
//      System.out.println(lineTxt);
      String lineArray[] = lineTxt.split(delimiter);
      for( int i=0; i<lineArray.length; i++ ){

        String variable = lineArray[i];
        String key = "col_"+i+"_"+variable;

        if( !columnVariableIndex.containsKey(key) ){
          columnVariableIndex.put(key, columnVariableIndex.size());

          if( newColumnSort.containsKey(i) ){
            newColumnSort.get(i).add(columnVariableIndex.size()-1);
          }else{
            newColumnSort.put(i, Sets.newHashSet(columnVariableIndex.size()-1));
          }
        }

        variableSet.get(lineNumber).add(columnVariableIndex.get(key));



      }
      lineNumber++;

    }

    int length = columnVariableIndex.size();
    List<String> outList = Lists.newArrayList();

    File outputFile = new File(outputPath);
    if( outputFile.exists() ){
      outputFile.delete();
    }

    StringBuilder firstLine = new StringBuilder();
    /*for( String col : columnVariableIndex.keySet() ){
      firstLine.append(col).append(" ");
    }
    outList.add(firstLine.toString());*/

    Map<Integer, String> columnInd = Maps.newLinkedHashMap();
    int columnIndex=0;
    for(String colName : columnVariableIndex.keySet()){
      columnInd.put(columnIndex, colName);
      columnIndex++;
    }

    for(Set<Integer> valueIndex : newColumnSort.values() ){
      for( int index : valueIndex ){
        firstLine.append(columnInd.get(index)).append(" ");
      }
    }
    outList.add(firstLine.toString());

    int count = 0;
    for( Set<Integer> variables : variableSet.values() ){

      count++;
      StringBuilder line = new StringBuilder(length*2+1);
//      String line = "";
      /*for(int i=0; i<length; i++ ){

        if (variables.contains(i)){
          line.append("1 ");
        }else {
          line.append("0 ");
        }

      }*/

      for(Set<Integer> valueIndex : newColumnSort.values() ){
        for( int index : valueIndex ){

          if( variables.contains(index) ){
            line.append("1 ");
          }else{
            line.append("0 ");
          }

        }
      }

      outList.add(line.toString().trim());

      if( outList.size()%10000 == 0){
        System.out.println(count);
      }

      if( outList.size() %40000 == 0 ){
        FileUtils.writeLines(outputFile, "utf-8", outList, true);
        outList.clear();
      }

    }

    FileUtils.writeLines(outputFile, "utf-8", outList, true);

  }

  private static void readColumnData( String inputPath, String delimiter, int[] cols, String outputPath )
          throws IOException {

    BufferedReader bufferedReader = HFUTFileUtils.read(inputPath);
    String lineTxt;
    List<String> outList = Lists.newArrayList();
    while ( (lineTxt=bufferedReader.readLine()) != null ){

      String[] array = lineTxt.split(delimiter);
      StringBuilder lineStr = new StringBuilder();
      for( int col : cols ){
        if( col<array.length )
          lineStr.append(array[col]).append(delimiter);
      }

      outList.add(lineStr.toString().substring(0, lineStr.lastIndexOf(delimiter)));

    }

    FileUtils.writeLines(new File(outputPath), outList);

  }

}
