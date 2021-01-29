package com.company.server.parsers;

import java.util.ArrayList;
import java.util.List;

public class QuestionParser {

    private List<String> fileRows;

    private static final QuestionParser instance = new QuestionParser();

    private QuestionParser() {}

    public QuestionParser(List<String> fileRows) {
        this.fileRows = fileRows;
    }

    public List<String> getRows(){
        List<String> questionSentances = new ArrayList<>();
        StringBuilder fileRow = new StringBuilder();

        for (String row : fileRows) {
            fileRow.append(row.trim());
        }

        String[] sentances = fileRow.toString().split("[^.!?]*[?]");
        for (String sentance : sentances) {
            questionSentances.add(sentance);
        }
        return questionSentances;
    }


    public static QuestionParser getInstance() {
        return instance;
    }

}
