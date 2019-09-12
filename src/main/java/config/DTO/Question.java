package config.DTO;

import java.util.LinkedList;

public class Question {
    private int id;
    private String quest;
    private int numOfRightAnswer;
    private String[] options;

    public Question(String quest, int numOfRightAnswer, String[]options) {

        this.quest = quest;
        this.numOfRightAnswer = numOfRightAnswer;
        this.options = options;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getNumOfRightAnswer() {
        return numOfRightAnswer;
    }

    public void setNumOfRightAnswer(int numOfRightAnswer) {
        this.numOfRightAnswer = numOfRightAnswer;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
