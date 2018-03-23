import org.hfutec.nlp.model.Corpus;

/**
 * 读取语料
 * Created by DuFei on 2017/5/26.
 */
public class CorpusTest {

  public static void main(String[] args) {

    String inputFile = "d:/test.txt";

    //读取文件并保存语料
    Corpus corpus = new Corpus(inputFile);
    System.out.println(corpus.docSize);
    System.out.println(corpus.wordSize);
    System.out.println(corpus.vocabularySize);
    System.out.println(corpus.wordIndex);

    corpus.saveCorpus(inputFile);


    //载入之前保存的语料
    Corpus corpusLoading = new Corpus();
    corpusLoading.loadCorpus(inputFile);

    //输入是文件夹的测试，去掉false参数即可
    String inputDir = "D:/test";
    String outputDir = "d:/test_out";
    Corpus corpusDir = new Corpus(inputDir);
    corpusDir.saveCorpus(outputDir);

  }

}
