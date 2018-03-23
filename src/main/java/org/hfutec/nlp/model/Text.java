package org.hfutec.nlp.model;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 读取未分词的语料内容
 * Created by DuFei on 2017/7/17.
 */
public class Text {

  public String inputPath;
  public File inputFile;
  public String charset = "UTF-8";

  public HashMap<String,List<String>> docs = Maps.newHashMap();
  public HashMap<String,String> docContent = Maps.newHashMap();

  public Text(){}

  public Text( String inputPath ){

    this.inputPath = inputPath;
    this.inputFile = new File(inputPath);

    if( inputFile.exists() ){

      if( inputFile.isDirectory() ){

        parseDocs();

      }else{

      }

    }else{

      System.out.println("Initialization failed! The input file or directory does not exist!");
      System.out.println("Input path:"+inputPath);

    }

  }

  public Text( String inputPath, String charset ){

    this.inputPath = inputPath;
    this.inputFile = new File(inputPath);
    this.charset = charset;

    if( inputFile.exists() ){

      if( inputFile.isDirectory() ){

        parseDocs();

      }else{

      }

    }else{

      System.out.println("Initialization failed! The input file or directory does not exist!");
      System.out.println("Input path:"+inputPath);

    }

  }

  private void parseDocs(){

    File[] fileList = inputFile.listFiles();

    for( File file : fileList ){

      try {
        docs.put(file.getName(), FileUtils.readLines(file,charset));
        docContent.put(file.getName(), FileUtils.readFileToString(file, charset));
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

}
