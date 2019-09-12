package config.DTO;

import java.util.LinkedList;

public class AnswerDTO {
    private LinkedList<String> result = new LinkedList<>();

    public LinkedList<String> getResult() {
        return result;
    }

    public void setResult(LinkedList<String> result) {
        this.result = result;
    }
}
