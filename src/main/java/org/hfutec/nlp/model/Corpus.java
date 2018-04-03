package org.hfutec.nlp.model;

import com.google.common.collect.*;
import org.apache.commons.io.FileUtils;
import org.hfutec.io.HFUTFileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 语料模型，必须是已经分词好的文档
 * Created by DuFei on 2017/5/22.
 */
public class Corpus {

  public String inputPath;
  public File inputFile;
  public String charset = "UTF-8";
  public String delimiter = " ";
  public String saveDir;

  private String docIndexFile = "docIndex";
  private String wordIndexFile = "wordIndex";
  private String sparseVSMFile = "sparseVSM";

  public int docSize = 0;       //文档数量
  public int wordSize = 0;      //词汇数量（包含重复）
  public int vocabularySize = 0;      //单词数量（不重复）

  public BiMap<String,Integer> docIndex = HashBiMap.create();     //文档索引
  public BiMap<String,Integer> wordIndex = HashBiMap.create();    //单词索引
  public Map<Integer,Map<Integer,Integer>> sparseVSMOfDocs = Maps.newHashMap();  //文档的稀疏空间表示
  public Map<Integer,HashSet<Integer>> wordDocs = Maps.newHashMap(); //单词对应的文档编号
  public Map<Integer,Integer> wordCount = Maps.newHashMap();      //单词计数，即每个单词对应的数量，单词用索引表示
  public Map<Integer,HashSet<Integer>> docWords = Maps.newHashMap();  //文档中包含的单词
  public int[] documentSizes;

  public Corpus(){}

  /********
   * 输入是文件夹的构造方法
   */
  public Corpus( String inputPath ){

    this.inputPath = inputPath;
    this.inputFile = new File(inputPath);

    init();

  }

  public Corpus( String inputPath, String charset ){

    this.inputPath = inputPath;
    this.inputFile = new File(inputPath);
    this.charset = charset;

    init();

  }

  public Corpus( String inputPath, String delimiter, String charset ){

    this.inputPath = inputPath;
    this.inputFile = new File(inputPath);
    this.delimiter = delimiter;
    this.charset = charset;

    init();

  }

  private void init (){

    if( inputFile.exists() ){

      System.out.println("Initialization successful!");
      System.out.println("Input path:"+inputPath);

      if( inputFile.isDirectory() ){

        parseDocs();

      }else{

        parseDocsByFile();

      }
    }else{
      System.out.println("Initialization failed! The input file or directory does not exist!");
      System.out.println("Input path:"+inputPath);
    }


  }

  private void parseDocsByFile(){

    BufferedReader reader = HFUTFileUtils.read(inputPath,charset);

    try {
      System.out.println(HFUTFileUtils.getLineNumber(inputPath));
      documentSizes = new int[HFUTFileUtils.getLineNumber(inputPath)];
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    docSize = lineNumber;


  }

  private void parseDocs (){

    File[] fileList = new File(inputPath).listFiles();
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

    int wordNumberOfEachDoc = 0;

    for( String word : content.split(delimiter) ){

      if( !word.equals("") ) {
        wordNumberOfEachDoc++;
        wordSize++;
        //解析全局单词索引
        int wordID;
        if (wordIndex.containsKey(word)) {
          wordID = wordIndex.get(word);
          wordDocs.get(wordID).add(docID);
          wordCount.put(wordID, wordCount.get(wordID) + 1);
        } else {
          vocabularySize++;
          wordID = wordIndex.size();
          wordIndex.put(word, wordID);
          wordDocs.put(wordID, Sets.newHashSet(docID));
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

    documentSizes[docID] = wordNumberOfEachDoc;
    docWords.put(docID, words);
    sparseVSMOfDocs.put(docID, wordCountOfDoc);

  }

  public void saveCorpus( String dir ){

    this.saveDir = dir;
    saveDocIndex();
    saveWordIndex();
    saveSparseVSMOfDocs();

  }

  private void saveDocIndex(){

    HFUTFileUtils.save2DMap(saveDir+"/"+docIndexFile, docIndex);

  }

  private void saveWordIndex(){

    HFUTFileUtils.save2DMap(saveDir+"/"+wordIndexFile, wordIndex);

  }

  private void saveSparseVSMOfDocs(){

    List<String> list = Lists.newArrayList();
    for( Map.Entry<Integer,Map<Integer,Integer>> docEntry : sparseVSMOfDocs.entrySet()){
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

  public void loadCorpus( String saveDir ){

    this.saveDir = saveDir;
    loadDocIndex();
    loadWordIndex();
    loadSparseVSM();

  }

  private void loadDocIndex(){

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+docIndexFile,"utf-8");
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

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+wordIndexFile,"utf-8");
    String lineTxt;
    try {
      while ( (lineTxt=reader.readLine()) != null ){
        String[] field = lineTxt.split("\t");
        int count = Integer.valueOf(field[1]);
        wordIndex.put(field[0],count);
        wordSize += count;

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    vocabularySize = wordIndex.size();
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

    BufferedReader reader = HFUTFileUtils.read(saveDir+"/"+sparseVSMFile,"utf-8");

    String lineTxt;
    try {
      int docID = 0;
      while ( (lineTxt=reader.readLine()) != null ){

        String[] field = lineTxt.split(" ");
        docWords.put(docID, Sets.newHashSet());
        sparseVSMOfDocs.put(docID, new HashMap<>());
        for( int i=1; i<field.length; i++ ){
          String[] words = field[i].split(":");

          int wordIndex = Integer.valueOf(words[0]);
          int wordCount = Integer.valueOf(words[1]);

          sparseVSMOfDocs.get(docID).put(wordIndex, wordCount);

          if( wordDocs.containsKey(wordIndex) ){
            wordDocs.get(wordIndex).add(docID);
          }else{
            wordDocs.put(wordIndex, Sets.newHashSet(docID));
          }

          docWords.get(docID).add(wordIndex);

        }
        docID++;

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {

    /*Corpus corpus = new Corpus("f:/test", StandardCharsets.UTF_8, "\r\n");
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
    }*/

//    String corpus_file = "F:\\experiment_data\\reuters_data\\reuters_with_nltk_filtered_no_title";
//    String word_vect_file = "F:\\experiment_data\\reuters_data\\reuters_vector_50.txt";
//    String output = "F:\\experiment_data\\reuters_data\\reuters_vector_50_id.txt";
    String corpus_file = "d:/dhdp/input.txt";

    Corpus corpus = new Corpus(corpus_file,"gbk");

    System.out.println(corpus.wordSize+"\tvocabulary size:"+corpus.vocabularySize);

  }

}
