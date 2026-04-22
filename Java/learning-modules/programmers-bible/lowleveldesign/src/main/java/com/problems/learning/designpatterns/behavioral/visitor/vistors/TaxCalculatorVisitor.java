package com.problems.learning.designpatterns.behavioral.visitor.vistors;

import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Bond;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Crypto;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.RealEstate;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Stock;

/**
 * PROBLEM SOLVED:
 * Tax rules differ per instrument type — stocks have short/long term rates,
 * bonds have coupon income tax, real estate has depreciation benefits,
 * crypto is taxed like property. Without Visitor, you'd cram all these
 * rules into each instrument class, violating Single Responsibility.
 * <p>
 * This visitor centralizes ALL tax logic in one class.
 */
public class TaxCalculatorVisitor implements PortfolioVisitor {

    private double totalTaxLiability = 0;

    private static final double SHORT_TERM_RATE = 0.30;  // < 365 days
    private static final double LONG_TERM_RATE = 0.15;  // >= 365 days
    private static final double DIVIDEND_TAX = 0.10;
    private static final double BOND_COUPON_TAX = 0.20;
    private static final double RENTAL_INCOME_TAX = 0.25;
    private static final double CRYPTO_TAX_RATE = 0.30;  // treated as property

    @Override
    public void visit(Stock stock) {
        double capitalGain = (stock.getCurrentPrice() - stock.getPurchasePrice())
                * stock.getShares();
        double taxRate = stock.getHoldingDays() >= 365 ? LONG_TERM_RATE : SHORT_TERM_RATE;
        double capitalGainTax = Math.max(0, capitalGain * taxRate);
        double dividendTax = stock.getCurrentValue() * (stock.getDividendYield() / 100)
                * DIVIDEND_TAX;
        double totalTax = capitalGainTax + dividendTax;

        totalTaxLiability += totalTax;

        System.out.printf("  📈 %-20s | Capital Gain: $%8.2f | Tax (%s): $%7.2f | Dividend Tax: $%6.2f | Total: $%7.2f%n",
                stock.getName(),
                capitalGain,
                stock.getHoldingDays() >= 365 ? "LONG " : "SHORT",
                capitalGainTax,
                dividendTax,
                totalTax);
    }

    @Override
    public void visit(Bond bond) {
        double annualCouponIncome = bond.getFaceValue() * (bond.getCouponRate() / 100);
        // Government bonds get a tax exemption
        double effectiveRate = bond.getIssuerType().equals("GOVERNMENT")
                ? BOND_COUPON_TAX * 0.5 : BOND_COUPON_TAX;
        double tax = annualCouponIncome * effectiveRate;

        totalTaxLiability += tax;

        System.out.printf("  🏦 %-20s | Coupon Income: $%7.2f | Rate: %.0f%% (%s) | Tax: $%7.2f%n",
                bond.getName(),
                annualCouponIncome,
                effectiveRate * 100,
                bond.getIssuerType(),
                tax);
    }

    @Override
    public void visit(RealEstate realEstate) {
        double netRentalIncome = realEstate.getAnnualRentalIncome()
                - realEstate.getAnnualMaintenanceCost();
        double rentalTax = Math.max(0, netRentalIncome * RENTAL_INCOME_TAX);
        double capitalGain = realEstate.getCurrentValue() - realEstate.getPurchasePrice();
        // Long hold real estate benefits from indexation relief
        double cgTaxRate = realEstate.getHoldingYears() > 3 ? 0.20 : 0.30;
        double capitalGainTax = Math.max(0, capitalGain * cgTaxRate);
        double totalTax = rentalTax + capitalGainTax;

        totalTaxLiability += totalTax;

        System.out.printf("  🏠 %-20s | Rental Tax: $%7.2f | Capital Gain Tax: $%7.2f | Total: $%7.2f%n",
                realEstate.getName(),
                rentalTax,
                capitalGainTax,
                totalTax);
    }

    @Override
    public void visit(Crypto crypto) {
        double capitalGain = (crypto.getCurrentPrice() - crypto.getPurchasePrice())
                * crypto.getCoins();
        double tax = Math.max(0, capitalGain * CRYPTO_TAX_RATE);

        totalTaxLiability += tax;

        System.out.printf("  ₿  %-20s | Capital Gain: $%8.2f | Tax (30%%): $%7.2f%n",
                crypto.getName(),
                capitalGain,
                tax);
    }

    public double getTotalTaxLiability() {
        return totalTaxLiability;
    }
}