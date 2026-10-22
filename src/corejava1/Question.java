package corejava1;

public class Question {
    private String prompt;
    private String[] options;
    private int correctAnswer;

    public Question(String prompt, String[] options, int correctAnswer) {
        this.prompt = prompt;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() { return prompt; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }

    public boolean isCorrect(int userChoice) {
        return userChoice == correctAnswer;
    }
}