package dao;

import database.DBConnection;
import model.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.TransactionDAO;

public class BankDAO {
    Connection connection = DBConnection.getConnection();
    TransactionDAO transactionDAO = new TransactionDAO();

    public void createAccount(BankAccount account) {
        String query = "INSERT INTO accounts (account_holder_name, account_type, phone, email, address, balance) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, account.getAccountHolderName());
            preparedStatement.setString(2, account.getAccountType());
            preparedStatement.setString(3, account.getPhone());
            preparedStatement.setString(4, account.getEmail());
            preparedStatement.setString(5, account.getAddress());
            preparedStatement.setDouble(6, account.getBalance());

            int rows = preparedStatement.executeUpdate();

            if(rows > 0) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("Failed to create account!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewAccounts() {
        String query = "SELECT * FROM accounts";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("--------------------------------");

                System.out.println("Account Number : " + resultSet.getInt("account_number"));
                System.out.println("Account Holder : " + resultSet.getString("account_holder_name"));
                System.out.println("Account Type : " + resultSet.getString("account_type"));
                System.out.println("Phone : " + resultSet.getString("phone"));
                System.out.println("Email : " + resultSet.getString("email"));
                System.out.println("Address : " + resultSet.getString("address"));
                System.out.println("Balance : " + resultSet.getDouble("balance"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searchAccount(int accountNumber) {
        String query = "SELECT * FROM accounts WHERE account_number = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("==================================");
                System.out.println("Account Number : " + resultSet.getInt("account_number"));
                System.out.println("Account Holder : " + resultSet.getString("account_holder_name"));
                System.out.println("Account Type : " + resultSet.getString("account_type"));
                System.out.println("Phone : " + resultSet.getString("phone"));
                System.out.println("Email : " + resultSet.getString("email"));
                System.out.println("Address : " + resultSet.getString("address"));
                System.out.println("Balance : " + resultSet.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void depositMoney(int accountNumber, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);

            int rows = preparedStatement.executeUpdate();

            if(rows > 0) {
                transactionDAO.addTransaction(accountNumber, "Deposit", amount);
                System.out.println("Money deposited successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void withdrawMoney(int accountNumber, double amount) {
        String query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);
            preparedStatement.setDouble(3,amount);

            int rows = preparedStatement.executeUpdate();

            if(rows > 0){
                transactionDAO.addTransaction(accountNumber, "Withdraw", amount);
                System.out.println("Money withdrawn successfully!");
            } else {
                System.out.println("Insufficient balance or account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void transferMoney(int senderAccountNumber, int receiverAccountNumber, double amount) {
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try {
            connection.setAutoCommit(false);

            PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setInt(2, senderAccountNumber);
            withdrawStatement.setDouble(3, amount);

            int withdrawRows = withdrawStatement.executeUpdate();

            if (withdrawRows > 0) {
                PreparedStatement depositStatement = connection.prepareStatement(depositQuery);
                depositStatement.setDouble(1, amount);
                depositStatement.setInt(2, receiverAccountNumber);

                int depositRows = depositStatement.executeUpdate();

                if(depositRows > 0) {
                    connection.commit();

                    transactionDAO.addTransaction(senderAccountNumber, "Transfer Out", amount);
                    transactionDAO.addTransaction(receiverAccountNumber, "Transfer In", amount);
                    System.out.println("Money transferred successfully!");
                } else {
                    connection.rollback();
                    System.out.println("Receiver account not found!");
                }
            } else {
                connection.rollback();
                System.out.println("Insufficient balance or sender account not found!");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void checkBalance(int accountNumber) {
        String query = "SELECT balance FROM accounts WHERE account_number = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Current Balance: Rs." + resultSet.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAccount(int accountNumber, String phone, String email, String address) {
        String query = "UPDATE accounts SET phone = ?, email = ?, address = ? WHERE account_number = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, accountNumber);

            int rows = preparedStatement.executeUpdate();

            if(rows > 0) {
                System.out.println("Account updated successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteAccount(int accountNumber) {
        String query = "DELETE FROM accounts WHERE account_number = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewTransactionHistory() {
        transactionDAO.viewTransactionHistory();
    }
}
