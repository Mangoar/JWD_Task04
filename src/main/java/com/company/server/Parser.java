package com.company.server;

import com.company.server.parsers.ClearCodeParser;
import com.company.server.parsers.QuestionParser;
import com.company.server.parsers.SentancesParser;
import com.company.server.parsers.WordsParser;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.Entry.comparingByKey;

public class Parser {
    private List<String> fileRows;
    private List<String> clearedFileRows;
    private List<String> textSentances;
    private List<String> textWords;
    private List<String> questionSentances;

    public List<String> getFileRows() {
        return fileRows;
    }

    public void setFileRows(List<String> fileRows) {
        this.fileRows = fileRows;
    }

    public Parser() {
        clearedFileRows = new ArrayList<>();
        textSentances = new ArrayList<>();
        textWords = new ArrayList<>();
        questionSentances = new ArrayList<>();
    }

    public Parser(List<String> fileRows) {
        this.fileRows = fileRows;
        clearedFileRows = new ArrayList<>();
        textSentances = new ArrayList<>();
        textWords = new ArrayList<>();
        questionSentances = new ArrayList<>();
    }

    public void getAllLists() {
        ClearCodeParser clearCodeParser = new ClearCodeParser(fileRows);
        clearedFileRows = clearCodeParser.getRows();
        SentancesParser sentancesParser = new SentancesParser(clearedFileRows);
        textSentances = sentancesParser.getRows();
        WordsParser wordsParser = new WordsParser(textSentances);
        textWords = wordsParser.getRows();
        QuestionParser questionParser = new QuestionParser(clearedFileRows);
        questionSentances = questionParser.getRows();
    }

