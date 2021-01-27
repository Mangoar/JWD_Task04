package com.company.server.parsers;

import java.util.ArrayList;
import java.util.List;

public class SentancesParser {

    private List<String> fileRows;

    private static final SentancesParser instance = new SentancesParser();

    private SentancesParser() {}

    public SentancesParser(List<String> fileRows) {
        this.fileRows = fileRows;
    }

    public List<String> getRows(){
        List<String> textSentances = new ArrayList<>();
        StringBuilder fileRow = new StringBuilder();

        for (String row : fileRows) {
            fileRow.append(row.trim());/*.replaceAll("[!?]","."));*/
            System.out.println(row);
        }

        String[] sentances = fileRow.toString().split("[\\.\\!\\?]");
//        System.out.println("All sentences are :");
        for (String sentance : sentances) {
            textSentances.add(sentance);
        }
        return textSentances;
    }


    public static SentancesParser getInstance() {
        return instance;
    }

}
