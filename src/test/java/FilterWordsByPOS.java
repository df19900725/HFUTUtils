import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DuFei on 2017/3/7.
 */
public class FilterWordsByPOS {

  private static String inFile = "D:\\koubei_seg_by_jieba";
  private static String outFile = "D:\\koubei_seg_filter_by_POS";

  public static void main(String[] args) throws IOException {

//    System.out.println(getWordByDelimiter("青海 ns"));

    for( String str : FileUtils.readLines(new File(inFile), StandardCharsets.UTF_8)){

      List<String> wordList = Lists.newArrayList();
      Arrays.asList(str.split("-")).forEach( line -> wordList.add(getWordByDelimiter(line)));
//      FileUtils.readLines(new File(inFile),StandardCharsets.UTF_8)
//      Arrays.asList(FileUtils.readFileToString(file, StandardCharsets.UTF_8).split("\n")).forEach(line -> wordList.add(getWordByDelimiter(line)));
//      Collection<String> result = ;
//      System.out.println(file.getName()+"\n"+result);

      FileUtils.write(new File(outFile),
              Collections2.filter(wordList, Predicates.notNull()).toString().replace("[","").replace("]","").replace(" ","")+"\r\n", StandardCharsets.UTF_8, true);


    }




    /*Collection<File> files = HFUTFileUtils.readFileList(inFile,outFile);

    int count = 0;
    for( File file : files){
      ++count;
      System.out.println(files.size()+"\t"+count);
      List<String> wordList = Lists.newArrayList();


//      break;

    }
    String str = new FilterWordsByPOS().getWordByDelimiter("RU0bAPN eng");
    System.out.println(str);*/

  }

  /*********
   * /w 标点符号
   * /r 代词
   * /c 并列连词
   * /u 助词
   * /y 语气词
   * /m 数词
   * /q 量词
   * /d 副词
   * /p 介词
   * 是 有
   * ************/
  private static String getWord( String word ){

    String pattern = "w|r|c|u|y|m|q|d|p|eng";
    Pattern r = Pattern.compile(pattern);

    Matcher m = r.matcher(word);

    /*if( m.find()){
      System.out.println("yes it is true");
    }*/

    if( word == null || word.trim().equals("") || m.find()){

      return null;

    }else{

      return word.split(" ")[0];


    }


  }

  /*********
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
   * ************/
  private static String getWordByDelimiter( String word ){

    String pattern = "x|r|c|u|y|m|q|d|p|eng";
    Pattern r = Pattern.compile(pattern);

    Matcher m = r.matcher(" "+word);

    /*if( m.find()){
      System.out.println("yes it is true");
    }*/

    if( word == null || word.trim().equals("") || m.find()){

      return null;

    }else{

      return word.split(" ")[0];


    }


  }

}
