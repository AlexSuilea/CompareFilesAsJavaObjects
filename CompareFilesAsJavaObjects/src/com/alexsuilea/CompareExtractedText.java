package com.alexsuilea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CompareExtractedText {

    public static void takeData() throws IOException {
        File directoryPath1 = new File("folder1");
        File directoryPath2 = new File("folder2");

        List<TextFile> mainTextFileList = new ArrayList<>();
        List<TextFile> newTextFileList = new ArrayList<>();

        File[] textFileListFromFolder1 = directoryPath1.listFiles();
        File[] textFileListFromFolder2 = directoryPath2.listFiles();

        assert textFileListFromFolder1 != null;
        for (File file : textFileListFromFolder1) {
            List<TextFile> textFileList = ReadTextFromFile.extractTextToList(Path.of(file.getPath()));
            mainTextFileList.addAll(textFileList);
        }

        assert textFileListFromFolder2 != null;
        for (File file : textFileListFromFolder2) {
            List<TextFile> textFileList = ReadTextFromFile.extractTextToList(Path.of(file.getPath()));
            newTextFileList.addAll(textFileList);
        }

        mainTextFileList.sort(Comparator.comparing(TextFile::getTextTitle));
        newTextFileList.sort(Comparator.comparing(TextFile::getTextTitle));

        Map<String, TextFile> mapFromMainTextFileList = new HashMap<>();
        mainTextFileList.forEach(textFile -> {
            mapFromMainTextFileList.put(textFile.getTextTitle(), textFile);
        });

        compareTexts(newTextFileList, mapFromMainTextFileList);
    }

    private static void compareTexts(List<TextFile> newTextFileList, Map<String, TextFile> mapFromMainTextFileList) throws IOException {
        FileWriter outputFile = new FileWriter("output/output.txt");

        int count = 0;
        StringBuilder result = new StringBuilder();

        for (TextFile textFile : newTextFileList) {
            if(mapFromMainTextFileList.containsKey(textFile.getTextTitle())){
                String textFromMainList = mapFromMainTextFileList.get(textFile.getTextTitle()).getTextContent();
                String textFromNewList = textFile.getTextContent();
                if(!textFromNewList.equals(textFromMainList)){
                    String[] textContentMain = textFromMainList.split("\n");
                    String[] textContentNew = textFromNewList.split("\n");

                    List<String> textContentMainList = new ArrayList<>(Arrays.asList(textContentMain));
                    List<String> textContentNewList = new ArrayList<>(Arrays.asList(textContentNew));

                    List<String> diffListMain = new ArrayList<>(textContentMainList);
                    diffListMain.removeAll(textContentNewList);

                    List<String> diffListNew = new ArrayList<>(textContentNewList);
                    diffListNew.removeAll(textContentMainList);

                    if(diffListMain.size() == diffListNew.size()) {
                        count++;
                        result = resultText(result, textFile, diffListMain, diffListNew);
                    }
                }
            }
        }
        outputFile.write(count + "\n" + result);
        outputFile.close();
    }

    private static StringBuilder resultText(StringBuilder result, TextFile textFile, List<String> diffListMain, List<String> diffListNew) {
        result.append("Text title: ").append(textFile.getTextTitle()).append("\n");
        for(int i = 0; i< diffListMain.size(); i++) {
            result.append("List Main: ").append(diffListMain.get(i)).append("\n");
            result.append("List New: ").append(diffListNew.get(i)).append("\n\n");
        }
        return result;
    }
}
