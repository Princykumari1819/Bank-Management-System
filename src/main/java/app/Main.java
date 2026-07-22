package app;

import dao.BankDAO;
import model.BankAccount;

import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankDAO bankDAO = new BankDAO();

        while (true) {

            System.out.println("\n===== Bank Management System =====");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Search Account");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Transfer Money");
            System.out.println("7. Check Balance");
            System.out.println("8. Update Account");
            System.out.println("9. Delete Account");
            System.out.println("10. Transaction History");
            System.out.println("11. Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    BankAccount account = new BankAccount();

                    System.out.print("Enter Account Holder Name: ");
                    account.setAccountHolderName(scanner.nextLine());

                    System.out.print("Enter Account Type (Saving/Current): ");
                    account.setAccountType(scanner.nextLine());

                    System.out.print("Enter Phone: ");
                    account.setPhone(scanner.nextLine());

                    System.out.print("Enter Email: ");
                    account.setEmail(scanner.nextLine());

                    System.out.print("Enter Address: ");
                    account.setAddress(scanner.nextLine());

                    System.out.print("Enter Opening Balance: ");
                    account.setBalance(scanner.nextDouble());
                    scanner.nextLine();

                    bankDAO.createAccount(account);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;

                case 2:
                    bankDAO.viewAccounts();

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int accountNumber = scanner.nextInt();

                    bankDAO.searchAccount(accountNumber);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    int depositAccountNumber = scanner.nextInt();

                    System.out.print("Enter Amount to Deposit: ");
                    double depositAmount = scanner.nextDouble();

                    bankDAO.depositMoney(depositAccountNumber, depositAmount);
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 5:
                    System.out.print("Enter Account Number: ");
                    int withdrawAccountNumber = scanner.nextInt();

                    System.out.print("Enter Amount to Withdraw: ");
                    double withdrawAmount = scanner.nextDouble();

                    bankDAO.withdrawMoney(withdrawAccountNumber, withdrawAmount);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 6:
                    System.out.print("Enter Sender Account Number: ");
                    int senderAccount = scanner.nextInt();

                    System.out.print("Enter Receiver Account Number: ");
                    int receiverAccount = scanner.nextInt();

                    System.out.print("Enter Amount: ");
                    double transferAmount = scanner.nextDouble();

                    bankDAO.transferMoney(senderAccount, receiverAccount, transferAmount);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 7:
                    System.out.print("Enter Account Number: ");
                    int balanceAccountNumber = scanner.nextInt();

                    bankDAO.checkBalance(balanceAccountNumber);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 8:
                    System.out.print("Enter Account Number: ");
                    int updateAccountNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter New Phone: ");
                    String newPhone = scanner.nextLine();

                    System.out.print("Enter New Email: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("Enter New Address: ");
                    String newAddress = scanner.nextLine();

                    bankDAO.updateAccount(updateAccountNumber, newPhone, newEmail, newAddress);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;

                case 9:
                    System.out.print("Enter Account Number: ");
                    int deleteAccountNumber = scanner.nextInt();

                    bankDAO.deleteAccount(deleteAccountNumber);

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();

                    break;

                case 10:
                    bankDAO.viewTransactionHistory();

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;

                case 11:
                    System.out.println("Thank you for using Bank Management System!");
                    return;

                default:
                    System.out.println("Invalid Choice!");

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();

            }
        }
    }
}
