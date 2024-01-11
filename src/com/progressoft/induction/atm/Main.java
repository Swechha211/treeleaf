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
         String accountNumber = "123456789"; 
         BigDecimal withdrawalAmount = new BigDecimal("100.0"); 
         try {
             List<Banknote> banknotes = atm.withdraw(accountNumber, withdrawalAmount);
             System.out.println("Withdrawal successful. Dispensed banknotes: " + banknotes);
         } catch (InsufficientFundsException e) {
             System.out.println("Insufficient funds in the account.");
         } catch (NotEnoughMoneyInATMException e) {
             System.out.println("Not enough money in the ATM.");
         } catch (AccountNotFoundException e) {
             System.out.println("Account not found.");
         }
         
         // Example: Check Balance operation
         String checkBalanceAccountNumber = "123456789"; // Replace with the account number to check balance

         BigDecimal balance = atm.checkBalance(checkBalanceAccountNumber);
         System.out.println("Account balance: " + balance);
    }
}
