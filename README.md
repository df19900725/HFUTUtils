# HFUTUtils
这是一个工具程序集合，方便我们平时处理数据。针对文本处理的内容较多。

详细的使用方法和案例参考：[HFUTUtils的使用 | 数据学习者官方网站（DataLearner）](http://www.datalearner.com/blog/1051494253501911)


[TOC]


##### [文件操作增强类HFUTFileUtils](#hfutfileutils)
##### 张华平分词和结巴分词 NLPIR/Jieba
##### 语料模型Corpus

-----------使用方法-----------

可以直接看源码文件，也可以直接下载jar包引入到工程中。注意，本项目使用jdk8+。使用Maven方式导入了Google Guava、Apache Commons等包。可以直接下载查看pom.xml文件后，添加到自己的项目中。

### <a href='#hfutfileutils' id='hfutfileutils'>HFUTFileUtils</a>

这是一个增强的文件操作，提供了集中方便读取文件的方法。Apache Commons IO已经提供了很多很好文件操作了。这里补充了一些没有但很实用的。

```
//从输入文件目录中读取文件，并去除输出目录中存在的文件。通常我们需要读取一些某个目录下所有的文件，但是又想去掉一些在目标目录中存在的文件，可以使用如下方法。
String source_directory = "d:/source";
String target_directory = "d:/target";
Collection<File> files = HFUTFileUtils.readFileList(source_directory, target_directory);

//读取某个文件夹下所有文件的名字到List中
String inputDirectory = "D:/test";
Collection<String> fileNames = HFUTFileUtils.readFileNameByDirectory(inputDirectory);

//获取某个文件的总行数
int lineNumber = HFUTFileUtils.getLineNumber(inputDirectory);

//将一个Map键值对写入到一个文件中

HashMap<String,Integer> map = new HashMap<String,Integer>();
map.put("test",1);
HFUTFileUtils.save2DMap(inputDirectory, map);

//将一个两列的文件读入到一个Map中，第一个参数是文件输入的路径或者是File，第二个参数是列的分隔符
HashMap<String,Integer> map = HFUTFileUtils.readFileToMapByDelimiter(file,"\t");

//使用BufferedReader读取文件，原生的Java使用BufferedReader有一长串，我们改写了其中部分

//原生写法
BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
//改写后的写法
BufferedReader reader = HFUTFileUtils.read(file, "utf-8");

```

-----------分词用法-----------

分词集成了张华平分词 具体使用方式可参考 初始化NLPIR，传入Data文件夹和lib文件夹位置的参数，然后就可以分词了，注意授权文件的更新日期，在博客中我们提供了更多的使用案例，可以参考。

NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

String output_text1 = nlpir.seg(input_text1, 0);

System.out.println(output_text1);

我们还提供了结巴分词，结巴分词的功能相对较少，它原本是Python版本的，有人将其转换成Java版本，也非常好用，为了避免还要去下载，本程序这里就集成了一下结巴分词。在返回结果上去掉了原来java版本的词语位置信息。默认使用的是原来的SEARCH模式分词，原来的Java版本依然可用。

结巴分词：https://github.com/fxsjy/jieba

结巴分词（Java）：https://github.com/huaban/jieba-analysis


Jieba jieba = new Jieba();

List<String> outList = jieba.seg(input_text_list);

for (String sentence : outList) {

  System.out.println(sentence);

}


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

Corpus corpus = new Corpus(inputFile, "utf-8");

//保存到某个文件夹中，会生成三个文件
corpus.saveCorpus(outputPath);

Corpus会自动计算将文本单词转换成索引，也就是每个单词对应一个int值，从0开始，可自动得到很多属性
1）单词和其对应的索引（BiMap格式）

2）稀疏矩阵，即每个文档下不同单词的数量（单词数为0不保存）

3）每个单词对应的文档集合，即某个单词被哪些文档包含过

4）单词数量word count

5）文档数

6）单词数（包含重复）

7）词汇数（不同单词数量）

//载入之前保存的语料

Corpus corpusLoading = new Corpus();

corpusLoading.loadCorpus(outputPath);

