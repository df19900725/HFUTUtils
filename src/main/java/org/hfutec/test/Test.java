package org.hfutec.test;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

  public static void main(String[] args) {

    String str = "#!See How To·ï¾ ï¾ ï¾ Create documents more efficiently using a new results-oriented interface·ï¾ ï¾ ï¾ Use formatting, editing, reviewing, and publishing tools to create documents in print and online·ï¾ ï¾ ï¾ Create great-looking documents faster using themes, styles, and templates·ï¾ ï¾ ï¾ Organize information and add impact with clip art, SmartArt diagrams, tables, and charts·ï¾ ï¾ ï¾ Create customized letters, labels, and envelopes·ï¾ ï¾ ï¾ Use the Full Reading view to comfortably read documents on screen·ï¾ ï¾ ï¾ Use the Outline and Draft views to develop your documents·ï¾ ï¾ ï¾ Use Groove and SharePoint Team Services to collaborate and share documents and information·ï¾ ï¾ ï¾ Prepare for the Microsoft Certified Applications Specialists examThis book uses real-world examples to give you a context in which to use the task. This book also includes workshops to help you put together individual tasks into projects. The Word example files that you need for project tasks are available at www.perspection.comPerspection has written and produced books on a variety of computer softwareï¾\u0096including Microsoft Office 2003 and XP, Microsoft Windows XP, Apple Mac OS X Panther, Adobe Photoshop CS2, Macromedia Flash 8, Macromedia Director MX, and Macromedia Fireworksï¾\u0096and Web publishing. In 1991, after working for Apple Computer and Microsoft, Steve Johnson founded Perspection, Inc. Perspection is committed to providing information and training to help people use software more effectively. Perspection has written more than 80 computer books, and sold more than 5 million copies.This courseware meets the objectives for the Microsoft Certified Application Specialist (MCAS). Upon completion of this courseware, you may be prepared to take the exam for MCAS qualification. To learn more about becoming a Microsoft Certified Application Specialist, visit www.microsoft.com.Introductionï¾ ï¾ ï¾ ï¾ xviiChapter 1ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Getting Started with Wordï¾ ï¾ ï¾ ï¾ ï¾ Chapter 2ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Working with Simple Documentsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 3ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Formatting Documentsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 4ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Using Templates, Styles, and Themesï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 5ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Adding Graphics and Multimedia to Documentsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 6ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Adding Tables and Charts to Documentsï¾ ï¾ ï¾ ï¾ ï¾ Chapter 7ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Creating Desktop Publishing Documentsï¾ ï¾ ï¾ ï¾ ï¾ Chapter 8ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Working with Long Documentsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 9ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Working with Technical Documentsï¾ Chapter 10ï¾ ï¾ ï¾ ï¾ ï¾ Creating Mail Merge Documentsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 11ï¾ ï¾ ï¾ ï¾ ï¾ Proofing and Printing Documentsï¾ ï¾ ï¾ ï¾ ï¾ Chapter 12ï¾ ï¾ ï¾ ï¾ ï¾ Publishing Documents on the Webï¾ ï¾ ï¾ Chapter 13ï¾ ï¾ ï¾ ï¾ ï¾ Protecting and Securing DocumentsChapter 14ï¾ ï¾ ï¾ ï¾ ï¾ Reviewing and Sharing Documentsï¾ ï¾ Chapter 15ï¾ ï¾ ï¾ ï¾ ï¾ Sharing Information Between Programsï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 16ï¾ ï¾ ï¾ ï¾ Customizing Wordï¾ ï¾ ï¾ Chapter 17ï¾ ï¾ ï¾ ï¾ Expanding Word Functionalityï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Chapter 18ï¾ ï¾ ï¾ ï¾ Working Together on Office Documentsï¾ ï¾ ï¾ Wï¾ ï¾ ï¾ ï¾ ï¾ ï¾ ï¾ Workshops: Putting It All Togetherï¾ ï¾ ï¾ ï¾ New FeaturesMicrosoft Certified Applications Specialistï¾ ï¾ ï¾ ï¾ ï¾ Indexï¾ ï¾ ï¾ The ACM Portal is published by the Association for Computing Machinery. Copyright © 2010 ACM, Inc. Terms of Usage Privacy Policy Code of Ethics Contact Us Useful downloads: Adobe Acrobat QuickTime Windows Media Player Real Player";
    //去掉's
    str = str.replaceAll("'s","");


    //去除尖括号内容
    str = str.replaceAll("<[^<]+>","");

    //去除latex标签即双$符号
    str = str.replaceAll("\\$\\$[^\\$]+\\$\\$","");
    str = str.replaceAll("\\$[^\\$]+\\$","");

    //去除非英文字符的结果
    str = str.replaceAll("[^A-Za-z0-9_ ]"," ").replaceAll(" +"," ");
    System.out.println(str);


    str = str.replaceAll("(http://|ftp://|https://|www){0,1}[^\\u4e00-\\u9fa5\\\\s]*?\\\\.(com|net|cn|me|tw|fr)[^\\u4e00-\\u9fa5\\\\s]*","").replace("-"," ");

    System.out.println(str.replaceAll("\\$\\$[^$]+\\$\\$","").replaceAll("[^A-Za-z0-9_ ]"," "));


    String str2 = "#!The focus on #the present 'work-in-progress' was to develop and test intervention method for facilitation of reading comprehension by blind teenagers. The study relied Vygotsky's theory of internalization, and specifically on Piotr Gal'perin's theory of stepwise formation of mental actions. The participants were helped to generate what Gal'perin called the 'orienting basis' (abstract representation) through providing them with a external physical model to structure the their comprehension activities. Theory and methods are described and results of the first case study provided.";
    System.out.println(str2);
//    str2 = " 'work-in-progress' was";
    System.out.println(replaceTwoTumple(replaceS(str2)));

//    System.out.println(str2.replaceAll("[\\w]+'s [\\w]+","(0)-(1)"));
    /*List<String[]> arrays = replaceRegxGroup(str2, "([\\S]+)'s ([\\S]+)", 2);

    for( String[] array : arrays ){
      str2 = str2.replace(array[0]+"'s "+array[1],(array[0]+"-"+array[1]).replace("'","-"));
    }
    System.out.println(str2.replaceAll(" [\\pP\\pS\\pZ][\\S][\\pP\\pS\\pZ] ",""));*/

  }

  public static String replaceTwoTumple(String str){

    String twoTuple = "\\$|\\$\\$|'|\"|#";
    str = str.replaceAll(" ["+twoTuple+"][^"+twoTuple+"]+["+twoTuple+"] "," ");
    String leftPunct = "<|\\[|\\(|{";
    String rightPunct = "}|)|]|>";
    str = str.replaceAll(" ["+leftPunct+"][^"+leftPunct+"]+["+rightPunct+"] ","");
    return str;

  }

  public static String replaceS(String str){

    List<String[]> arrays = replaceRegxGroup(str, "([\\S]+)'s ([\\S]+)", 2);

    for( String[] array : arrays ){
      str = str.replace(array[0]+"'s "+array[1],(array[0]+"-"+array[1]).replace("'","-"));
    }
    return str.replaceAll(" [\\pP\\pS\\pZ][\\S][\\pP\\pS\\pZ] ","");

  }

  private static List<String[]> replaceRegxGroup(String str, String source, int size ){

    Pattern pattern = Pattern.compile(source);
    Matcher matcher = pattern.matcher(str);

    List<String[]> outList = Lists.newArrayList();

    while ( matcher.find() ){
      String[] outArray = new String[size];
      for( int i=1; i<size+1; i++ ){
        outArray[i-1] = matcher.group(i);

      }
      outList.add(outArray);
//      System.out.println(matcher.group(1));
//      System.out.println(matcher.group(2));

    }

    return outList;

  }

}
