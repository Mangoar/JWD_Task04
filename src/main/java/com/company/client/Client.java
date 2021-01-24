package com.company.client;

import com.company.Message;
import com.company.server.Server;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Client {

    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException {
        Socket socket = new Socket("localhost", Server.PORT);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        FileReader fileReader = new FileReader(askForFile());


        Message message = new Message(fileReader.readFileAllRows());
        objectOutputStream.writeObject(message);

        String command = "0";
        while (command.matches("^[0-9]+$")){
            printMenu();
            System.out.println("Enter function number");
            Scanner input = new Scanner(System.in);
            command = input.next();

            message = new Message(Integer.parseInt(command));
            objectOutputStream.writeObject(message);
            Message returnMessage = (Message) objectInputStream.readObject();
            System.out.println("Client got from server:" + returnMessage);
            for (String row : returnMessage.getMessageBody()){
                System.out.println(row);
            }
        }

        socket.close();
    }

    public static String askForFile(){
        System.out.println("Enter file name without extension from resources folder:");
        Scanner input = new Scanner(System.in);
        String fileName = input.next();
        return fileName;
    }

    public static void printMenu(){
        StringBuilder menuString = new StringBuilder();
        menuString.append("Choose one of functions from the list below:" + "\n");
        menuString.append("2. Вывести все предложения заданного текста в порядке возрастания количества слов в каждом из них" + "\n");
        menuString.append("3. Найти такое слово в 1ом предложении, которого нет ни в одном из остальных предложений" + "\n");
        menuString.append("Будьте внимательны! Если вы введете что-то кроме номера функции из списка, программа будет завершена" + "\n");
        System.out.println(menuString);
    }


}
