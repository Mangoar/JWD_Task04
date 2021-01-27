package com.company.server.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClearCodeParser {

    private List<String> fileRows;

    private static final ClearCodeParser instance = new ClearCodeParser();

    private ClearCodeParser() {}

    public ClearCodeParser(List<String> fileRows) {
        this.fileRows = fileRows;
    }

    public List<String> getRows(){
        List<String> clearedFileRows = new ArrayList<>();
        for (String row : fileRows) {
//            char firstSymbol = row.trim().charAt(0);
//            if (Character.isDigit(firstSymbol) || Character.UnicodeBlock.of(firstSymbol).equals(Character.UnicodeBlock.CYRILLIC)) {
            String firstSymbol = row.trim().substring(0, 1);
            Pattern patkirletter = Pattern.compile("[а-яА-Я]{1}");
            Matcher matkirletter = patkirletter.matcher(firstSymbol);
            Pattern patnumber = Pattern.compile("[0-9]{1}");
            Matcher matnumber = patnumber.matcher(firstSymbol);
            if (matkirletter.matches() || matnumber.matches()) {
                clearedFileRows.add(row);
            }
        }
        return clearedFileRows;
    }


    public static ClearCodeParser getInstance() {
        return instance;
    }
}
