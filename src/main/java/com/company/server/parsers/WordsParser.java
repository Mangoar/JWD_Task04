package com.company.server.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsParser {

    private List<String> textSentances;

    private static final WordsParser instance = new WordsParser();

    private WordsParser() {}

    public WordsParser(List<String> textSentances) {
        this.textSentances = textSentances;
    }

    public List<String> getRows(){
        List<String> textWords = new ArrayList<>();
        Pattern rusWordPattern = Pattern.compile("([А-я]+)");
        Pattern rusDoubledWordPattern = Pattern.compile("[А-я]+-[А-я]+");
        Matcher rusWordMatcher;
        Matcher rusDoubledWordMatcher;
        for (String sentance : textSentances) {
            String[] words = sentance.split("[\\,\\:\\;\\s]");
            for (String word : words) {
                rusWordMatcher = rusWordPattern.matcher(word);
                rusDoubledWordMatcher = rusDoubledWordPattern.matcher(word);
                if (rusWordMatcher.matches() || rusDoubledWordMatcher.matches()) {
                    textWords.add(word);
                }
            }
        }
        return textWords;
    }


    public static WordsParser getInstance() {
        return instance;
    }

}
