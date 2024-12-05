package com.example.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyConversionService {

    private static final int MW_MCLIM = 50;
    private static final int MW_MLLIM = 6;

    private static final String[] ONES_VALUES = {
        "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
        "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"
    };

    private static final String[] TENS_VALUES = {
        "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"
    };

    public String convertToWords(BigDecimal amount) {
        List<String> moneyLines = new ArrayList<>();
        StringBuilder moneyBuilder = new StringBuilder();

        long dollars = amount.longValue();
        int cents = amount.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue();

        if (dollars == 0 && cents == 0) {
            moneyLines.add("** NO DOLLARS AND NO CENTS **");
            return "** NO DOLLARS AND NO CENTS **";
        }

        processNumber(dollars, moneyBuilder);

        if (dollars == 1) {
            moneyBuilder.append("DOLLAR ");
        } else {
            moneyBuilder.append("DOLLARS ");
        }

        moneyBuilder.append("AND ");

        if (cents == 0) {
            moneyBuilder.append("NO CENTS");
        } else {
            processNumber(cents, moneyBuilder);
            if (cents == 1) {
                moneyBuilder.append("CENT");
            } else {
                moneyBuilder.append("CENTS");
            }
        }

        String moneyText = moneyBuilder.toString().trim();
        formatMoneyLines(moneyText, moneyLines);


        return moneyText;
    }

    private void processNumber(long number, StringBuilder builder) {
        if (number >= 1_000_000_000_000L) {
            processGroup(number / 1_000_000_000_000L, builder);
            builder.append("TRILLION ");
            number %= 1_000_000_000_000L;
        }

        if (number >= 1_000_000_000) {
            processGroup(number / 1_000_000_000, builder);
            builder.append("BILLION ");
            number %= 1_000_000_000;
        }

        if (number >= 1_000_000) {
            processGroup(number / 1_000_000, builder);
            builder.append("MILLION ");
            number %= 1_000_000;
        }

        if (number >= 1000) {
            processGroup(number / 1000, builder);
            builder.append("THOUSAND ");
            number %= 1000;
        }

        processGroup(number, builder);
    }

    private void processGroup(long number, StringBuilder builder) {
        int hundreds = (int) (number / 100);
        int tensAndOnes = (int) (number % 100);

        if (hundreds > 0) {
            builder.append(ONES_VALUES[hundreds - 1]).append(" HUNDRED ");
        }

        if (tensAndOnes >= 20) {
            int tens = tensAndOnes / 10;
            int ones = tensAndOnes % 10;
            builder.append(TENS_VALUES[tens - 1]).append(" ");
            if (ones > 0) {
                builder.append(ONES_VALUES[ones - 1]).append(" ");
            }
        } else if (tensAndOnes > 0) {
            builder.append(ONES_VALUES[tensAndOnes - 1]).append(" ");
        }
    }

    private void formatMoneyLines(String moneyText, List<String> moneyLines) {
        StringBuilder lineBuilder = new StringBuilder();
        String[] words = moneyText.split("\\s+");

        for (String word : words) {
            if (lineBuilder.length() + word.length() + 3 > MW_MCLIM) {
                addMoneyLine(lineBuilder.toString(), moneyLines);
                lineBuilder = new StringBuilder();
            }
            if (lineBuilder.length() == 0) {
                lineBuilder.append("** ");
            } else {
                lineBuilder.append(" ");
            }
            lineBuilder.append(word);
        }

        if (lineBuilder.length() > 0) {
            addMoneyLine(lineBuilder.toString(), moneyLines);
        }
    }

    private void addMoneyLine(String line, List<String> moneyLines) {
        if (moneyLines.size() < MW_MLLIM) {
            StringBuilder paddedLine = new StringBuilder(line);
            while (paddedLine.length() < MW_MCLIM - 2) {
                paddedLine.append(" ");
            }
            paddedLine.append("**");
            moneyLines.add(paddedLine.toString());
        }
    }
}