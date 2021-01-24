package com.company;

import com.company.client.Client;
import com.company.server.Server;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

//    public static void main(String[] args) throws IOException {
//
//        Client client = new Client();
//
//        Server server = new Server();
//        server.clearCode(client.readFileAll());
//        server.getAllSentances();
//        server.getAllWords();
////        List<String> list = server.getClearedFileRows();
////        for (String row : list) {
////            System.out.println(row);
////       }
////        List<String> list = server.clearCode(client.readFileAll());
////        for (String row : list) {
////            System.out.println(row);
////        }
//
////        List<String> list2 = server.getTextWords();
////        for (String row : list2) {
////            System.out.println(row);
////        }
//        server.function3();
//        server.function6();
//
//
//
////        Client client = new Client();
////        List<String> list = client.readFileAll();
////        for (String row : list) {
////            System.out.println(row);
////        }
//
//
////        String yourString = "eo21jüdjüqw realString ././sdf.// 121vfdv";
////        String result1 = yourString.replaceAll("[-+\\.^:;!?/,]","");
////        String result2 = yourString.replaceAll("[\\-\\+\\.\\^:,]","");
////        String  result3 = yourString.replaceAll("[^\\w\\s]","");
////        String  result4 = yourString.replaceAll("[^\\p{L}\\p{Z}]","");
////        String  result5 = yourString.replaceAll("[^A-Za-z0-9]","");
////        System.out.println(result1);
////        System.out.println(result2);
////        System.out.println(result3);
////        System.out.println(result4);
////        System.out.println(result5);
//
//
////        getWordFromString("Это моя первая С++-программа.\n" +
////                "   Если вы используете интегрированную среду разработки, то выполнить программу можно путем выбора из меню командыRun (Выполнить). Безусловно, более точные инструкции приведены в сопроводительной документации, прилагаемой к вашему компилятору. Но, как упоминалось выше, проще всегокомпилировать и выполнять приведенные в этой книге программы с помощью командной строки.\n" +
////                "   Необходимо отметить, что все эти программы представляют собой консольные приложения, а не приложения, основанные на применении окон, т.е. они выполняются в сеансе приглашения на ввод команды. При этом вам, должно быть, известно, что язык С++ не просто подходит для Windows-программирования, C++ — основной язык, применяемый в разработке Windows-приложений. Однако ни одна из программ, представленных в этой книге, не использует графического интерфейса пользователя (GUI —graphics use interface).Дело в том, что Windows — довольно сложная среда для написания программ включающая множество второстепенных тем, не связанных напрямую с языком C++ В то же время консольные приложения гораздо короче графических и лучше подходят для обучения программированию. Освоив C++, вы сможете без проблем применить свои знания в сфере созданияWindows-приложений.Построчный \"разбор полетов\"\n" +
////                "   После успешной компиляции и выполнения первого примера программы настало время разобраться в том, как она работает. Поэтому мы подробно рассмотрим каждую её строку. Итак, наша программа начинается с таких строк.\n");
////        getSentancesFromString("Это моя первая С++-программа.\n" +
////                "   Если вы используете интегрированную среду разработки, то выполнить программу можно путем выбора из меню командыRun (Выполнить). Безусловно, более точные инструкции приведены в сопроводительной документации, прилагаемой к вашему компилятору. Но, как упоминалось выше, проще всегокомпилировать и выполнять приведенные в этой книге программы с помощью командной строки.\n" +
////                "   Необходимо отметить, что все эти программы представляют собой консольные приложения, а не приложения, основанные на применении окон, т.е. они выполняются в сеансе приглашения на ввод команды. При этом вам, должно быть, известно, что язык С++ не просто подходит для Windows-программирования, C++ — основной язык, применяемый в разработке Windows-приложений. Однако ни одна из программ, представленных в этой книге, не использует графического интерфейса пользователя (GUI —graphics use interface).Дело в том, что Windows — довольно сложная среда для написания программ включающая множество второстепенных тем, не связанных напрямую с языком C++ В то же время консольные приложения гораздо короче графических и лучше подходят для обучения программированию. Освоив C++, вы сможете без проблем применить свои знания в сфере созданияWindows-приложений.Построчный \"разбор полетов\"\n" +
////                "   После успешной компиляции и выполнения первого примера программы настало время разобраться в том, как она работает. Поэтому мы подробно рассмотрим каждую её строку. Итак, наша программа " +
////                "начинается с таких строк.\n");
//    }
//
//    public String getFileString(String[] fileRows) {
//        StringBuilder fileStringBuilder = new StringBuilder();
//        for (String row : fileRows) {
//            fileStringBuilder.append(row);
//        }
//        return fileStringBuilder.toString();
//    }
//
//    public String clearFileString(String fileString) {
//        String clearedFileString = fileString.replaceAll("[-+\\.^:;!?/,]", "");
//        return clearedFileString;
//    }
//
//    public static String[] getWordFromString(String fileString) {
//        String[] words = fileString.split(" ");
//        System.out.println("Words in the given sentence are :");
//        // iterate over the words
//        for (String word : words) {
//            System.out.println(word);
//        }
//        return words;
//    }
//
//    public static String[] getSentancesFromString(String fileString) {
//        String[] sentances = fileString.split("\\.");
//        System.out.println("Sentences are :");
//        // iterate over the words
//        for (String sentance : sentances) {
//            System.out.println(sentance);
//        }
//        return sentances;
//    }
}
