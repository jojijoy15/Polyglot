package com.problems.learning.designpatterns.behavioral.visitor.client;

import com.problems.learning.designpatterns.behavioral.visitor.financial.Portfolio;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Bond;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Crypto;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.RealEstate;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Stock;
import com.problems.learning.designpatterns.behavioral.visitor.vistors.ReportGeneratorVisitor;
import com.problems.learning.designpatterns.behavioral.visitor.vistors.RiskAssessorVisitor;
import com.problems.learning.designpatterns.behavioral.visitor.vistors.TaxCalculatorVisitor;

public class PortfolioManagement {

    public static void main(String[] args) {

        // Build portfolio
        Portfolio portfolio = new Portfolio("Joji");

        portfolio.add(new Stock("Apple Inc.", 150.00, 195.00, 50, 0.55, "Technology", 400));
        portfolio.add(new Stock("Johnson & Johnson", 155.00, 148.00, 30, 2.80, "Healthcare", 80));
        portfolio.add(new Bond("US Treasury 10Y", 10000, 4.50, 10, 9800, "GOVERNMENT"));
        portfolio.add(new Bond("Tesla Corp Bond", 5000, 6.20, 5, 5100, "CORPORATE"));
        portfolio.add(new RealEstate("Downtown Apartment", 250000, 340000, 24000, 5000, "RESIDENTIAL", 5));
        portfolio.add(new RealEstate("Office Space", 500000, 620000, 60000, 15000, "COMMERCIAL", 2));
        portfolio.add(new Crypto("Bitcoin", 30000, 65000, 0.5, 0.75, 500));
        portfolio.add(new Crypto("Ethereum", 1800, 3200, 2.0, 0.80, 120));

        // ── Run Tax Visitor ─────────────────────────────────────────────
        System.out.println("\n💰 TAX CALCULATION\n" + "─".repeat(110));
        TaxCalculatorVisitor taxVisitor = new TaxCalculatorVisitor();
        portfolio.accept(taxVisitor);
        System.out.printf("%n   ➤ Total Tax Liability: $%.2f%n", taxVisitor.getTotalTaxLiability());

        // ── Run Risk Visitor ─────────────────────────────────────────────
        System.out.println("\n⚠️  RISK ASSESSMENT\n" + "─".repeat(110));
        RiskAssessorVisitor riskVisitor = new RiskAssessorVisitor();
        portfolio.accept(riskVisitor);
        System.out.printf("%n   ➤ Portfolio Risk Score: %.2f | Level: %s%n",
                riskVisitor.getPortfolioRiskScore(),
                riskVisitor.getPortfolioRiskLevel());

        // ── Run Report Visitor ───────────────────────────────────────────
        System.out.println("\n📊 GENERATING REPORT...\n");
        ReportGeneratorVisitor reportVisitor = new ReportGeneratorVisitor();
        portfolio.accept(reportVisitor);
        System.out.println(reportVisitor.getReport(
                taxVisitor.getTotalTaxLiability(),
                riskVisitor.getPortfolioRiskScore()));
    }
}