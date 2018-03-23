package org.hfutec.preprocess;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.hfutec.io.HFUTFileUtils;
import org.hfutec.preprocess.wordseg.NLPIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单词过滤，包括去停用词等操作
 *
 * 所有的停用词必须用Set，这样的速度会快
 * 对于待处理的列表，也必须使用LinkedList，这样速度也快
 *
 * Created by DuFei on 2017/5/11.
 */
public class WordFiltering {

  static String delimiter = " ";
  static String filter_pos = "/w|/x|/r|/c|/u|/e|/y|/o|/x|/m|/q|/d|/p|/vshi|/vyou|/eng|/vshi|/pba|/pbei";

  /*******
   * @content 去除停用词
   * @param sentence 经过分词后的句子
   * @param delimiter 词语的分隔符
   * @param stop_words_file 停用词文件位置
   * @return 去除停用词后的句子 *********/
  public static String removeSentenceStopWords ( String sentence, String delimiter, String stop_words_file ){

    List<String> words = new ArrayList<>(Arrays.asList(sentence.split(delimiter)));
    try {

      LinkedList<String> stopWordsList = new LinkedList<>(
              FileUtils.readLines(new File(stop_words_file), StandardCharsets.UTF_8));
      words.removeAll(stopWordsList);
      return removeSentenceStopWords(sentence, delimiter, stopWordsList);

    } catch (IOException e) {

      e.printStackTrace();
      return sentence;

    }

  }

  /*******
   * @content 去除停用词
   * @param sentence 经过分词后的句子
   * @param delimiter 词语的分隔符
   * @param stopWordsList 停用词列表
   * @return 去除停用词后的句子 *********/
  public static String removeSentenceStopWords ( String sentence, String delimiter,
                                                 List<String> stopWordsList ){

    LinkedList<String> words = new LinkedList<>(Arrays.asList(
            sentence.replaceAll(" +",delimiter).split(delimiter)));
    words.removeAll(stopWordsList);

    String outStr = "";
    for( String word : words ){
      if( !word.equals(""))
        outStr += word+delimiter;
    }

    if( outStr.length()>0){
      outStr = outStr.substring(0,outStr.length()-delimiter.length()).trim();
    }

    return outStr;

  }

  /*******
   * @content 去除停用词
   * @param sentences 经过分词后的句子列表
   * @param delimiter 词语的分隔符
   * @param stopWordsList 停用词列表
   * @return 去除停用词后的句子 *********/
  public static List<String> removeSentenceListStopWords (LinkedList<String> sentences,
                                                          String delimiter, List<String> stopWordsList ){
    int count = 0;
    int sum = sentences.size();
    List<String> outList = Lists.newArrayList();
    for( String sentence : sentences ){
      System.out.println("current index:"+count+"\tall size:"+sum);
      outList.add(removeSentenceStopWords(sentence, delimiter, stopWordsList ));
      count++;
    }

    return outList;

  }

  /*******
   * @content 去除停用词
   * @param sentences 经过分词后的句子列表
   * @param delimiter 词语的分隔符
   * @param stop_words_file 停用词文件位置
   * @return 去除停用词后的句子 *********/
  public static List<String> removeSentenceListStopWords ( LinkedList<String> sentences,
                                                           String delimiter, String stop_words_file ){

    try {

      LinkedList<String> stopWordsList = new LinkedList<>(
              FileUtils.readLines(new File(stop_words_file), StandardCharsets.UTF_8));

      return removeSentenceListStopWords(sentences, delimiter, stopWordsList);

    } catch (IOException e) {

      e.printStackTrace();
      return sentences;

    }

  }

