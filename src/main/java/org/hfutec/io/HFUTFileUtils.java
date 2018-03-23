package org.hfutec.io;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by DuFei on 2017/2/22.
 * 文件输入输出类，作为commons-io的补充
 */
public class HFUTFileUtils {

  //读取目录下文件的名字
  public static Set<String> readFileNamesByDirectory( String inputDirectory ){

    Set<String> names = Sets.newHashSet();

    File inputPath = new File(inputDirectory);
    if( inputPath.exists() && inputPath.isDirectory() ){

      File[] fileList = inputPath.listFiles();

      if( fileList != null ) {
        for (File file : fileList) {
          names.add(file.getName());
        }
      }

    }else{

      System.out.println("input directory does not exist or is not a directory!");

    }


    return names;

  }

  public static void readSampleFromFile( String inputFile, int num ){

    BufferedReader reader = read(inputFile,"UTF-8");
    String lineTxt = "";
    int count = 0;
    try {
      while ( (lineTxt=reader.readLine()) != null ){

        System.out.println(lineTxt);
        count++;
        if( count%num == 0 ){

          break;

        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }


  /*********
   * read directory to a map, key is the file name, value is the content
   * @param inputDirectory : input directory
   * @param encoding : the encoding of the files ********/
  public static HashMap<String,String> readFileToMap( String inputDirectory, String encoding ){

    HashMap<String,String> map = Maps.newHashMap();
    File inputPath = new File(inputDirectory);
    if( inputPath.exists() && inputPath.isDirectory() ){

      File[] fileList = inputPath.listFiles();

      if( fileList != null ) {
        for (File file : fileList) {

          try {
            map.put(file.getName(), FileUtils.readFileToString(file, encoding));
          } catch (IOException e) {
            e.printStackTrace();
          }

        }
      }

    }else{
      System.out.println("input directory does not exist or is not a directory!");
    }
    return map;

  }

  /*********
   * read lines to a map, use delimiter to split each line
   * @param input_path : input file
   * @param delimiter : the delimiter of each line ********/
  public static HashMap<String,Integer> readFileToMapByDelimiter( String input_path, String delimiter ){

    HashMap<String,Integer> map = Maps.newHashMap();
    try {
      for( String line : FileUtils.readLines(new File(input_path), StandardCharsets.UTF_8)){
        map.put(line.split(delimiter)[0], Integer.valueOf(line.split(delimiter)[1]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return map;

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
  public static int getLineNumber (String inputFile ) throws IOException {

    return getLineNumber(new File(inputFile));

  }

  /*******
   * get line number of one file
   * ************/
  public static int getLineNumber (File inputFile ) throws IOException {

    InputStream is = new BufferedInputStream(new FileInputStream(inputFile));
    try {
      byte[] c = new byte[1024];
      int count = 0;
      int readChars = 0;
      boolean endsWithoutNewLine = false;
      while ((readChars = is.read(c)) != -1) {
        for (int i = 0; i < readChars; ++i) {
          if (c[i] == '\n')
            ++count;
        }
        endsWithoutNewLine = (c[readChars - 1] != '\n');
      }
      if(endsWithoutNewLine) {
        ++count;
      }
      return count;
    } finally {
      is.close();
    }

  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( String input_path, String encoding ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(input_path), encoding));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( File file ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(file), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( String file ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(file), "utf-8"));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

  /*******
   * new a BufferedReader******/
  public static BufferedReader read ( File file, String encoding ){

    try {
      return new BufferedReader(new InputStreamReader( new FileInputStream(file), encoding));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

  /**********
   * read file list from input path, remove all files existing in output path
   * @param input_file_path input directory path
   * @param output_file_path output directory path
   * *********/
  public static Collection<File> readFileList (String input_file_path, String output_file_path ){

    File input_file = new File(input_file_path);
    Collection<File> file_list = new ArrayList<>();

    if( input_file.isDirectory() ){

      Collection<String> input_file_names = readFileNamesByDirectory(input_file_path);
      Collection<String> output_file_names = readFileNamesByDirectory(output_file_path);

      input_file_names.removeAll(output_file_names);
      input_file_names.forEach( file_name -> file_list.add(
              new File(checkDirectory(input_file_path)+file_name)));
      return file_list;

    } else {

      System.out.println("input path is not directory!");
      return null;

    }

  }

  /**********
   * read file list from input path, remove all files existing in output path
   * @param directory_path input directory path
   * *********/
  public static Collection<File> readFileList (String directory_path ){

    if( new File(directory_path).isDirectory() ){

      return FileUtils.listFiles(new File(directory_path), null,false);

    } else {

      System.out.println("input path is not directory!");
      return null;

    }

  }

  public static String checkDirectory ( String input_directory ){

    if ( input_directory.endsWith("\\") || input_directory.endsWith("/") ){
      return input_directory + File.separator;
    }else{
      return input_directory + File.separator;
    }

  }

  public static void createDirIfNotExist( String inputDir ){

    File file = new File(inputDir);
    if( !file.exists() ){
      file.mkdirs();
    }

  }

  public static void cleanFile(String inputFile,String delimiter, String encoding){

    List<String> outList = Lists.newArrayList();
    try {
      for( String line : FileUtils.readLines(new File(inputFile), encoding)){
        if( !line.equals("") ){
          outList.add(line.replaceAll(delimiter + "+", delimiter));
          System.out.println("line:\t"+line);
        }
      }

      FileUtils.writeLines(new File(inputFile), outList);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }


  public static void main(String[] args) {

    /*String inputPath = "F:\\experiment_data\\weibo_data\\personal\\weibo_content_seg\\weibo_content_jieba";
    long start = System.currentTimeMillis();

    readFileNamesByDirectory(inputPath);

    long end = System.currentTimeMillis();
    System.out.println("time:\t"+(end-start));*/

    cleanFile("D:\\dhdp\\2-1.txt", "\t", "utf-8");

  }


}
