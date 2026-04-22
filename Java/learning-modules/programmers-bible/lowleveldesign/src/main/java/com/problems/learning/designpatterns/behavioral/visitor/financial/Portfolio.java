package com.problems.learning.designpatterns.behavioral.visitor.financial;

import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.FinancialInstrument;
import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all instruments and drives visitor traversal.
 * Portfolio doesn't know what any visitor does — it just
 * calls accept() on each instrument and lets double dispatch
 * route to the right visit() method.
 */
public class Portfolio {

    private final String ownerName;
    private final List<FinancialInstrument> instruments = new ArrayList<>();

    public Portfolio(String ownerName) {
        this.ownerName = ownerName;
    }

    public void add(FinancialInstrument instrument) {
        instruments.add(instrument);
    }

    public void accept(PortfolioVisitor visitor) {
        // Each instrument's accept() does double dispatch back to visitor
        instruments.forEach(instrument -> instrument.accept(visitor));
    }

    public String getOwnerName() {
        return ownerName;
    }
}