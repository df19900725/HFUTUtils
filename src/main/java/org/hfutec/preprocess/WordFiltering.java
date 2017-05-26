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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单词过滤，包括去停用词等操作
 * Created by DuFei on 2017/5/11.
 */
public class WordFiltering {


  /*******
   * @content 去除停用词
   * @param sentence 经过分词后的句子
   * @param delimiter 词语的分隔符
   * @param stop_words_file 停用词文件位置
   * @return 去除停用词后的句子 *********/
  public static String removeSentenceStopWords ( String sentence, String delimiter, String stop_words_file ){

    List<String> words = new ArrayList<>(Arrays.asList(sentence.split(delimiter)));
    try {

      List<String> stopWordsList = FileUtils.readLines(new File(stop_words_file), StandardCharsets.UTF_8);
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
  public static String removeSentenceStopWords ( String sentence, String delimiter, List<String> stopWordsList ){

    List<String> words = new ArrayList<>(Arrays.asList(sentence.replaceAll(" +",delimiter).split(delimiter)));
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
  public static List<String> removeSentenceListStopWords ( List<String> sentences, String delimiter, List<String> stopWordsList ){

    List<String> outList = Lists.newArrayList();
    for( String sentence : sentences ){

      outList.add(removeSentenceStopWords(sentence, delimiter, stopWordsList ));

    }

    return outList;

  }

  /*******
   * @content 去除停用词
   * @param sentences 经过分词后的句子列表
   * @param delimiter 词语的分隔符
   * @param stop_words_file 停用词文件位置
   * @return 去除停用词后的句子 *********/
  public static List<String> removeSentenceListStopWords ( List<String> sentences, String delimiter, String stop_words_file ){

    try {

      List<String> stopWordsList = FileUtils.readLines(new File(stop_words_file), StandardCharsets.UTF_8);

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
  public static List<String> removeStopWordsByFile ( String inputFile, String delimiter, String stopWordsFile ){

    List<String> stopWordsList = Lists.newArrayList();
    try {
      stopWordsList = FileUtils.readLines(new File(stopWordsFile), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return removeStopWordsByFile(inputFile,delimiter,stopWordsList);

  }

  /*******
   * @content 去除停用词
   * @param inputFile 经过分词后的输入文件位置
   * @param delimiter 词语的分隔符
   * @param stopWordsList 停用词列表
   * @return 去除停用词后的句子 *********/
  public static List<String> removeStopWordsByFile ( String inputFile, String delimiter, List<String> stopWordsList ){

    List<String> outList = Lists.newArrayList();
    BufferedReader reader = HFUTFileUtils.read(inputFile);
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
  public static void removeStopWordsByDirectory ( String inputDir, String outputDir, String delimiter,
                                                  List<String> stopWordsList ){

    if( !outputDir.endsWith(File.pathSeparator)){
      outputDir += File.pathSeparator;
    }
    for( File file : new File(inputDir).listFiles() ){

      List<String> outList = removeStopWordsByFile(file.getAbsolutePath(),delimiter,stopWordsList);
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
  public static void removeStopWordsByDirectory ( String inputDir, String outputDir, String delimiter,
                                                  String stopWordsFile ){

    List<String> stopWordsList = Lists.newArrayList();
    try {
      stopWordsList = FileUtils.readLines(new File(stopWordsFile), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

    removeStopWordsByDirectory(inputDir,outputDir,delimiter,stopWordsList);

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

    String pattern = "";
    if( filter_POS.equals("") ){
      pattern = "/w|/x|/r|/c|/u|/y|/m|/q|/d|/p|/vshi|/vyou";
    }

    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(" "+word);

    if( word == null || word.trim().equals("") || m.find()){

      return null;

    }else{

      return word.split("/")[0];

    }

  }

  /********过滤句子中的某些词*****/
  public static String filterWordsByPOS ( String sentence, String delimiter, String filter_pos ){

    String sentence_filtered = "";

    for( String word : sentence.split(delimiter) ){
      word = filterWordsByPOS( word, filter_pos );
      if( word != null ){
        sentence_filtered += word + delimiter;
      }
    }

    if( !sentence_filtered.equals("") )
      sentence_filtered = sentence_filtered.substring(0, sentence_filtered.length()-delimiter.length());

    return sentence_filtered;

  }

  /********过滤句子中的某些词*****/
  public static List<String> filterWordsByPOS ( List<String> sentences, String delimiter, String filter_pos ){

    List<String> sentenceList = Lists.newArrayList();

    for( String sentence : sentences )
      sentenceList.add(filterWordsByPOS(sentence, delimiter, filter_pos));

    return sentenceList;

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
    for( String sentence : sentences )
      sentenceList.add(removePOSTag(sentence,delimiter));

    return sentenceList;

  }

  public static void main(String[] args) {

    NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
    String sentence = nlpir.seg("#医生才知道#人体的脑血管就像水管一样，使用年头多了总会生锈，生锈了水管就会变窄，而锈" +
            "斑掉下来会堵塞，脑部供血就会不足，会导致缺血性脑血管疾病。@神外费医生 建议家有老人或者存在中风危险因素的朋友，应学习了" +
            "解中风的先兆表现和应急处理。N4大法则，迅速揪出中风！", 1);
    System.out.println(sentence);
    System.out.println(filterWordsByPOS(sentence," ", ""));
    System.out.println(removePOSTag(sentence, " "));
    System.out.println(removeSentenceStopWords(removePOSTag(sentence, " "), " ",
            "F:/stop_words_hit.txt"));

  }

}
