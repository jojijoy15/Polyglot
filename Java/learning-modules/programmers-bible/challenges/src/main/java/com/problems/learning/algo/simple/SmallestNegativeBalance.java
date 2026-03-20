package com.problems.learning.algo.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmallestNegativeBalance {

    public List<String> findWorstBorrower(String[][] transactions) {

        Map<String, Integer> ledger = new HashMap<>();
        for (int i = 0; i < transactions.length; i++) {
            String borrower = transactions[i][0];
            String lender = transactions[i][1];
            int amount = Integer.parseInt(transactions[i][2]);
            ledger.put(borrower, ledger.getOrDefault(borrower, 0) - amount);
            ledger.merge(lender, amount, Integer::sum);
        }
        Integer minBalance = ledger.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
        if(minBalance < 0) {
            return ledger.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(minBalance))
                    .map(Map.Entry::getKey)
                    .toList();
        } else {
            return List.of("Nobody has a negative balance");
        }
    }
}
