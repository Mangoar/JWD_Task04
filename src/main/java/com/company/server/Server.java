package com.company.server;

import com.company.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    Parser fileParser;

    public static final int PORT = 4444;
    final  static Logger logger = Logger.getLogger(String.valueOf(Server.class));

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server().runServer();
    }

    public void runServer() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server up and ready for connections...");
        Socket socket = serverSocket.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        Message message = (Message) objectInputStream.readObject();
        fileParser = new Parser();
//        System.out.println(message.getCode());
//        for (String str : message.getMessageBody()){
//            System.out.println(str);
//        }

        while (message.getCode() != 0 | message.getMessageBody()!=null) {
            int messageCode = message.getCode();
            List<String> messageBody = message.getMessageBody();
            List<String> resultList = new ArrayList<>();
            switch (messageCode) {
                case 0:{
                    fileParser.setFileRows(messageBody);
                    fileParser.clearCode();
                    fileParser.getAllSentances();
                    fileParser.getAllWords();
                    break;
                }
                case 2:{
                    resultList = fileParser.function2();
                    break;
                }
                case 3:{
                    resultList = fileParser.function3();
                    break;
                }
            }
            message.setMessageBody(resultList);
            objectOutputStream.writeObject(message);
            message = (Message) objectInputStream.readObject();
        }



//        message = (Message) objectInputStream.readObject();
//        System.out.println("Server got from client:");
//        System.out.println(message.getCode());
//        for (String row : message.getMessageBody()) {
//            System.out.println(row);
//        }
//
//        doSomething(message);
//        objectOutputStream.writeObject(message);

        socket.close();
    }

    private void doSomething(Message message) {
        message.addStringToList("You go fuck your self, right now! Ok?");
    }

}
