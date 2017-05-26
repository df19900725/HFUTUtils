import org.apache.commons.io.FileUtils;
import org.hfutec.io.HFUTFileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by DuFei on 2017/3/7.
 */
public class FilterWordsByStopWords {

  private static String inFile = "d:\\koubei_seg_filter_by_POS";
  private static String stop_words_file = "F:\\DPMM\\stop_words_hit.txt";
  private static String stop_words_file2 = "F:\\DPMM\\stop_words_car.txt";
  private static String outFile = "d:\\koubei_seg_filter_by_stopwords";

  public static void main(String[] args) throws IOException {

    Collection<File> files = HFUTFileUtils.readFileList(inFile,outFile);
    List<String> stop_words = FileUtils.readLines(new File(stop_words_file), StandardCharsets.UTF_8);
    stop_words.addAll(FileUtils.readLines(new File(stop_words_file2),StandardCharsets.UTF_8));

    System.out.println(stop_words.contains("满意"));

    int count = 0;
    for( String line : FileUtils.readLines(new File(inFile),StandardCharsets.UTF_8)){


      ++count;
//      System.out.println(files.size()+"\t"+count);
//      String wordList = Arrays.asList(line.split(","));

      List<String> wordList = new ArrayList<String>(Arrays.asList(line.split(",")));
      Arrays.asList(wordList.removeAll(stop_words));
      FileUtils.write(new File(outFile), wordList.toString().replace("[","").replace("]","").replace(" ","")+"\r\n",StandardCharsets.UTF_8,true);


    }

  }


}
