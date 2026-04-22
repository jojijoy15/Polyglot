package com.problems.learning.designpatterns.behavioral.visitor.vistors;

import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Bond;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Crypto;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.RealEstate;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Stock;

/**
 * PROBLEM SOLVED:
 * Risk scoring logic is completely different per asset class.
 * Stocks use beta/sector risk, bonds use duration risk,
 * real estate uses liquidity risk, crypto uses volatility.
 * This visitor owns all risk logic without touching the instruments.
 */
public class RiskAssessorVisitor implements PortfolioVisitor {

    public enum RiskLevel {LOW, MEDIUM, HIGH, VERY_HIGH}

    private double totalWeightedRisk = 0;
    private double totalValue = 0;

    @Override
    public void visit(Stock stock) {
        // Sector-based risk + concentration based on holding period
        double sectorRisk = switch (stock.getSector()) {
            case "Technology" -> 0.70;
            case "Healthcare" -> 0.45;
            case "Utilities" -> 0.25;
            case "Finance" -> 0.55;
            default -> 0.50;
        };
        double holdingRisk = stock.getHoldingDays() < 90 ? 0.20 : 0.0;
        double riskScore = Math.min(1.0, sectorRisk + holdingRisk);

        accumulateRisk(stock.getCurrentValue(), riskScore);
        System.out.printf("  📈 %-20s | Sector: %-12s | Risk Score: %.2f | Level: %-9s | Value: $%10.2f%n",
                stock.getName(), stock.getSector(), riskScore,
                classifyRisk(riskScore), stock.getCurrentValue());
    }

    @Override
    public void visit(Bond bond) {
        // Duration risk — longer maturity = higher interest rate risk
        double durationRisk = Math.min(1.0, bond.getMaturityYears() * 0.08);
        double issuerRisk = bond.getIssuerType().equals("GOVERNMENT") ? 0.05 : 0.25;
        double riskScore = Math.min(1.0, durationRisk + issuerRisk);

        accumulateRisk(bond.getCurrentValue(), riskScore);
        System.out.printf("  🏦 %-20s | Maturity: %2dy | Issuer: %-11s | Risk Score: %.2f | Level: %-9s%n",
                bond.getName(), bond.getMaturityYears(),
                bond.getIssuerType(), riskScore, classifyRisk(riskScore));
    }

    @Override
    public void visit(RealEstate realEstate) {
        // Real estate risk = liquidity risk + market risk
        double liquidityRisk = 0.40;  // always illiquid
        double typeRisk = realEstate.getPropertyType().equals("COMMERCIAL") ? 0.30 : 0.15;
        double riskScore = Math.min(1.0, liquidityRisk + typeRisk);

        accumulateRisk(realEstate.getCurrentValue(), riskScore);
        System.out.printf("  🏠 %-20s | Type: %-12s | Risk Score: %.2f | Level: %-9s | Value: $%10.2f%n",
                realEstate.getName(), realEstate.getPropertyType(),
                riskScore, classifyRisk(riskScore), realEstate.getCurrentValue());
    }

    @Override
    public void visit(Crypto crypto) {
        // Crypto risk is primarily volatility + holding period
        double holdingRisk = crypto.getHoldingDays() < 180 ? 0.20 : 0.10;
        double riskScore = Math.min(1.0, crypto.getVolatilityIndex() + holdingRisk);

        accumulateRisk(crypto.getCurrentValue(), riskScore);
        System.out.printf("  ₿  %-20s | Volatility: %.2f | Risk Score: %.2f | Level: %-9s | Value: $%10.2f%n",
                crypto.getName(), crypto.getVolatilityIndex(),
                riskScore, classifyRisk(riskScore), crypto.getCurrentValue());
    }

    private void accumulateRisk(double value, double riskScore) {
        totalWeightedRisk += value * riskScore;
        totalValue += value;
    }

    private RiskLevel classifyRisk(double score) {
        if (score < 0.30) return RiskLevel.LOW;
        if (score < 0.55) return RiskLevel.MEDIUM;
        if (score < 0.75) return RiskLevel.HIGH;
        return RiskLevel.VERY_HIGH;
    }

    public double getPortfolioRiskScore() {
        return totalValue > 0 ? totalWeightedRisk / totalValue : 0;
    }

    public RiskLevel getPortfolioRiskLevel() {
        return classifyRisk(getPortfolioRiskScore());
    }
}