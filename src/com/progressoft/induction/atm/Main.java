package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String args[]){
       // your code here
    	 BankingSystem bankingSystem = new BankingSystemImpl();

         // Instantiate the ATM with the Banking System
         ATM atm = new ATMImpl(bankingSystem);

         //  Withdrawal operation
         //  to get user input
         Scanner scanner = new Scanner(System.in);

         // to get the account number from the user
         System.out.print("Enter your account number: ");
         String accountNumber = scanner.next();

         // to get the amount to withdraw from the user
         System.out.print("Enter the amount to withdraw: ");
         BigDecimal withdrawalAmount = BigDecimal.valueOf(scanner.nextDouble());

         try {
             List<Banknote> banknotes = atm.withdraw(accountNumber, withdrawalAmount);
             System.out.println("Withdrawal successful. " );
         } catch (InsufficientFundsException e) {
             System.out.println("Insufficient funds in the account.");
         } catch (NotEnoughMoneyInATMException e) {
             System.out.println("Not enough money in the ATM.");
         } catch (AccountNotFoundException e) {
             System.out.println("Account not found.");
         }
         
         // Example: Check Balance operation
         String checkBalanceAccountNumber = accountNumber; // Replace with the account number to check balance

         BigDecimal balance = atm.checkBalance(checkBalanceAccountNumber);
         System.out.println("Account balance: " + balance);
    }
}
