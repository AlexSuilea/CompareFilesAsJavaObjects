package com.alexsuilea;

import java.util.Objects;

public class TextFile {
    private String textTitle;
    private String textContent;

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextFile textFile = (TextFile) o;
        return Objects.equals(textTitle, textFile.textTitle) && Objects.equals(textContent, textFile.textContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textTitle, textContent);
    }

    @Override
    public String toString() {
        return "TextFile{" +
                "textTitle=" + textTitle +
                ", textContent=\n" + textContent +
                '}';
    }
}
