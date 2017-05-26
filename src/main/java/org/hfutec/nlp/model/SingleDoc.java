package org.hfutec.nlp.model;

import org.apache.commons.math3.linear.OpenMapRealVector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by DuFei on 2017/3/11.
 */
public class SingleDoc extends Document {

  public SingleDoc (String file_path, Charset charset, String word_delimiter ){

    super(charset,word_delimiter);

    String content = formatContentByFilePath(file_path);

    parseDoc(content);

  }

  public static void main(String[] args) throws IOException {

    long start = System.currentTimeMillis();

    String testFile = "d:/test.txt";
    String testPath = "f:/dpmm/weibo_content_seg_by_jieba_filter_by_stopwords";

    SingleDoc doc = new SingleDoc(testFile,StandardCharsets.UTF_8," ");

    System.out.println(doc.getWordIndex());
//    System.out.println(doc.getWordCount());
    System.out.println(doc.getWordSet());

    for( int i=0; i<doc.bag_of_word.getDimension(); i++ ){
      System.out.print(doc.bag_of_word.getEntry(i)+" ");
    }
    System.out.println();
    System.out.println("-------------------");

    OpenMapRealVector bag_of_words = doc.parseDocByFilePath("d:/test1.txt");

    System.out.println(doc.getWordIndex());
//    System.out.println(doc.getWordCount());
    System.out.println(doc.getWordSet());
    System.out.println(doc.bag_of_word);

    for( int i=0; i<bag_of_words.getDimension(); i++ ){
      System.out.print(bag_of_words.getEntry(i)+" ");
    }
    System.out.println();


    /*for( File file : new File(testPath).listFiles()){

      SingleDoc doc = new SingleDoc(file.getAbsolutePath(),StandardCharsets.UTF_8," ");

    }*/

    System.out.println( (System.currentTimeMillis()-start) +" ms");




  }

}

