package com.problems.learning.algo.math;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelColumnTest {

    ExcelColumn excelColumn = new ExcelColumn();

    @Test
    void calculateColumnSmall() {
        String calculatedColumn = excelColumn.calculateColumn(27);
        assertThat(calculatedColumn).isEqualTo("AA");
    }

    @Test
    void calculateColumnBig() {

        String calculatedColumn = excelColumn.calculateColumn(701);
        assertThat(calculatedColumn).isEqualTo("ZY");
    }
}