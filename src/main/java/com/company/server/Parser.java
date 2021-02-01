package com.company.server;

import com.company.server.parsers.ClearCodeParser;
import com.company.server.parsers.QuestionParser;
import com.company.server.parsers.SentancesParser;
import com.company.server.parsers.WordsParser;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private List<String> fileRows;
    private List<String> clearedFileRows;
    private List<String> textSentances;
    private List<String> textWords;
    private List<String> questionSentances;

    final  static Logger logger = Logger.getLogger(String.valueOf(Server.class));

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

    public List<String> getAllSentanceWords(String sentance) {
        List<String> wordsList = new ArrayList<>();
        String[] words = sentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            if (!word.trim().isBlank()) {
                wordsList.add(word);
            }
        }
        return wordsList;
    }

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
        SortedSet<Integer> keys = new TreeSet<>(sentancesMap.keySet());
        for (int key : keys) {
            List<String> entryList = sentancesMap.get(key);
            resultList.add("Sentences with " + key + " words:");
            for (String entryRow : entryList) {
                resultList.add(entryRow);
            }
        }

        return resultList;
    }

    public List<String> function3() {
        List<String> resultList = new ArrayList<>();
        List<String> firstSentanceWords = new ArrayList<>();
        String firstSentance = textSentances.get(0);
        String[] words = firstSentance.split("[\\,\\:\\;\\s]");
        for (String word : words) {
            if (!word.trim().isBlank()) {
                firstSentanceWords.add(word);
            }
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
        Pattern rusWordPattern = Pattern.compile("([А-я]+)");
        Pattern rusDoubledWordPattern = Pattern.compile("[А-я]+-[А-я]+");
        Matcher rusWordMatcher;
        Matcher rusDoubledWordMatcher;

        resultList.add("Words from question sentances of given length:");
        for (String sentance : questionSentances) {
            List<String> sentanceWords = getAllSentanceWords(sentance);
            for (String x : sentanceWords) {
                wordsSet.add(x);
            }
        }
        for (String word : wordsSet) {
            word.trim();
            word.replaceAll("[\\.\\(\\)\"]", "");

            if (word.length() == intWordLength) {
                rusWordMatcher = rusWordPattern.matcher(word);
                rusDoubledWordMatcher = rusDoubledWordPattern.matcher(word);
                if (rusWordMatcher.matches() || rusDoubledWordMatcher.matches()) {
                    resultList.add(word);
                }
            }
        }
        return resultList;
    }

    public List<String> function5() {
        List<String> resultList = new ArrayList<>();
        resultList.add("Editted sentances:");
        for (String sentance : textSentances) {
            try {
                List<String> sentanceWords = getAllSentanceWords(sentance);
                String firstWord = sentanceWords.get(0);
                String lastWord = sentanceWords.get(sentanceWords.size() - 1);
                String newSentance = sentance.replaceFirst(lastWord, firstWord);
                resultList.add(newSentance.replaceFirst(firstWord, lastWord));
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException){
                logger.info("IndexOutOfBoundsException occured!");
            }
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
        Set<String> setWithVowelsWords = new HashSet<>();
        resultList.add("Sorted words:");
        for (String word : textWords) {
            word = word.toLowerCase(Locale.ROOT);
            char[] ch = word.toCharArray();
            if (ch.length > 1) {
                if (ch[0] == 'у' || ch[0] == 'е' || ch[0] == 'ы' || ch[0] == 'а' || ch[0] == 'о' || ch[0] == 'э' || ch[0] == 'я' || ch[0] == 'и' || ch[0] == 'ю') {
                    String newWord = word;
                    newWord = newWord + ch[0];
                    newWord = newWord.substring(1);
                    setWithVowelsWords.add(newWord);
                }
            }
        }
        TreeSet<String> sortedWordsTreeSet = new TreeSet();
        sortedWordsTreeSet.addAll(setWithVowelsWords);

        for (String str : sortedWordsTreeSet) {
            char[] ch = str.toCharArray();
            str = ch[ch.length - 1] + str;
            str = str.substring(0, str.length() - 1);
            resultList.add(str);
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

    public List<String> function10(List<String> words) {
        List<String> resultList = new ArrayList<>();

        Map<Integer, List<String>> wordsMap = new HashMap<>();

        for (String word : words) {
            int counter = 0;
            for (String sentance : textSentances) {
                if (sentance.toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))) {
                    counter++;
                }
            }
            if (!wordsMap.containsKey(counter)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(word);
                wordsMap.put(counter, wordsList);
            } else {
                List<String> wordsList = wordsMap.get(counter);
                wordsList.add(word);
            }
        }
        Map<Integer, List<String>> wordsTreeMap = new TreeMap<>(Collections.reverseOrder());
        wordsTreeMap.putAll(wordsMap);

        for (Map.Entry<Integer, List<String>> entry : wordsTreeMap.entrySet()) {
            resultList.add("Words with " + entry.getKey() + " entries in sentances:");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
        }

        return resultList;
    }

    public List<String> function11(String symbol) {
        List<String> resultList = new ArrayList<>();

        Matcher matcher;
        String regex = symbol + ".+" + symbol;

        resultList.add("Sentances with deleted substring:");
        for (String sentance : textSentances) {

            int maxLength = 0;

            matcher = Pattern.compile(regex).matcher(sentance);
            List<String> entries = new ArrayList<>();

            while (matcher.find()) {
                entries.add(matcher.group(0));
                int length = matcher.group(0).length();
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
        resultList.add("Words that remain:");
        for (String word : textWords) {
            boolean remain = false;
            char firstChar = word.toLowerCase(Locale.ROOT).charAt(0);
            char[] vowels = new char[]{'а', 'е', 'и', 'о', 'у', 'ы', 'ё', 'ю', 'э', 'я'};
            for (char vowel : vowels) {
                if (firstChar == vowel) {
                    remain = true;
                } else {
                    if (word.length() != deleteLength) {
                        remain = true;
                    }
                }
            }
            if (remain) {
                resultList.add(word);
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
            if (!wordsMap.containsKey(counter)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(word);
                wordsMap.put(counter, wordsList);
            } else {
                List<String> wordsList = wordsMap.get(counter);
                wordsList.add(word);
            }
        }
        Map<Integer, List<String>> wordsTreeMap = new TreeMap<>(Collections.reverseOrder());
        wordsTreeMap.putAll(wordsMap);

        for (Map.Entry<Integer, List<String>> entry : wordsTreeMap.entrySet()) {
            resultList.add("Words with " + entry.getKey() + " entries of " + symbol + ":");
            for (String entryRow : entry.getValue()) {
                resultList.add(entryRow);
            }
        }

        return resultList;
    }

    public List<String> function14() {
        List<String> resultList = new ArrayList<>();
        StringBuilder fileRow = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (String row : clearedFileRows) {
            fileRow.append(row);
        }

//        for (int i = 0; i < fileRow.length(); i++) {
//            if ((i + 1) < fileRow.length()) {
//                if (fileRow.charAt(i) == fileRow.charAt(i + 1)) {
//                    result.append(fileRow.charAt(i));
//                } else if ((i - 1) >= 0 && fileRow.charAt(i) == fileRow.charAt(i - 1)) {
//                    result.append(fileRow.charAt(i));
//                }
//            } else {
//                if (fileRow.charAt(i) == fileRow.charAt(i - 1)) {
//                    result.append(fileRow.charAt(i));
//                }
//            }
//        }
//        List<String> results = Arrays.asList(result.toString().split("[\\s]"));
//        int largestString = results.get(0).length();
//        int index = 0;
//
//        for(int i = 0; i < results.size(); i++)
//        {
//            if(results.get(i).length() > largestString)
//            {
//                largestString = results.get(i).length();
//                index = i;
//            }
//        }
//        resultList.add(results.get(index));
        return resultList;
    }

    public List<String> function15(String type) {
        List<String> resultList = new ArrayList<>();
        Map<String, String> wordsMap = new HashMap<>();
        if (type.equalsIgnoreCase("first")) {
            for (String word : textWords) {
                String firstSymbol = word.substring(0, 1);
                String newWord = firstSymbol.concat(word.replace(firstSymbol, ""));
                wordsMap.put(word, newWord);
            }
            resultList.add("Words with and their editted variant:");
            for (Map.Entry<String, String> entry : wordsMap.entrySet()) {
                resultList.add(entry.getKey() + " -> " + entry.getValue());
            }
        } else if (type.equalsIgnoreCase("last")) {
            for (String word : textWords) {
                String newWord;
                if (word.length() > 2) {
                    String lastSymbol = word.substring(word.length() - 1);
                    newWord = word.replace(lastSymbol, "").concat(lastSymbol);
                } else {
                    newWord = word;
                }
                wordsMap.put(word, newWord);
            }
            resultList.add("Words with and their editted variant:");
            for (Map.Entry<String, String> entry : wordsMap.entrySet()) {
                resultList.add(entry.getKey() + " -> " + entry.getValue());
            }
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


        String[] allWords = sentenceForEdit.split("[,:;\\s]");
        String resultString = "";
        for (String str : allWords) {
            if (str.length() == intWordLength) {
                str = substring;
            }
            resultString = resultString.concat(str).concat(" ");
        }
        resultList.add("Sentence after edit:");
        resultList.add(resultString);


        return resultList;
    }


}
