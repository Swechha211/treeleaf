package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public class ATMImpl implements ATM {

    private final BankingSystem bankingSystem;
    private final EnumMap<Banknote, Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public ATMImpl(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        initializeATM();
    }

    private void initializeATM() {
        atmCashMap.put(Banknote.FIFTY_JOD, 10);
        atmCashMap.put(Banknote.TWENTY_JOD, 20);
        atmCashMap.put(Banknote.TEN_JOD, 100);
        atmCashMap.put(Banknote.FIVE_JOD, 100);
    }

    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount)
            throws InsufficientFundsException, NotEnoughMoneyInATMException {
        BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);
        if (amount.compareTo(accountBalance) > 0) {
            throw new InsufficientFundsException("Account does not have enough funds");
        }

        BigDecimal totalAvailable = calculateTotalAvailable();
        if (amount.compareTo(totalAvailable) > 0) {
            throw new NotEnoughMoneyInATMException("Not enough money in the ATM");
        }

        if (!isAmountAvailableInATM(amount)) {
            throw new NotEnoughMoneyInATMException("Not enough money in the ATM");
        }

        bankingSystem.debitAccount(accountNumber, amount);
        return distributeBanknotes(amount);
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }

    private BigDecimal calculateTotalAvailable() {
        BigDecimal total = BigDecimal.ZERO;
        for (Entry<Banknote, Integer> entry : atmCashMap.entrySet()) {
            total = total.add(entry.getKey().getValue().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return total;
    }

    private boolean isAmountAvailableInATM(BigDecimal amount) {
        return amount.remainder(Banknote.FIVE_JOD.getValue()).compareTo(BigDecimal.ZERO) == 0 &&
                amount.compareTo(calculateTotalAvailable()) <= 0;
    }

    private List<Banknote> distributeBanknotes(BigDecimal amount) {
        List<Banknote> banknotes = new ArrayList<>();
        for (Banknote banknote : Banknote.values()) {
            int count = amount.divide(banknote.getValue(), 0, BigDecimal.ROUND_DOWN).intValue();
            int availableCount = Math.min(count, atmCashMap.get(banknote));
            atmCashMap.put(banknote, atmCashMap.get(banknote) - availableCount);
            amount = amount.subtract(banknote.getValue().multiply(BigDecimal.valueOf(availableCount)));
            for (int i = 0; i < availableCount; i++) {
                banknotes.add(banknote);
            }
        }
        return banknotes;
    }
}
