package corejava1;

import java.util.*;

public class QuizPortal {
    private static List<Question> questionBank = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        questionBank = QuizDao.getAllQuestions();

        if (questionBank.isEmpty()) {
            System.out.println("❌ Error: No questions found in the database. Check your connection.");
            return;
        }

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getValidChoice("Select an option (1-3): ", 1, 3);

            switch (choice) {
                case 1:
                    startQuizSession();
                    break;
                case 2:
                    QuizDao.displayLeaderboard();
                    break;
                case 3:
                    System.out.println("\nThank you for using the MySQL Core Java Quiz Portal. Goodbye! 👋");
                    running = false;
                    break;
            }
        }
        scanner.close();
    }

    private static void showMainMenu() {
        System.out.println("\n==================================================");
        System.out.println("        MYSQL CORE JAVA QUIZ PORTAL       ");
        System.out.println("==================================================");
        System.out.println("1. Start New Quiz");
        System.out.println("2. View Database Leaderboard");
        System.out.println("3. Exit");
        System.out.println("==================================================");
    }

    private static void startQuizSession() {
        System.out.print("\nEnter your full name: ");
        scanner.nextLine(); // clear buffer
        String userName = userName = scanner.nextLine().trim();
        if (userName.isEmpty()) userName = "Anonymous";

        // Shuffle questions randomly
        Collections.shuffle(questionBank);

        int score = 0;
        long startTime = System.currentTimeMillis();
        int totalToAsk = Math.min(5, questionBank.size());

        for (int i = 0; i < totalToAsk; i++) {
            Question q = questionBank.get(i);
            System.out.println("\n--------------------------------------------------");
            System.out.println("Q" + (i + 1) + ": " + q.getPrompt());
            System.out.println("--------------------------------------------------");

            String[] opts = q.getOptions();
            for (int j = 0; j < opts.length; j++) {
                System.out.println((j + 1) + ". " + opts[j]);
            }

            int userChoice = getValidChoice("Enter your option (1-" + opts.length + "): ", 1, opts.length);

            if (q.isCorrect(userChoice)) {
                System.out.println("-> Result: Correct! 🎉");
                score++;
            } else {
                System.out.println("-> Result: Incorrect. ❌");
                System.out.println("   The correct answer was: " + q.getCorrectAnswer() + ". " + opts[q.getCorrectAnswer() - 1]);
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000;

        displayAndSaveResults(userName, score, totalToAsk, timeTaken);
    }

    private static int getValidChoice(String promptMessage, int min, int max) {
        int choice = -1;
        System.out.print(promptMessage);
        
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.print("⚠️ Invalid choice. " + promptMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print("⚠️ Invalid input! Please enter a number. " + promptMessage);
                scanner.next();
            }
        }
        return choice;
    }

    private static void displayAndSaveResults(String name, int score, int total, long timeTaken) {
        double percentage = ((double) score / total) * 100;
        String grade = getGrade(percentage);

        System.out.println("\n==================================================");
        System.out.println("               QUIZ PERFORMANCE REPORT            ");
        System.out.println("==================================================");
        System.out.println("Candidate Name   : " + name);
        System.out.println("Questions Answered: " + total);
        System.out.println("Correct Answers  : " + score);
        System.out.println("Score Percentage : " + String.format("%.2f", percentage) + "%");
        System.out.println("Time Taken       : " + timeTaken + " seconds");
        System.out.println("Final Grade      : " + grade);
        System.out.println("==================================================");

        // Save directly to MySQL database via DAO
        QuizDao.saveScore(name, score, total, percentage, grade);
    }

    private static String getGrade(double percentage) {
        if (percentage >= 90) return "A+ (Outstanding)";
        if (percentage >= 75) return "A (Very Good)";
        if (percentage >= 60) return "B (Good)";
        if (percentage >= 40) return "C (Pass)";
        return "F (Needs Improvement)";
    }
}