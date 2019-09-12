package config.DTO;

import config.Entities.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TestDTO {


    public HashMap<String, LinkedList<Question>> getTestItself() {
        return testItself;
    }

    public void setTestItself(HashMap<String, LinkedList<Question>> testItself) {
        this.testItself = testItself;
    }

    public TestDTO(List<Test> input)
    {


        for (int k = 0; k < input.size(); k++) {
            String topic = "";

            LinkedList<Question> questions = new LinkedList<>();
            String[] nameAndQuestions = input.get(k).getText().split("\\*");
            topic = nameAndQuestions[0];

            String[] questionsText = nameAndQuestions[1].split("--");
            for (int i = 0; i < questionsText.length; i++) {
                int answerId = -1;
                String[] output = questionsText[i].split("-");
               String[] options = new String[output.length - 1];

               for (int j = 1; j < output.length; j++) {
                   options[j - 1] = output[j];
                   if (output[j].charAt(output[j].length() - 1) == '+') {
                       options[j - 1] = output[j].substring(0,output[j].length() - 1);
                       answerId = j - 1;
                   }
               }

                questions.add(new Question(output[0], answerId, options));
            }

            //topic = Integer.toString(k);
            testItself.put(topic,questions);
            testID.put(topic,k);
        }


    }

    private HashMap<String, LinkedList<Question>> testItself = new HashMap<>();

    public HashMap<String, Integer> getTestID() {
        return testID;
    }

    public void setTestID(HashMap<String, Integer> testID) {
        this.testID = testID;
    }

    private HashMap<String, Integer> testID = new HashMap<>();
    //private LinkedList<String> topic = new LinkedList<>();
    //private LinkedList<Question> questions = new LinkedList<>();
}
