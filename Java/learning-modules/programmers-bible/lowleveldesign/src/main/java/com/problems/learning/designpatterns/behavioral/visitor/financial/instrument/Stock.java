package com.problems.learning.designpatterns.behavioral.visitor.financial.instrument;

import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

public class Stock implements FinancialInstrument {

    private final String name;
    private final double purchasePrice;
    private final double currentPrice;
    private final int shares;
    private final double dividendYield;    // annual dividend %
    private final String sector;           // e.g. "Technology", "Healthcare"
    private final int holdingDays;      // for short/long term tax distinction

    public Stock(String name, double purchasePrice, double currentPrice,
                 int shares, double dividendYield, String sector, int holdingDays) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.shares = shares;
        this.dividendYield = dividendYield;
        this.sector = sector;
        this.holdingDays = holdingDays;
    }

    @Override
    public void accept(PortfolioVisitor visitor) {
        visitor.visit(this);   // double dispatch — tells visitor "I am a Stock"
    }

    @Override
    public double getCurrentValue() {
        return currentPrice * shares;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public int getShares() {
        return shares;
    }

    public double getDividendYield() {
        return dividendYield;
    }

    public String getSector() {
        return sector;
    }

    public int getHoldingDays() {
        return holdingDays;
    }

    @Override
    public String getName() {
        return name;
    }
}