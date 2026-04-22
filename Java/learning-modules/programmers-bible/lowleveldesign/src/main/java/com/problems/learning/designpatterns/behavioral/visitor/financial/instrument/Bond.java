package com.problems.learning.designpatterns.behavioral.visitor.financial.instrument;

import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

public class Bond implements FinancialInstrument {

    private final String name;
    private final double faceValue;
    private final double couponRate;         // annual interest %
    private final int maturityYears;
    private final double currentMarketValue;
    private final String issuerType;         // "GOVERNMENT" or "CORPORATE"

    public Bond(String name, double faceValue, double couponRate,
                int maturityYears, double currentMarketValue, String issuerType) {
        this.name = name;
        this.faceValue = faceValue;
        this.couponRate = couponRate;
        this.maturityYears = maturityYears;
        this.currentMarketValue = currentMarketValue;
        this.issuerType = issuerType;
    }

    @Override
    public void accept(PortfolioVisitor visitor) {
        visitor.visit(this);   // double dispatch — tells visitor "I am a Bond"
    }

    @Override
    public double getCurrentValue() {
        return currentMarketValue;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public int getMaturityYears() {
        return maturityYears;
    }

    public String getIssuerType() {
        return issuerType;
    }

    @Override
    public String getName() {
        return name;
    }
}