package com.problems.learning.designpatterns.behavioral.visitor.financial.instrument;

import com.problems.learning.designpatterns.behavioral.visitor.vistors.PortfolioVisitor;

/**
 * Every financial instrument accepts a visitor.
 * The accept() method is the hook that enables double dispatch —
 * the instrument tells the visitor exactly what type it is,
 * so the right visit() overload gets called automatically.
 */
public interface FinancialInstrument {
    void accept(PortfolioVisitor visitor);

    String getName();

    double getCurrentValue();
}