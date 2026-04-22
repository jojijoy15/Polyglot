package com.problems.learning.algo.math;

public class ExcelColumn {

    public String calculateColumn(int number) {
        StringBuilder excelRowNumber = new StringBuilder();
        while(number > 0){
            char ch = (char) ('A' + (number - 1) % 26);
            excelRowNumber.append(ch);
            number = (number - 1) / 26;
        }
        return excelRowNumber.reverse().toString();
    }


}
