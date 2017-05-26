# HFUTUtils
这是一个工具程序集合，方便我们平时处理数据。针对文本处理的内容较多。

参考用法：http://www.datalearner.com/blog/1051494253501911

-----------使用方法-----------

可以直接看源码文件，也可以直接下载jar包引入到工程中。注意，本项目使用jdk8+。使用Maven方式导入了Google Guava、Apache Commons等包。可以直接下载查看pom.xml文件后，添加到自己的项目中。

-----------分词用法-----------

分词集成了张华平分词 具体使用方式可参考 初始化NLPIR，传入Data文件夹和lib文件夹位置的参数，然后就可以分词了，注意授权文件的更新日期

NLPIR nlpir = new NLPIR("d:/nlpir/lib/win64/NLPIR","d:/nlpir/");

String output_text1 = nlpir.seg(input_text1, 0);

System.out.println(output_text1);
