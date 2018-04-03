# HFUTUtils
这是一个工具程序集合，方便我们平时处理数据。针对文本处理的内容较多。

详细的使用方法和案例参考：[HFUTUtils的使用 | 数据学习者官方网站（DataLearner）](http://www.datalearner.com/blog/1051494253501911)


[TOC]


##### [一、文件操作增强类HFUTFileUtils](#hfutfileutils)
##### [二、张华平分词和结巴分词 NLPIR/Jieba](#NLPIR/Jieba分词)
##### [三、语料模型Corpus](#corpus)
##### [四、文本预处理TextIO](#DataIOUtils)


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

### <a href='#NLPIR/Jieba分词' id='NLPIR/Jieba分词'>张华平分词和结巴分词用法</a>

分词集成了张华平分词 具体使用方式可参考 初始化NLPIR，传入Data文件夹和lib文件夹位置的参数，然后就可以分词了，注意授权文件的更新日期，在博客中我们提供了更多的使用案例，可以参考。

```
String input_text1 = "合肥工业大学（Hefei University of Technology）简称“合工大（HFUT）”，创建于1945 年，坐落于全国四大科教城市之一，素有大湖名城、创新高地之称的江淮枢纽名城安徽省合肥市";
String input_text2 = "学校创建于1945年，1960年被中共中央批准为全国重点大学";

HashMap<String,String> input_text_map = Maps.newHashMap();
input_text_map.put("1",input_text1);
input_text_map.put("2",input_text2);

//初始化，写出两个文件夹位置，基本上只要改"d:/nlpir"就可以了
NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

//对单个字符进行分词，第二个参数：0-不带标签输出，1-带词性标签输出。
String output_text1 = nlpir.seg(input_text1, 0);

System.out.println("-------------对单个字符进行分词------------");
System.out.println(output_text1);

//对一组字符进行分词，第二个参数：0-不带标签输出，1-带词性标签输出。
NLPIR nlpir2 = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
List<String> input_text_list = new ArrayList<>();
input_text_list.add(input_text1);
input_text_list.add(input_text2);
List<String> output_text2 = nlpir2.segList(input_text_list, 1);

System.out.println("-------------对一组字符进行分词------------");
for( String line : output_text2 ){
  System.out.println(line);
}
//对带有key值的HashMap分词，key是id之类的，value是待分词结果
NLPIR nlpir3 = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
HashMap<String,String> output_text_map = nlpir3.segMapValue(input_text_map, 1);

System.out.println("-------------对带有key值的HashMap分词------------");
for( Map.Entry<String, String> entry : output_text_map.entrySet() ){
  System.out.println(entry.getKey()+"\t"+entry.getValue());
}
//自定义词典的使用，第一种方法无效，应该是张华平分词自己的bug，推荐使用第二种
NLPIR nlpir4 = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
String dictPath = "F:\\dict.txt";

try {
  System.out.println("-------------第一种自定义词典的使用------------");
  List<String> output_text_with_dict = nlpir4.segWithUserDict(input_text_list,dictPath,1);
  for( String line : output_text_with_dict ){
    System.out.println(line);
  }
} catch (IOException e) {
  e.printStackTrace();
}

//第二种自定义词典
NLPIR nlpir5 = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");
List<String> list = Lists.newArrayList("合肥工业大学 n","合工大 n");
System.out.println("-------------第二种自定义词典的使用------------");
List<String> output_text_with_dict = nlpir5.segWithUserDict(input_text_list,list,1);
for( String line : output_text_with_dict ){
  System.out.println(line);
}

```

我们还提供了结巴分词，结巴分词的功能相对较少，它原本是Python版本的，有人将其转换成Java版本，也非常好用，为了避免还要去下载，本程序这里就集成了一下结巴分词。在返回结果上去掉了原来java版本的词语位置信息。默认使用的是原来的SEARCH模式分词，原来的Java版本依然可用。

结巴分词：https://github.com/fxsjy/jieba

结巴分词（Java）：https://github.com/huaban/jieba-analysis

```
Jieba jieba = new Jieba();
List<String> outList = jieba.seg(input_text_list);
for (String sentence : outList) {
  System.out.println(sentence);
}
```

### <a href='#corpus' id='corpus'>语料模型Corpus用法</a>

本程序可以自动将这些单词变成索引形式，并将文档用SparseVSM表示。保存后生成三个文件：docIndex、wordIndex、sparseVSM。分别表示文档-索引、单词-索引和稀疏向量空间模型。（注意，输入语料是文档的时候，VSM一行对应之前的一行，因此docIndex为空。输入语料是文件夹时候，docIndex是文件名字-索引，VSM是按索引0-ndocs来的）。

```java
import org.hfutec.nlp.model.Corpus;
/**
 * 读取语料
 * Created by DuFei on 2017/5/26.
 */
public class CorpusTest {
  public static void main(String[] args) {
    
    //输入可以是文件或者文件夹，如果是文件，则一行表示一个文档，如果是文件夹则一个文件是一个文档
    String inputFile = "d:/test.txt";
    
    //读取文件并保存语料
    Corpus corpus = new Corpus(inputFile);
    corpus.saveCorpus(inputFile);
    
    //载入之前保存的语料
    Corpus corpusLoading = new Corpus();
    corpusLoading.loadCorpus(inputFile);

  }
}

```

Corpus会自动计算将文本单词转换成索引，也就是每个单词对应一个int值，从0开始，可自动得到很多属性
1）单词和其对应的索引（BiMap格式）

2）稀疏矩阵，即每个文档下不同单词的数量（单词数为0不保存）

3）每个单词对应的文档集合，即某个单词被哪些文档包含过

4）单词数量word count

5）文档数

6）单词数（包含重复）

7）词汇数（不同单词数量）

Corpus提供了很多属性，经过Corpus计算后可以得到如下结果

```
corpus.docSize;       //文档数量
corpus.wordSize = 0;      //单词总数（包含重复）
corpus.vocabularySize = 0;   //词汇数量（不重复）

//下面类似的，这里列举出来余下的属性和类型，调用直接使用corpus.wordSet这种方式就好
public HashSet<Integer> wordSet = Sets.newHashSet();      //单词集合
public HashBiMap<String,Integer> docIndex = HashBiMap.create();     //文档索引
public HashBiMap<String,Integer> wordIndex = HashBiMap.create();    //单词索引
public HashMap<Integer,HashMap<Integer,Integer>> sparseVSMOfDocs = Maps.newHashMap();  //文档的稀疏空间表示
public HashMap<Integer,HashSet<Integer>> wordDocs = Maps.newHashMap(); //单词对应的文档编号
public HashMap<Integer,Integer> wordCount = Maps.newHashMap();      //单词计数，即每个单词对应的数量，单词用索引表示
public HashMap<Integer,HashSet<Integer>> docWords = Maps.newHashMap();  //文档中包含的单词
```

### <a href='#DataIOUtils' id='DataIOUtils'>文本数据处理DataIOUtils</a>

该类提供了几个常用的方法，包括读取文件中的几个列的数据，将文本中的变量转成0-1表示的方式（一般叫做Binaries），在数据挖掘中一般我们需要将分类属性变成0-1的形式。这个方法相当于pandas的get_dummies的java实现

例如有如下的数据

```$xslt
A A B C D
A C D C D
B A C C E
```
经过binary转换之后（第一行是列序号+变量名）：

```$xslt
col_0_A col_0_B col_1_A col_1_C col_2_C col_2_B col_2_D col_3_C col_4_D col_4_E 
1 0 1 0 0 1 0 1 1 0
1 0 0 1 0 0 1 1 1 0
0 1 1 0 1 0 0 1 0 1

```

```
String inputFile = "round1_ijcai_18_train_20180301 - 副本.txt";
String someColumnDataPath = "some_columns";
String outputPath = "dummies";

int[] cols = {4,5,11,13};       //读取哪几列就把那几列组成数组传进去

//第一个参数数输入错文件，第二个参数是列分隔符，第三个参数是选择哪几列，第四个参数是输出位置
TextIO.readColumnData(inputFile, " ", cols, someColumnDataPath);

//输入位置，输出位置和分隔符
TextIO.getDummies(someColumnDataPath, outputPath, " ");
```