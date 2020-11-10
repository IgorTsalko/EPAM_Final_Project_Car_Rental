package by.epamtc.tsalko.bean.content;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {

    private static final long serialVersionUID = -4585648513279152744L;

    private int userID;
    private String titleRU;
    private String titleEN;
    private String textRU;
    private String textEN;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitleRU() {
        return titleRU;
    }

    public void setTitleRU(String titleRU) {
        this.titleRU = titleRU;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTextRU() {
        return textRU;
    }

    public void setTextRU(String textRU) {
        this.textRU = textRU;
    }

    public String getTextEN() {
        return textEN;
    }

    public void setTextEN(String textEN) {
        this.textEN = textEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return userID == news.userID &&
                Objects.equals(titleRU, news.titleRU) &&
                Objects.equals(titleEN, news.titleEN) &&
                Objects.equals(textRU, news.textRU) &&
                Objects.equals(textEN, news.textEN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, titleRU, titleEN, textRU, textEN);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", titleRU='" + titleRU + '\'' +
                ", titleEN='" + titleEN + '\'' +
                ", textRU='" + textRU + '\'' +
                ", textEN='" + textEN + '\'' +
                '}';
    }
}
