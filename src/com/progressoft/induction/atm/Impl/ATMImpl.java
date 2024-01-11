package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMImpl implements ATM {
	
    

	private int fiftyJODCount = 10;
    private int twentyJODCount = 20;
    private int tenJODCount = 100;
    private int fiveJODCount = 100;

    private final BankingSystemImpl bankingSystem=new BankingSystemImpl();
    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        // Your code here
    	
    	
//        public ATMImpl(BankingSystem bankingSystem) {
//            this.bankingSystem = bankingSystem;
//        }

        @Override
        public List<Banknote> withdraw(String accountNumber, BigDecimal amount) throws InsufficientFundsException, NotEnoughMoneyInATMException {
            BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);
            if (amount.compareTo(accountBalance) > 0) {
                throw new InsufficientFundsException("Account does not have enough funds");
            }

            BigDecimal totalAvailable = calculateTotalAvailable();
            if (amount.compareTo(totalAvailable) > 0) {
                throw new NotEnoughMoneyInATMException("ATM does not have enough funds");
            }

            if (!isAmountAvailableInATM(amount)) {
                throw new NotEnoughMoneyInATMException("ATM does not have the requested banknotes");
            }

            return distributeBanknotes(amount);
        }

        @Override
        public BigDecimal checkBalance(String accountNumber) {
            return bankingSystem.getAccountBalance(accountNumber);
        }

        private BigDecimal calculateTotalAvailable() {
            BigDecimal fiftyTotal = Banknote.FIFTY_JOD.getValue().multiply(BigDecimal.valueOf(fiftyJODCount));
            BigDecimal twentyTotal = Banknote.TWENTY_JOD.getValue().multiply(BigDecimal.valueOf(twentyJODCount));
            BigDecimal tenTotal = Banknote.TEN_JOD.getValue().multiply(BigDecimal.valueOf(tenJODCount));
            BigDecimal fiveTotal = Banknote.FIVE_JOD.getValue().multiply(BigDecimal.valueOf(fiveJODCount));
            return fiftyTotal.add(twentyTotal).add(tenTotal).add(fiveTotal);
        }

        private boolean isAmountAvailableInATM(BigDecimal amount) {
            return amount.remainder(Banknote.FIVE_JOD.getValue()).equals(BigDecimal.ZERO) &&
                    amount.compareTo(calculateTotalAvailable()) <= 0;
        }

        private List<Banknote> distributeBanknotes(BigDecimal amount) {
            List<Banknote> banknotes = new ArrayList<>();
            while (amount.compareTo(Banknote.FIFTY_JOD.getValue()) >= 0 && fiftyJODCount > 0) {
                banknotes.add(Banknote.FIFTY_JOD);
                fiftyJODCount--;
                amount = amount.subtract(Banknote.FIFTY_JOD.getValue());
            }
            while (amount.compareTo(Banknote.TWENTY_JOD.getValue()) >= 0 && twentyJODCount > 0) {
                banknotes.add(Banknote.TWENTY_JOD);
                twentyJODCount--;
                amount = amount.subtract(Banknote.TWENTY_JOD.getValue());
            }
            while (amount.compareTo(Banknote.TEN_JOD.getValue()) >= 0 && tenJODCount > 0) {
                banknotes.add(Banknote.TEN_JOD);
                tenJODCount--;
                amount = amount.subtract(Banknote.TEN_JOD.getValue());
            }
            while (amount.compareTo(Banknote.FIVE_JOD.getValue()) >= 0 && fiveJODCount > 0) {
                banknotes.add(Banknote.FIVE_JOD);
                fiveJODCount--;
                amount = amount.subtract(Banknote.FIVE_JOD.getValue());
            }
            return banknotes;
        }
       
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}
