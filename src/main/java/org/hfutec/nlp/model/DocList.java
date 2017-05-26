package org.hfutec.nlp.model;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealVector;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by DuFei on 2017/3/11.
 */
public class DocList extends Document {

  private ImmutableBiMap<String,Integer> docIndex;
  private OpenMapRealMatrix docWords = new OpenMapRealMatrix(1,1);
  private int docSize = 0;

  public DocList(String directory_path, Charset charset, String word_delimiter){

    super(charset,word_delimiter);

    Map<String,Integer> docIndex_current = Maps.newHashMap();
    Map<Integer,OpenMapRealVector> docCount = Maps.newHashMap();

    for( File file : new File(directory_path).listFiles() ){

      docIndex_current.put(file.getName(),docSize);
//      docCount.put(docSize,addDoc(file));

      docSize++;

    }

    docIndex = ImmutableBiMap.copyOf(docIndex_current);

  }

  public String getDocNameByIndex ( int index ){
    return docIndex.inverse().get(index);
  }

  public int getIndexOfDoc ( String docName ){
    return docIndex.get(docName);
  }


  public int getDocSize(){
    return docSize;
  }

  public static void main(String[] args) {

    long start = System.currentTimeMillis();

    String inPath = "f:/dpmm/weibo_content_seg_by_jieba_filter_by_stopwords";

    DocList docList = new DocList(inPath, StandardCharsets.UTF_8, " ");

    System.out.println(System.currentTimeMillis() - start +" ms");

    /*for( int i=0; i<docList.docWords.length; i++ ){
      int[] c = docList.docWords[i];
      System.out.print("\t"+i+"---");
      for( int j=0; j<c.length; j++){
        System.out.print(c[j]+" ");
      }
      System.out.println();

    }
    System.out.print("\to---");
    for( int i=0;i<docList.bag_of_word.length;i++){

      System.out.print(docList.bag_of_word[i]+" ");

    }

    System.out.println();
    System.out.println(docList.getWordIndex());
    System.out.println(docList.getWordCount());*/

  }



}
