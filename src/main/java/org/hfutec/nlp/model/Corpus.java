package org.hfutec.nlp.model;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.hfutec.io.HFUTFileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 语料模型
 * Created by DuFei on 2017/5/22.
 */
public class Corpus {

  private boolean isInputADirectory;

  public String input_dir;
  public Charset charset;
  public String delimiter;
  public String saveDir;

  private String docIndexFile = "docIndex";
  private String wordIndexFile = "wordIndex";
  private String sparseVSMFile = "sparseVSM";

  public int docSize = 0;       //文档数量
  public int wordSize = 0;      //词汇数量（包含重复）
  public int vocabularySize = 0;      //单词数量（不重复）

  public HashSet<Integer> wordSet = Sets.newHashSet();      //单词集合

  public HashBiMap<String,Integer> docIndex = HashBiMap.create();     //文档索引
  public HashBiMap<String,Integer> wordIndex = HashBiMap.create();    //单词索引
  public HashMap<Integer,HashMap<Integer,Integer>> sparseVSMOfDocs = Maps.newHashMap();  //文档的稀疏空间表示
  public HashMap<Integer,HashSet<Integer>> wordDocs = Maps.newHashMap(); //单词对应的文档编号
  public HashMap<Integer,Integer> wordCount = Maps.newHashMap();      //单词计数
  public HashMap<Integer,HashSet<Integer>> docWords = Maps.newHashMap();  //文档中包含的单词

  public Corpus(){}

  /********
   * 输入是文件夹的构造方法
   */
  public Corpus(String input_dir ){
    this(input_dir, " ");
  }

  public Corpus(String input_dir, String delimiter ){
    this(input_dir, StandardCharsets.UTF_8, delimiter);
  }

  public Corpus(String input_dir, Charset charset, String delimiter){

    this.input_dir = input_dir;
    this.charset = charset;
    this.delimiter = delimiter;

    parseDocs();

  }

  /********
   * 输入是文件的构造方法，多了一个参数
   */
  public Corpus( String input_file, boolean isInputADirectory ){ this(input_file," ", isInputADirectory); }

  public Corpus( String input_file, String delimiter, boolean isInputADirectory ){

    this(input_file, StandardCharsets.UTF_8, delimiter, isInputADirectory);

  }

  public Corpus( String input_file, Charset charset, String delimiter, boolean isInputADirectory ){

    this.input_dir = input_file;
    this.charset = charset;
    this.delimiter = delimiter;
    this.isInputADirectory = isInputADirectory;
    parseDocsByFile();

  }

