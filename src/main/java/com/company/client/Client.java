package com.company.client;

import com.company.Message;
import com.company.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    final static Logger logger = Logger.getLogger(String.valueOf(Client.class));

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", Server.PORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Message message;
            Message returnMessage;
            int command;
            try {
                while (!socket.isClosed()) {
                    printMenu();
                    String stringComand = "";
                    while (!isInteger(stringComand)) {
                        System.out.println("Enter function number");
                        stringComand = reader.readLine();
                    }
                    command = Integer.parseInt(stringComand);
                    switch (command) {
                        case 0: {
                            FileReader fileReader = new FileReader(askForFile());
                            message = new Message(fileReader.readFileAllRows());
                            objectOutputStream.writeObject(message);
                            objectOutputStream.flush();
                            break;
                        }
                        case 1:
                        case 2:
                        case 3:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 14:{
                            message = new Message(command);
                            objectOutputStream.writeObject(message);
                            break;
                        }
                        case 15: {
                            String type = "";
                            while (!type.equalsIgnoreCase("last") && !type.equalsIgnoreCase("first")) {
                                System.out.println("first or last?");
                                type = reader.readLine();
                            }
                            message = new Message(command);
                            message.addStringToList(type);
                            objectOutputStream.writeObject(message);
                            break;
                        }
                        case 10: {
                            String wordAmount = "";
                            while (!isInteger(wordAmount)) {
                                System.out.println("Enter the word amount:");
                                wordAmount = reader.readLine();
                            }
                            message = new Message(command);
                            for (int i=0; i<Integer.parseInt(wordAmount); i++){
                                System.out.println("Enter the word:");
                                String word = reader.readLine();
                                message.addStringToList(word);
                            }
                            objectOutputStream.writeObject(message);
                            objectOutputStream.flush();
                            break;
                        }
                        case 4:
                        case 12: {
                            String wordLength = "";
                            while (!isInteger(wordLength)) {
                                System.out.println("Enter the word length:");
                                wordLength = reader.readLine();
                            }
                            message = new Message(command);
                            message.addStringToList(wordLength);
                            objectOutputStream.writeObject(message);
                            objectOutputStream.flush();
                            break;
                        }
                        case 9:
                        case 11:
                        case 13: {
                            String symbol = "";
                            while (symbol.length() != 1) {
                                System.out.println("Enter the symbol:");
                                symbol = reader.readLine();
                            }
                            message = new Message(command);
                            message.addStringToList(symbol);
                            objectOutputStream.writeObject(message);
                            objectOutputStream.flush();

                            break;
                        }
                        case 16: {
                            String wordLength = "";
                            while (!isInteger(wordLength)) {
                                System.out.println("Enter the word length:");
                                wordLength = reader.readLine();
                            }
                            System.out.println("Enter the substring:");
                            String substring = reader.readLine();
                            message = new Message(command);
                            message.addStringToList(wordLength);
                            message.addStringToList(substring);
                            objectOutputStream.writeObject(message);
                            objectOutputStream.flush();
                            break;
                        }
                        default: {
                            System.out.println("There is no such command - program exit;");
                            objectOutputStream.close();
                            objectInputStream.close();
                            socket.close();
                        }

                    }
                    returnMessage = (Message) objectInputStream.readObject();
                    System.out.println("Client got from server:" + returnMessage);

                }
            } catch (ClassNotFoundException classNotFoundException) {
                logger.info("ClassNotFoundException occurred!");
            }


        } catch (IOException ioException) {
            logger.info("IO Exception occurred!");
        }
    }


    public static String askForFile() {
        System.out.println("Enter file name without extension from resources folder:");
        Scanner input = new Scanner(System.in);
        String fileName = input.next();
        return fileName;
    }

    public static void printMenu() {
        StringBuilder menuString = new StringBuilder();
        menuString.append("Choose one of functions from the list below:" + "\n");
        menuString.append("0. Отправить на сервер файл из папки resources" + "\n");
        menuString.append("1. Найти наибольшее количество предложений текста, в которых есть одинаковые слова." + "\n");
        menuString.append("2. Вывести все предложения заданного текста в порядке возрастания количества слов в каждом из них" + "\n");
        menuString.append("3. Найти такое слово в 1ом предложении, которого нет ни в одном из остальных предложений" + "\n");
        menuString.append("4. Во всех вопросительных предложениях текста найти и напечатать без повторений слова заданной длины." + "\n");
        menuString.append("5. В каждом предложении текста поменять местами первое слово с последним не меняя длины" + "\n");
        menuString.append("6. Напечатать слова текста в алфавитном порядке по первой букве. Слова, начинающиеся с новой буквы, печатать с красной строки." + "\n");
        menuString.append("7. Рассортировать слова текста по возрастанию доли гласных гласных букв (отношение количества к общему количеству букв в слове). " + "\n");
        menuString.append("8. Слова текста, начинающиеся с гласных букв, рассортировать в алфавитном порядке по первой согласной букве слова." + "\n");
        menuString.append("9. Все слова текста рассортировать по возрастанию количества заданной буквы в слове. Слова с одинаковым количеством букв расположить в алфавитном порядке." + "\n");
        menuString.append("10. Существует текст и список слов. Для каждого слова из заданного списка найти, сколько раз оно встречается в каждом предложении, и рассортировать слова по\n" +
                "убыванию общего количества вхождений." + "\n");
        menuString.append("11. В каждом предложении исключить подстроку максимальной длины, начинающуюся и заканчивающуюся заданными символами" + "\n");
        menuString.append("12. Из текста удалить все слова заданной длины, начинающиеся на согласную букву." + "\n");
        menuString.append("13. Отсортировать слова в тексте по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту" + "\n");
        menuString.append("14. В заданном тексте найти подстроку максимальной длины, являющуюся палиндромом, т.е. читающуюся слева направо и справа налево одинаково." + "\n");
        menuString.append("15. Преобразовать каждое слово в тексте, удалив из него все последующие вхождения первой буквы этого слова" + "\n");
        menuString.append("16. В некотором предложении текста слова заданной длины заменить подстрокой, длина которой может не совпадать с длиной слова. указанной" + "\n");
        menuString.append("Будьте внимательны! Если вы введете что-то кроме номера функции из списка, программа будет завершена" + "\n");
        System.out.println(menuString);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            logger.info("NumberFormatException occurred!");
            return false;
        } catch (NullPointerException e) {
            logger.info("NullPointerException occurred!");
            return false;
        }
        return true;
    }


}
