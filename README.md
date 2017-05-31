# HFUTUtils
这是一个工具程序集合，方便我们平时处理数据。针对文本处理的内容较多。

详细的使用方法和案例参考：http://www.datalearner.com/blog/1051494253501911

-----------使用方法-----------

可以直接看源码文件，也可以直接下载jar包引入到工程中。注意，本项目使用jdk8+。使用Maven方式导入了Google Guava、Apache Commons等包。可以直接下载查看pom.xml文件后，添加到自己的项目中。

-----------分词用法-----------

分词集成了张华平分词 具体使用方式可参考 初始化NLPIR，传入Data文件夹和lib文件夹位置的参数，然后就可以分词了，注意授权文件的更新日期

NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

String output_text1 = nlpir.seg(input_text1, 0);

System.out.println(output_text1);

-----------过滤单词-----------

String original_text = "合肥工业大学简称合工大，位于安徽省省会合肥市，创建于1945年秋，1960年10月22日被中共中央批准为全国重点大学，是教育部直属高校，“211工程”和“985工程”优势学科创新平台项目建设高校，是一所以工科为主要特色，工、理、文、经、管、法、教育多学科的综合性高等院校。";
//分词

NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

String sentence = nlpir.seg(original_text, 1);    //分词后带标签

System.out.println("original text:\t" + original_text);

System.out.println("seg by NLPIR:\t" + sentence);        //分词结果

System.out.println("filtered by POS:\t" + WordFiltering.filterWordsByPOS(sentence," ", ""));        //按照词性标注结果过滤

System.out.println("remove POSTag:\t" + WordFiltering.removePOSTag(sentence, " "));        //去除词性标注的标签

System.out.println("filtered by stop words:\t" + WordFiltering.removeSentenceStopWords(WordFiltering.filterWordsByPOS(sentence," ", ""), " "，"F:/experiment_data/stop_words_hit")); //按照词性标注结果过滤后，再去除停用词，去除停用词方法的第二个参数是空格，表示单词由空格切分


-----------语料模型用法-----------

本程序可以自动将这些单词变成索引形式，并将文档用SparseVSM表示。保存后生成三个文件：docIndex、wordIndex、sparseVSM。分别表示文档-索引、单词-索引和稀疏向量空间模型。（注意，输入语料是文档的时候，VSM一行对应之前的一行，因此docIndex为空。输入语料是文件夹时候，docIndex是文件名字-索引，VSM是按索引0-ndocs来的）。

//读取文件并保存语料

Corpus corpus = new Corpus(inputFile, false);

corpus.saveCorpus(inputFile);

//载入之前保存的语料

Corpus corpusLoading = new Corpus();

corpusLoading.loadCorpus(inputFile);

//输入是文件夹的测试，去掉false参数即可

String inputDir = "D:/test";

String outputDir = "d:/test_out";

Corpus corpusDir = new Corpus(inputDir);

corpusDir.saveCorpus(outputDir);