  private void parseDocsByFile(){

    BufferedReader reader = HFUTFileUtils.read(input_dir);
    String lineTxt;
    int lineNumber = 0;
    try {
      while( (lineTxt=reader.readLine()) != null ){

        int docID = lineNumber;
        parseDoc(docID, lineTxt, delimiter);

        lineNumber++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  private void parseDocs (){

    File[] fileList = new File(input_dir).listFiles();
    docSize = fileList.length;

    for( File file : fileList ){

      String content = null;
      try {
        //将文档中的换行符替换成单词的分隔符
        content = FileUtils.readFileToString(file,charset).replace("\n",delimiter).replace("\r",delimiter);
      } catch (IOException e) {
        e.printStackTrace();
      }

      int docID = docIndex.size();
      docIndex.put(file.getName(),docID);

      parseDoc(docID, content, delimiter);

      /*HashMap<Integer,Integer> wordCountOfDoc = Maps.newHashMap();
      HashSet<Integer> words = Sets.newHashSet();

      for( String word : content.split(delimiter) ){

        termSize++;

        //解析全局单词索引
        int wordID;
        if( wordIndex.containsKey(word) ){
          wordID = wordIndex.get(word);
          wordDocs.get(wordID).add(docID);
          wordCount.put(wordID, wordCount.get(wordID)+1);
        }else{
          wordID = wordIndex.size();
          wordIndex.put(word,wordID);
          wordDocs.put(wordID, Sets.newHashSet(docID));
          wordSet.add(wordID);
          wordCount.put(wordID, 1);
        }

        //解析全局单词-文档
        *//*if( wordDocs.containsKey(wordID) ){
          wordDocs.get(wordID).add(docID);
        }else{
          wordDocs.put(wordID,new HashSet<>(docID));
        }*//*

        //解析当前文档的VSM
        if( wordCountOfDoc.containsKey(wordID) ){
          wordCountOfDoc.put(wordID, wordCountOfDoc.get(wordID)+1);
        }else{
          wordCountOfDoc.put(wordID, 1);
          words.add(wordID);
        }

      }

      docWords.put(docID, words);
      sparseVSMOfDocs.put(docID, wordCountOfDoc);*/

    }

    wordSize = wordIndex.size();

  }

  private void parseDoc (int docID, String content, String delimiter){

    HashMap<Integer,Integer> wordCountOfDoc = Maps.newHashMap();
    HashSet<Integer> words = Sets.newHashSet();
    content = content.replaceAll(delimiter+"+",delimiter);

    for( String word : content.split(delimiter) ){

      if( !word.equals("") ) {
        vocabularySize++;

        //解析全局单词索引
        int wordID;
        if (wordIndex.containsKey(word)) {
          wordID = wordIndex.get(word);
          wordDocs.get(wordID).add(docID);
          wordCount.put(wordID, wordCount.get(wordID) + 1);
        } else {
          wordID = wordIndex.size();
          wordIndex.put(word, wordID);
          wordDocs.put(wordID, Sets.newHashSet(docID));
          wordSet.add(wordID);
          wordCount.put(wordID, 1);
        }

        //解析全局单词-文档
        /*if( wordDocs.containsKey(wordID) ){
          wordDocs.get(wordID).add(docID);
        }else{
          wordDocs.put(wordID,new HashSet<>(docID));
        }*/

        //解析当前文档的VSM
        if (wordCountOfDoc.containsKey(wordID)) {
          wordCountOfDoc.put(wordID, wordCountOfDoc.get(wordID) + 1);
        } else {
          wordCountOfDoc.put(wordID, 1);
          words.add(wordID);
        }
      }

    }

    docWords.put(docID, words);
    sparseVSMOfDocs.put(docID, wordCountOfDoc);

  }

  public void saveCorpus( String dir ){

    this.saveDir = dir;
    saveDocIndex();
    saveWordIndex();
    saveSparseVSMOfDocs();

  }

  public void loadCorpus( String saveDir ){

    this.saveDir = saveDir;
    loadDocIndex();
    loadWordIndex();
    loadSparseVSM();

  }

  private void saveDocIndex(){

    HFUTFileUtils.save2DMap(saveDir+"/"+docIndexFile, docIndex);

  }

  private void saveWordIndex(){

    HFUTFileUtils.save2DMap(saveDir+"/"+wordIndexFile, wordIndex);

  }

  private void saveSparseVSMOfDocs(){

    List<String> list = Lists.newArrayList();
    for( Map.Entry<Integer,HashMap<Integer,Integer>> docEntry : sparseVSMOfDocs.entrySet()){
//      String line = docEntry.getKey()+" ";
      String line = "";
      for(Map.Entry entry : docEntry.getValue().entrySet() ){
        line += entry.getKey()+":"+entry.getValue()+" ";
      }
      list.add(line);
    }
    try {
      FileUtils.writeLines(new File(saveDir+"/"+sparseVSMFile), list);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void loadDocIndex(){

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+docIndexFile);
    String lineTxt;
    try {
      while ( (lineTxt=reader.readLine()) != null ){

        String[] field = lineTxt.split("\t");
        docIndex.put(field[0],Integer.valueOf(field[1]));

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    docSize = docIndex.size();

  }

  private void loadWordIndex(){

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+wordIndexFile);
    String lineTxt;
    try {
      while ( (lineTxt=reader.readLine()) != null ){
        String[] field = lineTxt.split("\t");
        int count = Integer.valueOf(field[1]);
        wordIndex.put(field[0],count);
        vocabularySize += count;

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    wordSize = wordIndex.size();
    System.out.println("word size:"+wordSize);

  }

  //带有docID的载入方法
  /*private void loadSparseVSM(){

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+sparseVSMFile);
    String lineTxt;
    try {
      while ( (lineTxt=reader.readLine()) != null ){

        String[] field = lineTxt.split(" ");
        int docID = Integer.valueOf(field[0]);
        sparseVSMOfDocs.put(docID, new HashMap<>());
        for( int i=1; i<field.length; i++ ){
          String[] words = field[i].split(":");
          sparseVSMOfDocs.get(docID).put(Integer.valueOf(words[0]), Integer.valueOf(words[1]));
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }*/

  private void loadSparseVSM(){

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+sparseVSMFile);
    String lineTxt;
    try {
      int docID = 0;
      while ( (lineTxt=reader.readLine()) != null ){

        String[] field = lineTxt.split(" ");

        sparseVSMOfDocs.put(docID, new HashMap<>());
        for( int i=1; i<field.length; i++ ){
          String[] words = field[i].split(":");
          sparseVSMOfDocs.get(docID).put(Integer.valueOf(words[0]), Integer.valueOf(words[1]));
        }
        docID++;

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {

    Corpus corpus = new Corpus("f:/test", StandardCharsets.UTF_8, "\r\n");
    for( String doc : corpus.docIndex.keySet() ){
      System.out.println(doc+"\t"+corpus.docIndex.get(doc));
    }
    for( int i=0; i<corpus.docSize; i++ ){
      System.out.println(i+"\t"+corpus.docIndex.inverse().get(i));
    }

    for( String word : corpus.wordIndex.keySet() ){
      System.out.println(word+"\t"+corpus.wordIndex.get(word));
    }

    for( int i=0; i<corpus.wordSize; i++ ){
      System.out.println(i+"\t"+corpus.wordDocs.get(i));
    }

    for( int i=0; i<corpus.docSize; i++ ){
      System.out.println(i+"\t"+corpus.sparseVSMOfDocs.get(i));
    }

  }

}
