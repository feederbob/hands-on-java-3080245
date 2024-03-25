package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank International!");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Account account = DataSource.getAccount(customer.getId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }

    return customer;
 
  }

  private void showMenu(Customer customer, Account account) {

    int selection = 0;

    while(selection != 4 && customer.isAuthenticated()) {
      System.out.print("=================================================");
      System.out.print("Please select one of the following options: ");
      System.out.print("1: Deposit");
      System.out.print("2: Withdraw");
      System.out.print("3: Check balance");
      System.out.print("4. Exit");
      System.out.print("=================================================");

      selection = scanner.nextInt();
      double amount = 0;

      switch (selection) {
        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scanner.nextDouble();
          account.deposit(amount);
          break;

        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          account.withdraw(amount);
          break;

        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;

        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking at Globe Bank International!");
          break;
      
        default:
          System.out.println("Invalid option. Please try again.");
          break;
      }
    }
  }
}
