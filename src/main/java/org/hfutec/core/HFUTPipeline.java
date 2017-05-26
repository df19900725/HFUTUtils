package org.hfutec.core;

/**
 * Created by DuFei on 2017/3/9.
 */
public class HFUTPipeline {

  public static enum Mode{

    TOKENIZE,
    SEGMENT,
    FILTER_BY_POS,
    FILTER_BY_STOP_WORDS;


  }

  public static enum Cutter{

    NLPIR,
    PULLWORD,
    JIEBA

  }

}
