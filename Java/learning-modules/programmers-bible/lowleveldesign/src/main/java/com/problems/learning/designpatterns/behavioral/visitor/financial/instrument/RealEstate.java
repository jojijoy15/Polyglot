package com.problems.learning.designpatterns.behavioral.visitor.financial.instrument;

import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

public class RealEstate implements FinancialInstrument {

    private final String name;
    private final double purchasePrice;
    private final double currentMarketValue;
    private final double annualRentalIncome;
    private final double annualMaintenanceCost;
    private final String propertyType;          // "RESIDENTIAL", "COMMERCIAL"
    private final int holdingYears;

    public RealEstate(String name, double purchasePrice, double currentMarketValue,
                      double annualRentalIncome, double annualMaintenanceCost,
                      String propertyType, int holdingYears) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.currentMarketValue = currentMarketValue;
        this.annualRentalIncome = annualRentalIncome;
        this.annualMaintenanceCost = annualMaintenanceCost;
        this.propertyType = propertyType;
        this.holdingYears = holdingYears;
    }

    @Override
    public void accept(PortfolioVisitor visitor) {
        visitor.visit(this);   // double dispatch — tells visitor "I am RealEstate"
    }

    @Override
    public double getCurrentValue() {
        return currentMarketValue;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getAnnualRentalIncome() {
        return annualRentalIncome;
    }

    public double getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public int getHoldingYears() {
        return holdingYears;
    }

    @Override
    public String getName() {
        return name;
    }
}