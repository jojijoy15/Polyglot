package com.problems.learning.designpatterns.behavioral.visitor.vistors;

import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Bond;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Crypto;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.RealEstate;
import com.problems.learning.designpatterns.behavioral.visitor.financial.instrument.Stock;

/**
 * Visitor declares one visit() method per concrete element type.
 * Each new operation (tax, risk, report) becomes a new Visitor —
 * zero changes to the instrument classes themselves.
 */
public interface PortfolioVisitor {
    void visit(Stock stock);

    void visit(Bond bond);

    void visit(RealEstate realEstate);

    void visit(Crypto crypto);
}