package org.hfutec.core.model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by DuFei on 2017/3/9.
 */
public class Document {

  private String file_path;
  private Charset charset;
  private File file;
  private static List<String> lineList;
  private static int docSize;

  public Document (String file_path, Charset charset){

    this.file_path = file_path;
    this.charset = charset;

  }

  private void init(){

    try {
      lineList = FileUtils.readLines(new File(file_path), charset);
      docSize = lineList.size();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public static List<String> getAllLines (){
    return lineList;
  }

  public static int getDocSize(){
    return docSize;
  }

}
