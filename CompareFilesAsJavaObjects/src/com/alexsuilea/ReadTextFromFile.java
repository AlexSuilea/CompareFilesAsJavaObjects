package com.alexsuilea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadTextFromFile {

    public static List<TextFile> extractTextToList(Path path) {

        List<TextFile> textFileList = new ArrayList<>();

        removeEmptyLines(path);

        try {
            BufferedReader bf = Files.newBufferedReader(path);
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = bf.readLine()) != null) {
                if (line.startsWith("agenda-group") || line.startsWith("import")) {
                    continue;
                }

                if (line.startsWith("start")) {
                    TextFile textFile = new TextFile();
                    textFileList.add(textFile);
                    textFile.setTextTitle(line.substring(7, line.length() - 1));
                    content.append(line).append("\n");
                } else if (line.startsWith("end")) {
                    content.append(line).append("\n");
                    textFileList.get(textFileList.size() - 1).setTextContent(content.toString());
                    content = new StringBuilder();
                } else {
                    content.append(line).append("\n");
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return textFileList;
    }

    private static void removeEmptyLines(Path OldFile) {
        Scanner file;
        PrintWriter writer;
        File file1 = new File(OldFile.toUri());
        File file2 = new File("newFile.txt");

        try {
            file = new Scanner(file1);
            writer = new PrintWriter(file2);

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }

            file.close();
            writer.close();

            file1.delete();
            file2.renameTo(file1);

        } catch (FileNotFoundException ex) {
            System.out.println("No file found");
        }
    }
}





