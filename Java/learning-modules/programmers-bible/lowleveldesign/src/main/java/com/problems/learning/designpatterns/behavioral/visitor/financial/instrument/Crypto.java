package com.problems.learning.designpatterns.behavioral.visitor.financial.instrument;

import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

public class Crypto implements FinancialInstrument {

    private final String name;
    private final double purchasePrice;     // price per coin at purchase
    private final double currentPrice;      // price per coin now
    private final double coins;
    private final double volatilityIndex;   // 0.0 - 1.0 (higher = riskier)
    private final int holdingDays;

    public Crypto(String name, double purchasePrice, double currentPrice,
                  double coins, double volatilityIndex, int holdingDays) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.coins = coins;
        this.volatilityIndex = volatilityIndex;
        this.holdingDays = holdingDays;
    }

    @Override
    public void accept(PortfolioVisitor visitor) {
        visitor.visit(this);   // double dispatch — tells visitor "I am Crypto"
    }

    @Override
    public double getCurrentValue() {
        return currentPrice * coins;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getCoins() {
        return coins;
    }

    public double getVolatilityIndex() {
        return volatilityIndex;
    }

    public int getHoldingDays() {
        return holdingDays;
    }

    @Override
    public String getName() {
        return name;
    }
}