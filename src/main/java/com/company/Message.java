package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private List<String> messageBody;

    public Message() {
        messageBody = new ArrayList<>();
    }

    public Message(int code) {
        this.code = code;
        messageBody = new ArrayList<>();
    }

    public Message(List<String> messageBody) {
        this.messageBody = messageBody;
    }

    public Message(int code, List<String> messageBody) {
        this.code = code;
        this.messageBody = messageBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(List<String> messageBody) {
        this.messageBody = messageBody;
    }

    public void addStringToList(String row){
        messageBody.add(row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return code == message.code && Objects.equals(messageBody, message.messageBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, messageBody);
    }

    @Override
    public String toString() {
        StringBuilder messageString = new StringBuilder();
        messageString.append("Message{" + "code=" + code + "\n" + "messageBody:" + "\n");
        if (messageBody != null) {
            for (String row : messageBody) {
                messageString.append(row + "\n");
            }
        }
        messageString.append("}");
        return messageString.toString();
    }
}
