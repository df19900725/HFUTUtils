package org.hfutec.test;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.hfutec.io.HFUTFileUtils;
import org.hfutec.preprocess.WordFiltering;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AbstractExtract {

  public static void main(String[] args) throws IOException {

    /*String str = "ni book will apprentice true sql master ï writing queries sh .net fd ï¾ as$$dfas$$d $dfaf--";
    System.out.println(str.replaceAll("\\$\\$[^$]+\\$\\$","").replaceAll("[^A-Za-z0-9_ ]"," "));
*/
    String data_file = "E:\\citeulike\\outputacm.txt";
    String stopWordsFile = "E:\\citeulike\\stopwords.txt";

    LinkedList<String> abstractList = Lists.newLinkedList();

    List<String> stopWords = FileUtils.readLines(new File(stopWordsFile));

    BufferedReader reader = HFUTFileUtils.read(new File(data_file));
    reader.readLine();
    String lineTxt = "";
    int i=0;
    while( (lineTxt=reader.readLine()) != null ){

      i++;
      if( lineTxt.startsWith("#!") ){
        String str = WordFiltering.removeSentenceStopWords(lineTxt, " ", stopWords).replace("#!","");

        str = Test.replaceTwoTumple(str);
        str = Test.replaceS(str);
        //去掉's
        str = str.replaceAll("'","-");

        //去除非英文字符的结果
        str = str.replaceAll("[^A-Za-z0-9_ ]"," ").replaceAll(" +"," ");


        str = str.replaceAll("(http://|ftp://|https://|www){0,1}[^\\u4e00-\\u9fa5\\\\s]*?\\\\.(com|net|cn|me|tw|fr)[^\\u4e00-\\u9fa5\\\\s]*","").replace("-"," ");
        abstractList.add(str.replaceAll("\\pP" , "").toLowerCase());
      }


    }

    List<String> newAbstractList = WordFiltering.removeSentenceListStopWords(abstractList, " ", stopWords);

//    List<String> newAbstractList = FileUtils.readLines(new File("e:/citeulike/abstract"),"utf-8");

    List<String> newAbstractList2 = Lists.newArrayList();
    i=0;
    for( String line : newAbstractList ){

      i++;
      if(i%200==0){
        System.out.println(i);
      }
//      System.out.println(line);
      String str = "";
      for( String word : line.split(" ") ){

        if( !word.matches(".*\\d+.*") ){
          str+= word+" ";
        }

      }

      if( !str.equals("") )
        newAbstractList2.add(str.toString().trim());


    }

    FileUtils.writeLines(new File("e:/citeulike/trainingset_without_sw"), newAbstractList2);


  }

}
