package org.hfutec.nlp.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author DuFei
 * Created by DuFei on 2017/3/12.
 */
public class Corpus_old extends Document{

  private BiMap<String,Integer> docIndex = HashBiMap.create();
  private int[][] documents;
  private int sizeOfDocuments = 0;

  private String directory;

  public Corpus_old(String directory, Charset charset, String word_delimiter ){

    super(charset,word_delimiter);

    this.directory = directory;

    parseDocs();
//    System.out.println("begin to transform:\t"+System.currentTimeMillis());

  }

  /********
   * transform all files into VSM
   * 将当前目录下的所有文件转换成VSM的表示方式**********/
  private void parseDocs (){

    File[] fileList = new File(directory).listFiles();

    if( fileList != null ){

      documents = new int[fileList.length][];
      for( File file : fileList ){

        docIndex.put(file.getName(),sizeOfDocuments);
        addDoc(sizeOfDocuments, formatContentByFile(file));

        sizeOfDocuments++;

        if( sizeOfDocuments % 50 ==0 )
          System.out.println("current doc size:\t"+sizeOfDocuments);

      }
    }

  }

  /********
   * parse a doc and return the Bag-of-Word of the document
   * 将当前目录下的所有文件转换成VSM的表示方式**********/
  private void addDoc( int docIndex, String content ){

    String[] wordsArray = content.split(word_delimiter);
    documents[docIndex] = new int[wordsArray.length];

    int count = 0,currentWordIndex;
    for( String word : wordsArray ){

      totalNumberOfWords++;
      word = word.trim();
      if( !wordIndex.containsKey(word) ){

        wordIndex.put(word,sizeOfVocabulary);
        currentWordIndex = sizeOfVocabulary;
        sizeOfVocabulary++;

      } else {

        currentWordIndex = wordIndex.get(word);

      }

      documents[docIndex][count] = currentWordIndex;
      count++;

    }

//    System.out.println(content);

  }

  public String getDocNameByIndex ( int index ){ return docIndex.inverse().get(index); }

  public int getSizeOfDocuments (){ return sizeOfDocuments; }

  public Map<String,Integer> getDocIndex(){ return docIndex; }

  public int getTotalNumberOfWords(){ return totalNumberOfWords; }

  public int[][] getDocuments(){ return documents; }

  public static void main(String[] args) {

    //准确性测试
    /*String inputFile = "F:\\test";
    Corpus_old corpus = new Corpus_old(inputFile, StandardCharsets.UTF_8," ");

    for( String word : corpus.getWordIndex().keySet() ){
      System.out.println(word+"\t"+corpus.getIndexOfWord(word));
    }

    System.out.println("sizeOfDocuments:"+corpus.getSizeOfDocuments());
    System.out.println("totalNumberOfWords:"+corpus.getTotalNumberOfWords());
    System.out.println("sizeOfVocabulary:"+corpus.getSizeOfVocabulary());

    System.out.println(corpus.getDocIndex());


    int[][] documents = corpus.getDocuments();

    for( int docIndex =0 ; docIndex<documents.length; docIndex++ ){

      String outStr = "doc:"+corpus.getDocNameByIndex(docIndex)+"\t"+docIndex+"\r\n";
      for( int wordIndex = 0; wordIndex<documents[docIndex].length; wordIndex++ ){
        int word = documents[docIndex][wordIndex];
        outStr += wordIndex+":"+word+" "+corpus.getWordByIndex(word)+"  ";

      }
      outStr += "\r\n";

      System.out.println(outStr);
    }*/

    //速度测试
    long start = System.currentTimeMillis();

    String directory = "f:/dpmm/weibo_content_seg_by_jieba_filter_by_stopwords";

    Corpus_old corpus = new Corpus_old(directory, StandardCharsets.UTF_8," ");

    long middle = System.currentTimeMillis();
    System.out.println(corpus.sizeOfDocuments);
    System.out.println(corpus.totalNumberOfWords);
    System.out.println(corpus.sizeOfVocabulary);

    System.out.println( (middle-start) +" ms");


  }

}
