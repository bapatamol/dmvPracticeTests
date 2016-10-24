package model;

/**
 * Created by abapat on 4/8/2015.
 */
public class QuestionData {

    private String questionText;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String answer;
    private String attachmentLink;
    private String selectedOption;

    public String getAttachmentLink() {
        return attachmentLink;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