  /*******
   * @content 去除停用词
   * @param inputFile 经过分词后的输入文件位置
   * @param delimiter 词语的分隔符
   * @param stopWordsFile 停用词文件位置
   * @return 去除停用词后的句子 *********/
  public static List<String> removeStopWordsByFile ( String inputFile, String encoding, String delimiter, String stopWordsFile ){

    List<String> stopWordsList = Lists.newArrayList();
    try {
      stopWordsList = FileUtils.readLines(new File(stopWordsFile), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return removeStopWordsByFile(inputFile,encoding,delimiter,stopWordsList);

  }

  /*******
   * @content 去除停用词
   * @param inputFile 经过分词后的输入文件位置
   * @param delimiter 词语的分隔符
   * @param stopWordsList 停用词列表
   * @return 去除停用词后的句子 *********/
  public static List<String> removeStopWordsByFile ( String inputFile,String encoding, String delimiter, List<String> stopWordsList ){

    LinkedList<String> outList = Lists.newLinkedList();
    BufferedReader reader = HFUTFileUtils.read(inputFile,encoding);
    String lineTxt = "";
    try {
      while( (lineTxt = reader.readLine()) != null ){

        outList.add(removeSentenceStopWords(lineTxt, delimiter, stopWordsList));

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return outList;

  }

  /*******
   * @content 去除停用词
   * @param inputDir 经过分词后的输入文件夹路径
   * @param outputDir 输出文件夹
   * @param delimiter 词语的分隔符
   * @param stopWordsList 停用词列表
   * @return 去除停用词后的句子 *********/
  public static void removeStopWordsByDirectory ( String inputDir, String outputDir, String encoding, String delimiter,
                                                  List<String> stopWordsList ){

    if( !outputDir.endsWith(File.pathSeparator)){
      outputDir += File.pathSeparator;
    }
    for( File file : new File(inputDir).listFiles() ){

      List<String> outList = removeStopWordsByFile(file.getAbsolutePath(),encoding,delimiter,stopWordsList);
      try {
        FileUtils.writeLines(new File(outputDir+file.getName()), outList );
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

  /*******
   * @content 去除停用词
   * @param inputDir 经过分词后的输入文件夹路径
   * @param outputDir 输出文件夹
   * @param delimiter 词语的分隔符
   * @param stopWordsFile 停用词文件
   * @return 去除停用词后的句子 *********/
  public static void removeStopWordsByDirectory ( String inputDir, String outputDir, String encoding, String delimiter,
                                                  String stopWordsFile ){

    List<String> stopWordsList = Lists.newArrayList();
    try {
      stopWordsList = FileUtils.readLines(new File(stopWordsFile), encoding);
    } catch (IOException e) {
      e.printStackTrace();
    }

    removeStopWordsByDirectory(inputDir,outputDir,encoding, delimiter,stopWordsList);

  }

  /********
   * 按标签过滤单词，输入为句子列表，指定的filter_pos为空的时候，采用默认过滤规则*****/
  public static String filterSentenceByPOS ( String sentence ){

    return filterSentenceByPOS(sentence, filter_pos, delimiter);

  }

  /********
   * 按标签过滤单词，输入为句子列表，指定的filter_pos为空的时候，采用默认过滤规则*****/
  public static String filterSentenceByPOS ( String sentence, String filter_pos ){

    return filterSentenceByPOS(sentence, filter_pos, delimiter);

  }

  /********
   * 按标签过滤单词，输入为句子列表*****/
  public static List<String> filterSentenceListByPOS ( List<String> sentences, String filter_pos, String delimiter ){

    List<String> sentenceList = Lists.newArrayList();

    for( String sentence : sentences )
      sentenceList.add(filterSentenceByPOS(sentence, filter_pos, delimiter));

    return sentenceList;

  }

  /********
   * 按标签过滤单词，输入为句子*******/
  public static String filterSentenceByPOS ( String sentence, String filter_pos, String delimiter ){

    String sentence_filtered = "";

    for( String word : sentence.split(delimiter) ){
      word = filterWordsByPOS( word, filter_pos );
      if( word != null ){
        sentence_filtered += word + delimiter;
      }
    }

    if( !sentence_filtered.equals("") )
      sentence_filtered = sentence_filtered.substring(0, sentence_filtered.length()-delimiter.length())
              .replaceAll(delimiter+"+",delimiter).trim();

    return sentence_filtered;

  }

  /********
   * 按照POS规则规律NLPIR分词结果
   * x 标点符号
   * r 代词
   * c 并列连词
   * u 助词
   * y 语气词
   * m 数词
   * q 量词
   * d 副词
   * p 介词
   * 是 有
   * *********/
  public static String filterWordsByPOS( String word, String filter_POS ){

    if( !filter_POS.equals("") )
      filter_pos = filter_POS;

    Pattern r = Pattern.compile(filter_pos);
    Matcher m = r.matcher(delimiter+word);

    if( word == null || word.trim().equals("") || m.find()){

      return null;

    }else{
      try{
        return word.split("/")[0];
      }catch (Exception e){
        System.out.println("the word "+word+" is something wrong");
        return  null;
      }

    }

  }

  /**********
   * @content 去除分词结果的词性标签
   * *************/
  public static String removePOSTag ( String sentence, String delimiter ){

    String sentence_filtered = "";
    for( String word : sentence.split(delimiter) )
      sentence_filtered += word.split("/")[0]+delimiter;

    if( !sentence_filtered.equals("") )
      sentence_filtered = sentence_filtered.substring(0, sentence_filtered.length()-delimiter.length());

    return sentence_filtered;

  }

  /**********
   * @content 去除分词结果的词性标签
   * *************/
  public static List<String> removePOSTag ( List<String> sentences, String delimiter ){

    List<String> sentenceList = Lists.newArrayList();
    int count = 0;
    for( String sentence : sentences ){
      System.out.println("count:"+count+"\tall size:"+sentences.size());
      sentenceList.add(removePOSTag(sentence,delimiter));
      count++;
    }

    return sentenceList;

  }

  public static void main(String[] args) {

    String original_text = "合肥工业大学简称合工大，位于安徽省省会合肥市，创建于1945年秋，1960年10月22日被中共中央批准为全国重点" +
            "大学，是教育部直属高校，“211工程”和“985工程”优势学科创新平台项目建设高校，是一所以工科为主要特色，工、理、文、经、管、" +
            "法、教育多学科的综合性高等院校。";

    NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
    String sentence = nlpir.seg(original_text, 1);    //分词后带标签
    System.out.println("original text:\t" + original_text);
    System.out.println("seg by NLPIR:\t" + sentence);
    System.out.println("filtered by POS:\t" + WordFiltering.filterWordsByPOS(sentence," "));
    System.out.println("remove POSTag:\t" + removePOSTag(sentence, " "));
    System.out.println("filtered by stop words:\t" + removeSentenceStopWords(removePOSTag(sentence, " "), " ",
            "F:/experiment_data/stop_words_hit"));

  }

}
