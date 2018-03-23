package org.hfutec.preprocess.wordseg;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.jna.Library;
import com.sun.jna.Native;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DuFei on 2017/2/22.
 * word segmentation
 */
public class NLPIR {

  public static String libDir;

  public NLPIR(String libDir, String dataDir){

    NLPIR.libDir = libDir;

    try {
      init(dataDir);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // 定义接口CLibrary，继承自com.sun.jna.Library
  public interface CLibrary extends Library {

    // 定义并初始化接口的静态变量
    CLibrary Instance = (CLibrary) Native.loadLibrary(
            NLPIR.libDir, CLibrary.class);

    int NLPIR_Init(byte[] sDataPath, int encoding,
                   byte[] sLicenceCode);

    String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);

    String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

    int NLPIR_ImportUserDict(String sFilename, boolean bOverwrite);

    int NLPIR_DelUsrWord(String sWord);

    public int NLPIR_AddUserWord(String sWord);

//    String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);

    void NLPIR_Exit();
  }

  private void init(String dataDir) throws IOException{

    String system_charset = "utf-8";
    int charset_type = 1;
    int init_flag = CLibrary.Instance.NLPIR_Init(dataDir
            .getBytes(system_charset), charset_type, "0"
            .getBytes(system_charset));

    if (0 == init_flag)
      System.err.println("初始化失败！");

  }

  public String extractKeyWords( String sentence ){

    String keywords = CLibrary.Instance.NLPIR_GetKeyWords(sentence, 10,true);
    return keywords;

  }

  public String seg(String sentence, int bPOSTagged) {

    String outStr = CLibrary.Instance.NLPIR_ParagraphProcess(sentence, bPOSTagged);
//    CLibrary.Instance.NLPIR_Exit();
    return outStr;

  }

  public String segWithoutTag(String sentence) {

    String outStr = CLibrary.Instance.NLPIR_ParagraphProcess(sentence, 0);
//    CLibrary.Instance.NLPIR_Exit();
    return outStr;

  }

  public static List<String> segWithUserDict(List<String> sentences, String dictPath, int bPOSTagged) throws IOException {

    int nCount = CLibrary.Instance.NLPIR_ImportUserDict(dictPath,true);
    System.out.println("user dict size:\t"+nCount);

    List<String> outList = new ArrayList<String>();
    int count = 0;
    for( String str : sentences ){
      ++count;
      if( count%1000==0 )
        System.out.println("all size:\t"+sentences.size()+"------current size:\t"+count);

      outList.add(CLibrary.Instance.NLPIR_ParagraphProcess(str, bPOSTagged));
    }

//    CLibrary.Instance.NLPIR_DelUsrWord(dictPath);
    CLibrary.Instance.NLPIR_Exit();

    return outList;

  }

  public List<String> segWithUserDict(List<String> sentences, List<String> dicts, int bPOSTagged) {

    for(String word : dicts ){
      CLibrary.Instance.NLPIR_AddUserWord(word);
    }

    List<String> outList = new ArrayList<String>();
    int count = 0;
    for( String str : sentences ){
      ++count;
      if( count%1000==0 )
        System.out.println("all size:\t"+sentences.size()+"------current size:\t"+count);

      outList.add(CLibrary.Instance.NLPIR_ParagraphProcess(str, bPOSTagged));
    }

//    CLibrary.Instance.NLPIR_DelUsrWord(dictPath);
    CLibrary.Instance.NLPIR_Exit();

    return outList;

  }

  public String segWithUserDict(String sentences, List<String> dicts, int bPOSTagged) {

    for(String word : dicts ){
      CLibrary.Instance.NLPIR_AddUserWord(word);
    }

    String str = CLibrary.Instance.NLPIR_ParagraphProcess(sentences, bPOSTagged);

//    CLibrary.Instance.NLPIR_DelUsrWord(dictPath);
    CLibrary.Instance.NLPIR_Exit();

    return str;

  }

  public List<String> segList(List<String> sentences, int tagged){

    List<String> outList = new ArrayList<String>();
    int count = 0;
    for( String str : sentences ){
      ++count;
      if( count%1000==0 )
        System.out.println("all size:\t"+sentences.size()+"------current size:\t"+count);

      outList.add(CLibrary.Instance.NLPIR_ParagraphProcess(str, tagged));
    }

    CLibrary.Instance.NLPIR_Exit();

    return outList;

  }

  public HashMap<String,String> segMapValue(HashMap<String,String> docs, int tagged){

    HashMap<String,String> outMap = Maps.newHashMap();
    int count = 0;
    for( Map.Entry<String, String> entry : docs.entrySet() ){
      ++count;
      if( count%1000==0 )
        System.out.println("all size:\t"+docs.size()+"------current size:\t"+count);

      outMap.put(entry.getKey(),CLibrary.Instance.NLPIR_ParagraphProcess(entry.getValue(), tagged));
    }

    CLibrary.Instance.NLPIR_Exit();

    return outMap;

  }

  public HashMap<String,String> segMapValue(HashMap<String,String> docs, List<String> userDict, int tagged){

    for(String word : userDict ){
      CLibrary.Instance.NLPIR_AddUserWord(word);
    }

    HashMap<String,String> outMap = Maps.newHashMap();
    int count = 0;
    for( Map.Entry<String, String> entry : docs.entrySet() ){
      ++count;
      if( count%1000==0 )
        System.out.println("all size:\t"+docs.size()+"------current size:\t"+count);

      outMap.put(entry.getKey(),CLibrary.Instance.NLPIR_ParagraphProcess(entry.getValue(), tagged));
    }

    CLibrary.Instance.NLPIR_Exit();

    return outMap;

  }

  public HashMap<String,List<String>> segMapListValue(HashMap<String,List<String>> docs, List<String> userDict, int tagged){

    for(String word : userDict ){
      CLibrary.Instance.NLPIR_AddUserWord(word);
    }

    HashMap<String,List<String>> outMap = Maps.newHashMap();
    int count = 0;
    for( Map.Entry<String, List<String>> entry : docs.entrySet() ){
      ++count;
      if( count%10==0 )
        System.out.println("all size:\t"+docs.size()+"------current size:\t"+count);

      List<String> list = Lists.newArrayList();
      outMap.put(entry.getKey(), list);

      for( String line : entry.getValue() ){
        outMap.get(entry.getKey()).add(CLibrary.Instance.NLPIR_ParagraphProcess(line, tagged));
      }
    }

    CLibrary.Instance.NLPIR_Exit();

    return outMap;

  }

  public void segMapListValue(HashMap<String,List<String>> docs, List<String> userDict,
                                                      int tagged, String output_dir){

    for(String word : userDict ){
      CLibrary.Instance.NLPIR_AddUserWord(word);
    }

    int count = 0;
    for( Map.Entry<String, List<String>> entry : docs.entrySet() ){
      ++count;
      if( count%10==0 )
        System.out.println("all size:\t"+docs.size()+"------current size:\t"+count);

      List<String> list = Lists.newArrayList();

      for( String line : entry.getValue() ){
        list.add(CLibrary.Instance.NLPIR_ParagraphProcess(line, tagged));
      }

      try {
        FileUtils.writeLines(new File(output_dir+File.separator+entry.getKey()), list);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    CLibrary.Instance.NLPIR_Exit();

  }

  public static void main(String[] args) throws IOException {

    String sInput = "#呆熊呆语##我为西游狂#悟空就是有这样的自信，但说回来，不是三打白骨精的时候才赶他走的吗？怎么这回打" +
            "死几个山贼也赶他走啊？这唐僧没看出来猴哥才是能保住他命的保镖吗？ 三藏啊三藏……@天津卫视超级剧制";

    String dictPath = "F:\\DPMM\\dict.txt";

    NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

    System.out.println(nlpir.seg(sInput,1));
    nlpir.seg(sInput,0);
//    CLibrary.Instance.NLPIR_ImportUserDict(dictPath,true);
    /*CLibrary.Instance.NLPIR_AddUserWord("微博");
    System.out.println(nlpir.segWithUserDict(Lists.newArrayList(sInput),dictPath,0));
    CLibrary.Instance.NLPIR_DelUsrWord(dictPath);*/

    String keywords = CLibrary.Instance.NLPIR_GetKeyWords(sInput, 10,true);
    System.out.println(keywords);
  }

}
