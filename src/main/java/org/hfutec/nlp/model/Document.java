package org.hfutec.nlp.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.linear.OpenMapRealVector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by DuFei on 2017/3/11.
 * 文档主类。内存占有较高。
 */
public abstract class Document {

  protected BiMap<String,Integer> wordIndex = HashBiMap.create();     //单词索引
  protected OpenMapRealVector bag_of_word = new OpenMapRealVector();                          //词袋

  protected String word_delimiter;
  protected Charset charset;

  protected int sizeOfVocabulary = 0;
  protected int totalNumberOfWords = 0;

  /******
   * 文档构造函数，初始化一个文档，参数是该文档的内容以及单词的分隔符 ****/
  public Document(Charset charset, String word_delimiter){

    this.charset = charset;
    this.word_delimiter = word_delimiter;

  }

  //根据当前的文档状态解析添加的内容
  /********
   * @param file_path content added
   * @return the bag_of_words of current doc
   *******/

  public OpenMapRealVector parseDocByFilePath ( String file_path ){

    return parseDoc(formatContentByFilePath(file_path));

  }

  public OpenMapRealVector parseDoc ( String content ){


    for( String word : content.toString().split(word_delimiter) ){

      word = word.trim();
      int currentWordIndex = 0;
      if( !wordIndex.containsKey(word) ){

        wordIndex.put(word,sizeOfVocabulary);

        bag_of_word = bag_of_word.append(1);

        sizeOfVocabulary++;
      } else {

        currentWordIndex = wordIndex.get(word);
        bag_of_word.setEntry(currentWordIndex,bag_of_word.getEntry(currentWordIndex)+1);

      }
      totalNumberOfWords++;

    }

    return bag_of_word;

  }

  public String formatContentByFilePath( String file_path){

    return formatContentByFile(new File(file_path));

  }

  public String formatContentByFile( File file){

    String content = "";
    try {
      content = FileUtils.readFileToString(file, charset);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return formatContent(content);

  }

  public String formatContent( String content ){

    return content.replaceAll("\r\n",word_delimiter).replace("\n",word_delimiter).replaceAll("["+word_delimiter+"]+",word_delimiter);

  }

  public String getWordByIndex ( int index ){
    return wordIndex.inverse().get(index);
  }

  public int getIndexOfWord ( String word ){
    return wordIndex.get(word);
  }

  /*public ImmutableBiMap<String, Integer> getWordIndex() {
    return wordIndex;
  }

  public ImmutableSet<Integer> getWordSet() {
    return wordSet;
  }

  public ImmutableMap<Integer, Integer> getWordCount() {
    return wordCount;
  }*/

  public int getSizeOfVocabulary(){ return sizeOfVocabulary; }

  public BiMap<String, Integer> getWordIndex() {
    return wordIndex;
  }

  public Set<Integer> getWordSet() {
    return null;
  }

  public int getTotalNumberOfWords() {
    return totalNumberOfWords;
  }

}
