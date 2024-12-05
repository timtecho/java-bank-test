package com.example.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
@Component
public class MoneyUtils {
    private static final int MW_GLIM = 5;
    private static final int MW_MCLIM = 50;
    private static final int MW_MLLIM = 6;

    private BigDecimal amount;
    private char[] moneyChar;
    private String[] moneyLines;
    private String[] groupWords;
    private String[] tensWords;
    private String[] onesWords;

    public MoneyUtils() {
        this.amount = BigDecimal.ZERO;
        this.moneyChar = new char[MW_MCLIM];
        this.moneyLines = new String[MW_MLLIM];
        Arrays.fill(this.moneyLines, "");

        this.groupWords = new String[]{"TRILLION", "BILLION", "MILLION", "THOUSAND", ""};
        this.tensWords = new String[]{"TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"};
        this.onesWords = new String[]{"ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
                "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String[] getMoneyLines() {
        return this.moneyLines;
    }

    public String getMoneyLine(int index) {
        if (index >= 0 && index < moneyLines.length) {

            return moneyLines[index];

        }
        // print line
        System.out.println("Number: " + moneyLines);
        return "";
    }

    public void convertToWords() {
        // Implementation of converting amount to words
        // This would involve complex logic to handle different cases
        // and populate the moneyLines array with the result
    }

    private void addWord(String word) {
        // Logic to add a word to the current money line
        // If the current line is full, move to the next line
    }

    private String getGroupWord(int index) {
        return (index >= 0 && index < groupWords.length) ? groupWords[index] : "";
    }

    private String getTensWord(int index) {
        return (index >= 0 && index < tensWords.length) ? tensWords[index] : "";
    }

    private String getOnesWord(int index) {
        return (index >= 0 && index < onesWords.length) ? onesWords[index] : "";
    }
}