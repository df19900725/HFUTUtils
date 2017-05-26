package org.hfutec.nlp.model;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 計算word count
 * Created by DuFei on 2017/5/22.
 */
public class WordCount {

  public static void main(String[] args) throws IOException {


  }

  public static HashMap<String,Integer> getWordCountByDir( String input_dir, String delimiter ){

    HashMap<String,Integer> wordCount = Maps.newHashMap();
    try {
    for( File file : new File(input_dir).listFiles() ){

        for( String word : FileUtils.readFileToString(file, StandardCharsets.UTF_8).split(delimiter) ){
          word = word.trim();
          if( !word.equals("") ) {
            if (wordCount.containsKey(word)) {
              wordCount.put(word, wordCount.get(word) + 1);
            } else {
              wordCount.put(word, 1);
            }
          }

        }

    }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return wordCount;

  }

  public static HashMap<String,Integer> getWordCountByFile( String input_file, String delimiter ){

    HashMap<String,Integer> wordCount = Maps.newHashMap();
    try {
      for( String word : FileUtils.readFileToString(new File(input_file), StandardCharsets.UTF_8).split(delimiter) ){
        if( !word.equals("") ) {
          if (wordCount.containsKey(word)) {
            wordCount.put(word, wordCount.get(word) + 1);
          } else {
            wordCount.put(word, 1);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return wordCount;

  }

}
