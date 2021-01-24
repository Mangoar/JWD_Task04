package com.company.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private List<String> fileRows;
    private List<String> clearedFileRows;
    private List<String> textSentances;
    private List<String> textWords;

    public List<String> getFileRows() {
        return fileRows;
    }

    public void setFileRows(List<String> fileRows) {
        this.fileRows = fileRows;
    }

    public List<String> getClearedFileRows() {
        return clearedFileRows;
    }

    public void setClearedFileRows(List<String> clearedFileRows) {
        this.clearedFileRows = clearedFileRows;
    }

    public List<String> getTextSentances() {
        return textSentances;
    }

    public void setTextSentances(List<String> textSentances) {
        this.textSentances = textSentances;
    }

    public List<String> getTextWords() {
        return textWords;
    }

    public void setTextWords(List<String> textWords) {
        this.textWords = textWords;
    }

    public Parser() {
        clearedFileRows = new ArrayList<>();
        textSentances = new ArrayList<>();
        textWords = new ArrayList<>();
    }

    public Parser(List<String> fileRows) {
        this.fileRows = fileRows;
        clearedFileRows = new ArrayList<>();
        textSentances = new ArrayList<>();
        textWords = new ArrayList<>();
    }

    public void clearCode() {

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
    }

    public void getAllSentances() {

        StringBuilder fileRow = new StringBuilder();

        for (String row : clearedFileRows) {
            fileRow.append(row.trim());/*.replaceAll("[!?]","."));*/
        }

        String[] sentances = fileRow.toString().split("[\\.\\!\\?]");
//        System.out.println("All sentences are :");
        for (String sentance : sentances) {
            textSentances.add(sentance);
        }
    }

    public void getAllWords() {

//        System.out.println("All words are :");
        for (String sentance : textSentances) {
            String[] words = sentance.split("[\\,\\:\\;\\s]");
            for (String word : words) {
                textWords.add(word);
            }
        }

    }

    public List<String> getAllSentanceWords(String sentance) {
        List<String> wordsList = new ArrayList<>();
        String[] words = sentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            wordsList.add(word);
        }
        return wordsList;
    }

    public List<String> function3() {
        List<String> resultList = new ArrayList<>();
        List<String> firstSentanceWords = new ArrayList<>();
        String firstSentance = textSentances.get(0);
//        System.out.println(firstSentance);
        String[] words = firstSentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            firstSentanceWords.add(word);
//            System.out.println(word);
        }

        List<String> textSentancesForSearch = new ArrayList<>(textSentances);
        textSentancesForSearch.remove(0);

        for (String word : firstSentanceWords) {
            boolean isOnly = true;
            for (String sentance : textSentancesForSearch) {
                if (getAllSentanceWords(sentance).contains(word)) {
                    isOnly = false;
                }
            }
            if (isOnly) {
                resultList.add("The word \"" + word + "\" is situated only in first sentance of text!");
            }
        }
        return resultList;
    }

    public List<String> function2() {
        List<String> resultList = new ArrayList<>();
        Map<String, Integer> sentancesMap = new HashMap<>();
        int countMaxWords = 1;
        for (String sentance : textSentances) {
            if (getAllSentanceWords(sentance).size() > countMaxWords) {
                countMaxWords = getAllSentanceWords(sentance).size();
            }
            sentancesMap.put(sentance, getAllSentanceWords(sentance).size());
        }
        for (int i = 1; i < countMaxWords + 1; i++) {
            resultList.add("Sentences with " + i + " words:");
            for (Map.Entry<String, Integer> entry : sentancesMap.entrySet()) {
                if (entry.getValue() == i){
                    resultList.add(entry.getKey());
                }
            }
        }
        return resultList;
    }
}
