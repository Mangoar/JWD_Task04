package com.company.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileReader {

    private static final String TXT_EXTENSION = ".txt";
    private String fileName;
    final static Logger logger = Logger.getLogger(String.valueOf(Client.class));

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readFileAllRows() throws IOException {

        List<String> fileRowsList = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName + TXT_EXTENSION);

        if (inputStream == null) {
            logger.info("File " + fileName + TXT_EXTENSION +" not found!");
            throw new IllegalArgumentException();

        } else {
            try (InputStreamReader streamReader =
                         new InputStreamReader(inputStream, UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        fileRowsList.add(line);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileRowsList;
    }
}
