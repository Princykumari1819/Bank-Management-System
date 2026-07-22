package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionDAO {
    Connection connection = DBConnection.getConnection();

    public void addTransaction(int accountNumber, String transactionType, double amount) {
        String query = "INSERT INTO transactions(account_number, transaction_type, amount) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, transactionType);
            preparedStatement.setDouble(3, amount);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewTransactionHistory() {
        String query = "SELECT * FROM transactions ORDER BY transaction_date DESC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("----------------------------------------");
                System.out.println("Transaction ID : " + resultSet.getInt("transaction_id"));
                System.out.println("Account Number: " + resultSet.getInt("account_number"));
                System.out.println("Type : " + resultSet.getString("transaction_type"));
                System.out.println("Amount : Rs. " + resultSet.getDouble("amount"));
                System.out.println("Date : " + resultSet.getTimestamp("transaction_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
