package org.hfutec.io;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by DuFei on 2017/2/22.
 * 文件输入输出类，作为commons-io的补充
 */
public class HFUTFileUtils {



  public static void writeList (String outFile, List<?> list, boolean append){

//    FileUtils.writeLines();

  }

  public static HashMap<Object,Integer> readMap( String input_file, String delimiter ){

    HashMap<Object,Integer> map = Maps.newHashMap();
    BufferedReader reader = read(input_file);
    String lineTxt;
    try {
      while ( (lineTxt=reader.readLine()) != null ){
        String[] field = lineTxt.split(delimiter);
        map.put(field[0], Integer.valueOf(field[1]));

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return  map;

  }

  /*******
   * save map content*******/
  public static <K,V> void save2DMap ( String input_path, Map<K,V> map ){

    List<String> outList = Lists.newArrayList();
    for(Map.Entry<K,V> entry : map.entrySet() ){

      outList.add(entry.getKey().toString()+"\t"+entry.getValue());

    }

    try {
      FileUtils.writeLines(new File(input_path), outList);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /*******
   * get line number of one file
   * ************/
  public static int getLineNumber (String inputFile ){

    return getLineNumber(new File(inputFile));

  }

  /*******
   * get line number of one file
   * ************/
  public static int getLineNumber (File inputFile ){

    BufferedReader reader = read(inputFile);
    int count = 0;
    try {
      while( reader.readLine() != null ){
        ++count;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return count;

  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( String input_path ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(input_path), StandardCharsets.UTF_8));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }

  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( File file ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(file), StandardCharsets.UTF_8));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }

  }


  /**********
   * @content read file list from input path, remove all files existing in output path
   * @param input_file_path input directory path
   * @param output_file_path output directory path
   * *********/
  public static Collection<File> readFileList (String input_file_path, String output_file_path ){

    File input_file = new File(input_file_path);
    Collection<File> file_list = new ArrayList<File>();

    if( input_file.isDirectory() ){

      Collection<String> input_file_names = getFileNamesFromDir(input_file_path);
      Collection<String> output_file_names = getFileNamesFromDir(output_file_path);

      input_file_names.removeAll(output_file_names);
      input_file_names.forEach( file_name -> file_list.add(new File(checkDirectory(input_file_path)+file_name)));
      return file_list;

    } else {

      System.out.println("input path is not directory!");
      return null;

    }

  }

  public static Collection<String> getFileNamesFromDir ( String input_directory ){


    Collection<String> file_names = new ArrayList<String>();

    FileUtils.listFiles(new File(input_directory),null,false).forEach( file -> file_names.add(file.getName()));

    return  file_names;

  }

  /**********
   * @content read file list from input path, remove all files existing in output path
   * @param directory_path input directory path
   * *********/
  public static Collection<File> readFileList (String directory_path ){

    File input_file = new File(directory_path);

    if( input_file.isDirectory() ){

      Collection<File> input_files = FileUtils.listFiles(new File(directory_path), null,false);

      return input_files;

    } else {

      System.out.println("input path is not directory!");
      return null;

    }

  }

  public static String checkDirectory ( String input_directory ){

    if ( input_directory.endsWith("\\") || input_directory.endsWith("/") ){
      return input_directory + File.separator;
    }else{
      return input_directory;
    }


  }

  public static void main(String[] args) {
    System.out.println(checkDirectory("d://hell//"));
  }


}
