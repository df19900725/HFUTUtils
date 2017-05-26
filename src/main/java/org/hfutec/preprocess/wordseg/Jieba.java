package org.hfutec.preprocess.wordseg;

import com.google.common.collect.Lists;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.List;

/**
 * 结巴分词
 * Created by DuFei on 2017/3/8.
 */
public class Jieba {

  public static void main(String[] args) {

    JiebaSegmenter segmenter = new JiebaSegmenter();
    String[] sentences =
            new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
                    "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
    for (String sentence : sentences) {
      System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
    }

    System.out.println(segmenter.process("#呆熊呆语##我为西游狂#悟空就是有这样的自信，但说回来，不是三打白骨精的时候才赶他走的吗？怎么这回打死几个山贼也赶他走啊？这唐僧没看出来猴哥才是能保住他命的保镖吗？ 三藏啊三藏……@天津卫视超级剧制", JiebaSegmenter.SegMode.SEARCH));
  }

  public List<String> seg(List<String> sentences){

    List<String> outList = Lists.newArrayList();
    JiebaSegmenter segmenter = new JiebaSegmenter();

    for( String sentence : sentences ){
      List<SegToken> tokens = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
      String outStr = "";
      for( SegToken segToken : tokens ){
        outStr += segToken.word+" ";
      }
      outList.add(outStr);
    }

    return outList;
  }

}
