package org.hfutec.preprocess;

import com.google.common.collect.Lists;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.hfutec.constant.ChineseSegmentation;
import org.hfutec.core.HFUTPipeline;
import org.hfutec.core.model.Document;
import org.hfutec.preprocess.wordseg.NLPIR;

import java.util.List;

/**
 * 文本预处理相关方法
 * Created by DuFei on 2017/3/9.
 */
public class Preprocessor {

  private ChineseSegmentation cutter;

  private void setChineseSegmentation( ChineseSegmentation cutter ){
    this.cutter = cutter;
  }


  public static void wordSegment (Document doc, HFUTPipeline.Cutter cutter ){

    getSegmentationByJIEBA("你好啊。你是谁？");

  }


  /*******
   * @content 分词后去除停用词
   * *********/
  public static String parseSentenceWithWordSegAndRemovingStopWords( String sentence, String stop_words_file){

    return null;

  }

  public static String getSegmentationByNLPIR (NLPIR nlpir, String sentence, String stop_word_file ){

    return nlpir.seg(sentence,1);

  }

  public static String getSegmentationByJIEBA ( String sentence ){

    JiebaSegmenter segmenter = new JiebaSegmenter();
    List<String> outStrList = Lists.newArrayList();
    segmenter.process(sentence,JiebaSegmenter.SegMode.SEARCH).forEach(segToken -> outStrList.add(segToken.word));
    return outStrList.toString().replace("[","").replace("]","").replace(","," ");

  }

  public static void main(String[] args) {

    /*NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
    BufferedReader reader = HFUTFileUtils.read(FilePipeline.weibo_file_without_html);
    String lineTxt;
    List<String> outList = Lists.newArrayList();
    List<String> dicts = Lists.newArrayList("宝能","王石","踏空");

    try {
      while( (lineTxt=reader.readLine()) != null ){

        String[] sentences = lineTxt.split(FilePipeline.sentence_seperator_reg);

        for( String sentence : sentences ){
          if( !sentence.equals(""))
            outList.add(sentence);
        }


      }

      outList = nlpir.segWithUserDict(outList, dicts, 0);
      outList = WordFiltering.removeSentenceListStopWords(outList, " ", FilePipeline.stop_word_hit);

      FileUtils.writeLines(new File(FilePipeline.weibo_file_seperate_by_senetence), outList);

    } catch (IOException e) {
      e.printStackTrace();
    }



    for( String sentence : outList ){
//      System.out.println(sentence);
    }*/

  }


}