    //    public void clearCode() {
//
//        for (String row : fileRows) {
////            char firstSymbol = row.trim().charAt(0);
////            if (Character.isDigit(firstSymbol) || Character.UnicodeBlock.of(firstSymbol).equals(Character.UnicodeBlock.CYRILLIC)) {
//            String firstSymbol = row.trim().substring(0, 1);
//            Pattern patkirletter = Pattern.compile("[а-яА-Я]{1}");
//            Matcher matkirletter = patkirletter.matcher(firstSymbol);
//            Pattern patnumber = Pattern.compile("[0-9]{1}");
//            Matcher matnumber = patnumber.matcher(firstSymbol);
//            if (matkirletter.matches() || matnumber.matches()) {
//                clearedFileRows.add(row);
//            }
//        }
//    }
//
//    public void getAllSentances() {
//
//        StringBuilder fileRow = new StringBuilder();
//
//        for (String row : clearedFileRows) {
//            fileRow.append(row.trim());/*.replaceAll("[!?]","."));*/
//            System.out.println(row);
//        }
//
//        String[] sentances = fileRow.toString().split("[\\.\\!\\?]");
////        System.out.println("All sentences are :");
//        for (String sentance : sentances) {
//            textSentances.add(sentance);
//        }
//    }
//
//    public void getAllQuestionSentances() {
//
//        StringBuilder fileRow = new StringBuilder();
//
//        for (String row : clearedFileRows) {
//            fileRow.append(row.trim());
//        }
//
//        String[] sentances = fileRow.toString().split("[^.!?]*[?]");
//        for (String sentance : sentances) {
//            questionSentances.add(sentance);
//        }
//    }
//
//    public void getAllWords() {
//        Pattern rusWordPattern = Pattern.compile("([А-я]+)");
//        Pattern rusDoubledWordPattern = Pattern.compile("[А-я]+-[А-я]+");
//        Matcher rusWordMatcher;
//        Matcher rusDoubledWordMatcher;
//        for (String sentance : textSentances) {
//            String[] words = sentance.split("[\\,\\:\\;\\s]");
//            for (String word : words) {
//                rusWordMatcher = rusWordPattern.matcher(word);
//                rusDoubledWordMatcher = rusDoubledWordPattern.matcher(word);
//                if (rusWordMatcher.matches() || rusDoubledWordMatcher.matches()) {
//                    textWords.add(word);
//                }
//            }
//        }
//
//    }
//
    public List<String> getAllSentanceWords(String sentance) {
        List<String> wordsList = new ArrayList<>();
        String[] words = sentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            wordsList.add(word);
        }
        return wordsList;
    }

    //1 2 3 4 5 6 7 8 9 11 12 13 15 16 (10 14)
    public List<String> function1() {
        List<String> resultList = new ArrayList<>();

        String maxRepeatsWord = "";
        int maxRepeatsCount = 0;

        for (String word : textWords) {
            int repeatsCount = 0;
            for (String sentance : textSentances) {
                List<String> sentanceList = getAllSentanceWords(sentance.toLowerCase(Locale.ROOT));
                if (sentanceList.contains(word.toLowerCase(Locale.ROOT))) {
                    repeatsCount++;
                }
            }
            if (repeatsCount > maxRepeatsCount) {
                maxRepeatsCount = repeatsCount;
                maxRepeatsWord = word;
            }
        }

        resultList.add("Word " + maxRepeatsWord + " repeats in " + maxRepeatsCount + " sentances!");

        return resultList;
    }

    public List<String> function2() {
        List<String> resultList = new ArrayList<>();
        Map<Integer, List<String>> sentancesMap = new HashMap<>();
        int countMaxWords = 1;
        for (String sentance : textSentances) {
            int counter = getAllSentanceWords(sentance).size();
            if (counter > countMaxWords) {
                countMaxWords = getAllSentanceWords(sentance).size();
            }
            if (!sentancesMap.containsKey(counter)) {
                List<String> sentancesList = new ArrayList<>();
                sentancesList.add(sentance);
                sentancesMap.put(counter, sentancesList);
            } else {
                List<String> sentancesList = sentancesMap.get(counter);
                sentancesList.add(sentance);
            }
        }
        for (Map.Entry<Integer, List<String>> entry : sentancesMap.entrySet()) {
            resultList.add("Sentences with " + entry.getKey() + " words:");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
        }

        return resultList;
    }

    public List<String> function3() {
        List<String> resultList = new ArrayList<>();
        List<String> firstSentanceWords = new ArrayList<>();
        String firstSentance = textSentances.get(0);
//        System.out.println(firstSentance);
        String[] words = firstSentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            if (word.trim().length() != 0) {
                firstSentanceWords.add(word);
            }
//            System.out.println(word);
        }

        List<String> textSentancesForSearch = new ArrayList<>(textSentances);
        textSentancesForSearch.remove(0);

        for (String word : firstSentanceWords) {
            boolean isOnly = true;
            for (String sentance : textSentancesForSearch) {
                if (getAllSentanceWords(sentance.toLowerCase(Locale.ROOT)).contains(word.toLowerCase(Locale.ROOT))) {
                    isOnly = false;
                }
            }
            if (isOnly) {
                resultList.add("The word \"" + word + "\" is situated only in first sentance of text!");
            }
        }
        return resultList;
    }

    public List<String> function4(String wordLength) {
        List<String> resultList = new ArrayList<>();
        int intWordLength = Integer.parseInt(wordLength);
        Set<String> wordsSet = new HashSet<String>();

        resultList.add("Words from question sentances of given length:");
        for (String sentance : questionSentances) {
            List<String> sentanceWords = getAllSentanceWords(sentance);
            for (String x : sentanceWords) {
                wordsSet.add(x);
            }
        }
        for (String word : wordsSet) {
            if (word.length() == intWordLength) {
                resultList.add(word);
            }
        }
        return resultList;
    }

    public List<String> function5() {
        List<String> resultList = new ArrayList<>();
        resultList.add("Editted sentances:");
        for (String sentance : textSentances) {
            List<String> sentanceWords = getAllSentanceWords(sentance);
            String firstWord = sentanceWords.get(0);
            String lastWord = sentanceWords.get(sentanceWords.size() - 1);
            String newSentance = sentance.replaceFirst(lastWord, firstWord);
            newSentance.replaceFirst(firstWord, lastWord);

            resultList.add(newSentance);
        }


        return resultList;
    }

    public List<String> function6() {
        List<String> resultList = new ArrayList<>();
        Map<Character, List<String>> wordsMap = new HashMap<>();
        resultList.add("Words in alphabetic order::");
        for (String word : textWords) {
            char firstChar = word.toLowerCase(Locale.ROOT).charAt(0);

            if (!wordsMap.containsKey(firstChar)) {
                List<String> wordList = new ArrayList<>();
                wordList.add(word);
                wordsMap.put(firstChar, wordList);
            } else {
                List<String> wordList = wordsMap.get(firstChar);
                wordList.add(word);
            }
        }
        Map<Character, List<String>> wordsTreeMap = new TreeMap<>(wordsMap);


        for (Map.Entry<Character, List<String>> entry : wordsTreeMap.entrySet()) {
            StringBuilder resultRowBuilder = new StringBuilder("Letter " + entry.getKey() + " -> ");
            for (String mapRow : entry.getValue()) {
                resultRowBuilder.append(mapRow + " ");
            }
            resultList.add(resultRowBuilder.toString());
        }

        return resultList;
    }

    public List<String> function7() {
        List<String> resultList = new ArrayList<>();
        Map<Integer, List<String>> wordsMap = new HashMap<>();
        for (String word : textWords) {
            int vowelsCounter = 0;
            int percentage = 0;
            for (int i = 0; i < word.length(); i++) {
                char v = word.charAt(i);
                if (v == 'а' || v == 'е' || v == 'и' || v == 'о' || v == 'у' || v == 'ы' || v == 'ё' || v == 'ю' || v == 'э' || v == 'я'
                        || v == 'А' || v == 'Е' || v == 'И' || v == 'О' || v == 'У' || v == 'Ы' || v == 'Ё' || v == 'Ю' || v == 'Э' || v == 'Я') {
                    vowelsCounter++;

                }
                percentage = (int) ((double) vowelsCounter / (double) word.length() * 100);
            }
            if (!wordsMap.containsKey(percentage)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(word);
                wordsMap.put(percentage, wordsList);
            } else {
                List<String> wordsList = wordsMap.get(percentage);
                wordsList.add(word);
            }
        }

        for (Map.Entry<Integer, List<String>> entry : wordsMap.entrySet()) {
            resultList.add("Words with " + entry.getKey() + "% of vowels:");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
        }
        return resultList;
    }

    public List<String> function8() {
        List<String> resultList = new ArrayList<>();
        Map<Character, List<String>> wordsMap = new HashMap<>();
        resultList.add("Words in alphabetic order:");
        for (String word : textWords) {
            char firstChar = word.toLowerCase(Locale.ROOT).charAt(0);
            char[] vowels = new char[]{'а', 'е', 'и', 'о', 'у', 'ы', 'ё', 'ю', 'э', 'я'};
            for (char vowel : vowels) {
                if (firstChar != vowel) {
                    if (!wordsMap.containsKey(firstChar)) {
                        List<String> wordList = new ArrayList<>();
                        wordList.add(word);
                        wordsMap.put(firstChar, wordList);
                    } else {
                        List<String> wordList = wordsMap.get(firstChar);
                        wordList.add(word);
                    }
                }
            }
        }
        Map<Character, List<String>> wordsTreeMap = new TreeMap<>(wordsMap);


        for (Map.Entry<Character, List<String>> entry : wordsTreeMap.entrySet()) {
            StringBuilder resultRowBuilder = new StringBuilder("Letter " + entry.getKey() + " -> ");
            for (String mapRow : entry.getValue()) {
                resultRowBuilder.append(mapRow + " ");
            }
            resultList.add(resultRowBuilder.toString());
        }

        return resultList;
    }

    public List<String> function9(String symbol) {
        List<String> resultList = new ArrayList<>();
        Map<Integer, List<String>> wordsMap = new HashMap<>();

        char symbolToSearch = symbol.charAt(0);

        int maxCounter = 0;

        for (String word : textWords) {
            int counter = 0;
            char[] symbols = word.toCharArray();
            for (char s : symbols) {
                if (s == symbolToSearch) {
                    counter++;
                }
            }
            if (counter > maxCounter) {
                maxCounter = counter;
            }
            if (!wordsMap.containsKey(counter)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(word);
                wordsMap.put(counter, wordsList);
            } else {
                List<String> wordsList = wordsMap.get(counter);
                wordsList.add(word);
            }
//            wordsMap.put(word, counter);
        }
        Map<Integer, List<String>> wordsTreeMap = new TreeMap<>(wordsMap);

//        for (int i = 0; i < maxCounter + 1; i++) {
//            resultList.add("Words with " + i + " entries of " + symbol + ":");
        for (Map.Entry<Integer, List<String>> entry : wordsTreeMap.entrySet()) {
            List<String> wordsList = entry.getValue();
            wordsList.sort(Comparator.naturalOrder());
            resultList.add("Words with " + entry.getKey() + " entries of " + symbol + ":");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
//                if (entry.getValue() == i) {
//                    resultList.add(entry.getKey());
//                }

//            }
        }


        return resultList;
    }

    public List<String> function11(String symbol) {
        List<String> resultList = new ArrayList<>();

        Matcher matcher;
        String regex = symbol + "(.*?)" + symbol;

        resultList.add("Sentances with deleted substring:");
        for (String sentance : textSentances) {

            int maxLength = 0;

            matcher = Pattern.compile(regex).matcher(sentance);
            List<String> entries = new ArrayList<>();
            int pos = -1;
            while (matcher.find(pos + 1)) {
                pos = matcher.start();
                entries.add(matcher.group(1));
                int length = matcher.group(1).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
            for (String entry : entries) {
                if (entry.length() == maxLength) {
                    resultList.add(sentance.replaceAll(entry, ""));
                }
            }

        }

        return resultList;
    }

    public List<String> function12(String wordLength) {
        List<String> resultList = new ArrayList<>();
        int deleteLength = Integer.parseInt(wordLength);
        resultList.add("Words in that remain:");
        for (String word : textWords) {
            char firstChar = word.toLowerCase(Locale.ROOT).charAt(0);
            char[] vowels = new char[]{'а', 'е', 'и', 'о', 'у', 'ы', 'ё', 'ю', 'э', 'я'};
            for (char vowel : vowels) {
                if (firstChar != vowel) {
                    if (word.length() != deleteLength) {
                        resultList.add(word);
                    }
                }
            }
        }
        return resultList;
    }

    public List<String> function13(String symbol) {
        List<String> resultList = new ArrayList<>();
        Map<Integer, List<String>> wordsMap = new HashMap<>();

        char symbolToSearch = symbol.charAt(0);

        int maxCounter = 0;

        for (String word : textWords) {
            int counter = 0;
            char[] symbols = word.toCharArray();
            for (char s : symbols) {
                if (s == symbolToSearch) {
                    counter++;
                }
            }
            if (counter > maxCounter) {
                maxCounter = counter;
            }
//            wordsMap.put(word, counter);
            if (!wordsMap.containsKey(counter)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(word);
                wordsMap.put(counter, wordsList);
            } else {
                List<String> wordsList = wordsMap.get(counter);
                wordsList.add(word);
            }
        }
        Map<Integer, List<String>> wordsTreeMap = new TreeMap<>(wordsMap);

//        for (int i = maxCounter; i > -1; i--) {
//            resultList.add("Words with " + i + " entries of " + symbol + ":");
//            for (Map.Entry<String, Integer> entry : wordsTreeMap.entrySet()) {
//                if (entry.getValue() == i) {
//                    resultList.add(entry.getKey());
//                }
//
//            }
//        }
        for (Map.Entry<Integer, List<String>> entry : wordsTreeMap.entrySet()) {
            List<String> wordsList = entry.getValue();
            wordsList.sort(Comparator.reverseOrder());
            resultList.add("Words with " + entry.getKey() + " entries of " + symbol + ":");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
        }

        return resultList;
    }
    //!
    public List<String> function15() {
        List<String> resultList = new ArrayList<>();
        Map<String, String> wordsMap = new HashMap<>();
        for (String word : textWords) {
            String firstSymbol = word.substring(0, 1);
            String newWord = word.replace(firstSymbol, "");
            if (!newWord.isEmpty()) {
                wordsMap.put(word, newWord);
            }
        }
        resultList.add("Words with and their editted variant:");
        for (Map.Entry<String, String> entry : wordsMap.entrySet()) {
            resultList.add(entry.getKey() + " -> " + entry.getValue());
        }
        return resultList;
    }

    public List<String> function16(String wordLength, String substring) {
        List<String> resultList = new ArrayList<>();
        int intWordLength = Integer.parseInt(wordLength);

        int sentenceNumber = new Random().nextInt(textSentances.size());
        String sentenceForEdit = textSentances.get(sentenceNumber);

        resultList.add("Sentence to edit:");
        resultList.add(sentenceForEdit);

        List<String> sentenceWords = getAllSentanceWords(sentenceForEdit);
        for (String word : sentenceWords) {
            if (word.length() == intWordLength) {
                sentenceForEdit.replace(word, substring);
            }
        }
        resultList.add("Sentence after edit:");
        resultList.add(sentenceForEdit);


        return resultList;
    }
}
