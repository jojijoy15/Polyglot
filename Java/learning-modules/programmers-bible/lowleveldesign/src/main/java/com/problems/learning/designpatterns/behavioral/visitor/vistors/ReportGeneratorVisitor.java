package com.problems.learning.designpatterns.behavioral.visitor.vistors;

import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Bond;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Crypto;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.RealEstate;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Stock;

/**
 * PROBLEM SOLVED:
 * Report formatting per instrument type would pollute each class
 * with presentation logic. This visitor handles ALL report generation
 * in one place — swap it for a JSON or PDF visitor with zero changes
 * to the instrument classes.
 */
public class ReportGeneratorVisitor implements PortfolioVisitor {

    private final StringBuilder report = new StringBuilder();
    private double totalValue = 0;
    private double totalGain = 0;

    @Override
    public void visit(Stock stock) {
        double invested = stock.getPurchasePrice() * stock.getShares();
        double gain = stock.getCurrentValue() - invested;
        double gainPct = (gain / invested) * 100;

        totalValue += stock.getCurrentValue();
        totalGain += gain;

        report.append(String.format("""
                        ┌─ STOCK: %s
                        │  Shares       : %d @ $%.2f (now $%.2f)
                        │  Invested     : $%.2f
                        │  Market Value : $%.2f
                        │  Gain/Loss    : $%.2f (%.2f%%)
                        │  Sector       : %s | Held: %d days
                        └─────────────────────────────────────%n""",
                stock.getName(), stock.getShares(),
                stock.getPurchasePrice(), stock.getCurrentPrice(),
                invested, stock.getCurrentValue(),
                gain, gainPct,
                stock.getSector(), stock.getHoldingDays()));
    }

    @Override
    public void visit(Bond bond) {
        double annualIncome = bond.getFaceValue() * (bond.getCouponRate() / 100);
        double gain = bond.getCurrentValue() - bond.getFaceValue();

        totalValue += bond.getCurrentValue();
        totalGain += gain;

        report.append(String.format("""
                        ┌─ BOND: %s
                        │  Face Value   : $%.2f | Coupon: %.2f%%
                        │  Market Value : $%.2f
                        │  Annual Income: $%.2f
                        │  Issuer       : %s | Matures in: %d years
                        └─────────────────────────────────────%n""",
                bond.getName(), bond.getFaceValue(),
                bond.getCouponRate(), bond.getCurrentValue(),
                annualIncome, bond.getIssuerType(),
                bond.getMaturityYears()));
    }

    @Override
    public void visit(RealEstate realEstate) {
        double gain = realEstate.getCurrentValue() - realEstate.getPurchasePrice();
        double gainPct = (gain / realEstate.getPurchasePrice()) * 100;
        double netYield = ((realEstate.getAnnualRentalIncome()
                - realEstate.getAnnualMaintenanceCost())
                / realEstate.getCurrentValue()) * 100;

        totalValue += realEstate.getCurrentValue();
        totalGain += gain;

        report.append(String.format("""
                        ┌─ REAL ESTATE: %s
                        │  Purchase     : $%.2f → Now: $%.2f
                        │  Capital Gain : $%.2f (%.2f%%)
                        │  Net Yield    : %.2f%% | Type: %s
                        │  Held         : %d years
                        └─────────────────────────────────────%n""",
                realEstate.getName(),
                realEstate.getPurchasePrice(), realEstate.getCurrentValue(),
                gain, gainPct, netYield,
                realEstate.getPropertyType(),
                realEstate.getHoldingYears()));
    }

    @Override
    public void visit(Crypto crypto) {
        double invested = crypto.getPurchasePrice() * crypto.getCoins();
        double gain = crypto.getCurrentValue() - invested;
        double gainPct = (gain / invested) * 100;

        totalValue += crypto.getCurrentValue();
        totalGain += gain;

        report.append(String.format("""
                        ┌─ CRYPTO: %s
                        │  Coins        : %.4f @ $%.2f (now $%.2f)
                        │  Invested     : $%.2f
                        │  Market Value : $%.2f
                        │  Gain/Loss    : $%.2f (%.2f%%)
                        │  Volatility   : %.2f | Held: %d days
                        └─────────────────────────────────────%n""",
                crypto.getName(), crypto.getCoins(),
                crypto.getPurchasePrice(), crypto.getCurrentPrice(),
                invested, crypto.getCurrentValue(),
                gain, gainPct,
                crypto.getVolatilityIndex(), crypto.getHoldingDays()));
    }

    public String getReport(double taxLiability, double riskScore) {
        return "╔══════════════════════════════════════╗\n"
                + "║       PORTFOLIO REPORT               ║\n"
                + "╚══════════════════════════════════════╝\n"
                + report
                + String.format("""
                        ══════════════════════════════════════
                        Total Portfolio Value : $%.2f
                        Total Gain / Loss     : $%.2f
                        Tax Liability         : $%.2f
                        Portfolio Risk Score  : %.2f
                        ══════════════════════════════════════%n""",
                totalValue, totalGain, taxLiability, riskScore);
    }
}