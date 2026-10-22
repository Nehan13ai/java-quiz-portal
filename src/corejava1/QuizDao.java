package corejava1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDao {

    // Fetch questions from MySQL database
    public static List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();
        String query = "SELECT prompt, option1, option2, option3, option4, correct_answer FROM questions";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String prompt = rs.getString("prompt");
                String[] options = {
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4")
                };
                int correctAns = rs.getInt("correct_answer");
                list.add(new Question(prompt, options, correctAns));
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error loading questions: " + e.getMessage());
        }
        return list;
    }

    // Save score directly to MySQL database
    public static void saveScore(String name, int score, int total, double percentage, String grade) {
        String query = "INSERT INTO scores (student_name, score, total_questions, percentage, grade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, score);
            pstmt.setInt(3, total);
            pstmt.setDouble(4, percentage);
            pstmt.setString(5, grade);
            
            pstmt.executeUpdate();
            System.out.println("✅ Result successfully saved to MySQL Database!");
        } catch (SQLException e) {
            System.out.println("❌ Database Error saving score: " + e.getMessage());
        }
    }

    // Fetch Leaderboard from MySQL database
    public static void displayLeaderboard() {
        String query = "SELECT student_name, score, total_questions, percentage, grade, attempt_date FROM scores ORDER BY score DESC, percentage DESC LIMIT 10";

        System.out.println("\n==================================================");
        System.out.println("           MYSQL DATABASE LEADERBOARD             ");
        System.out.println("==================================================");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int rank = 1;
            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf("%d. Name: %s | Score: %d/%d (%.2f%%) | Grade: %s | Date: %s\n",
                        rank++,
                        rs.getString("student_name"),
                        rs.getInt("score"),
                        rs.getInt("total_questions"),
                        rs.getDouble("percentage"),
                        rs.getString("grade"),
                        rs.getTimestamp("attempt_date")
                );
            }
            if (!hasRecords) {
                System.out.println("No records found in database yet!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error reading leaderboard: " + e.getMessage());
        }
        System.out.println("==================================================");
    }
}