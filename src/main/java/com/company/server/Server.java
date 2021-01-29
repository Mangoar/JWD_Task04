package com.company.server;

import com.company.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server {

    Parser fileParser;

    public static final int PORT = 4444;
    final  static Logger logger = Logger.getLogger(String.valueOf(Server.class));

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server().runServer();
    }

    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server up and ready for connections...");
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message message = (Message) objectInputStream.readObject();
            fileParser = new Parser();

            while (!socket.isClosed()) {
                int messageCode = message.getCode();
                List<String> messageBody = message.getMessageBody();
                List<String> resultList = new ArrayList<>();
                switch (messageCode) {
                    case 0: {
                        fileParser.setFileRows(messageBody);
                        fileParser.getAllLists();
                        resultList.add("File is uploaded. Now you can work with other functions");
                        break;
                    }
                    case 1: {
                        resultList = fileParser.function1();
                        break;
                    }
                    case 2: {
                        resultList = fileParser.function2();
                        break;
                    }
                    case 3: {
                        resultList = fileParser.function3();
                        break;
                    }
                    case 4: {
                        resultList = fileParser.function4(message.getMessageBody().get(0));
                        break;
                    }
                    case 5: {
                        resultList = fileParser.function5();
                        break;
                    }
                    case 6: {
                        resultList = fileParser.function6();
                        break;
                    }
                    case 7: {
                        resultList = fileParser.function7();
                        break;
                    }
                    case 8: {
                        resultList = fileParser.function8();
                        break;
                    }
                    case 9: {
                        resultList = fileParser.function9(message.getMessageBody().get(0));
                        break;
                    }
                    case 10: {
                        resultList = fileParser.function10(message.getMessageBody());
                        break;
                    }
                    case 11: {
                        resultList = fileParser.function11(message.getMessageBody().get(0));
                        break;
                    }
                    case 12: {
                        resultList = fileParser.function12(message.getMessageBody().get(0));
                        break;
                    }
                    case 13: {
                        resultList = fileParser.function13(message.getMessageBody().get(0));
                        break;
                    }
                    case 14: {
                        resultList = fileParser.function14();
                        break;
                    }
                    case 15: {
                        resultList = fileParser.function15(message.getMessageBody().get(0));
                        break;
                    }
                    case 16: {
                        resultList = fileParser.function16(message.getMessageBody().get(0),message.getMessageBody().get(1));
                        break;
                    }
                    default:{
                        objectInputStream.close();
                        objectInputStream.close();
                        socket.close();
                        break;
                    }
                }
                message.setMessageBody(resultList);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
                message = (Message) objectInputStream.readObject();
            }
            socket.close();
        }
        catch (IOException ioException){
            logger.info("IO Exception occurred!");
        } catch (ClassNotFoundException classNotFoundException) {
            logger.info("ClassNotFound Exception occurred!");
        }
    }

}
