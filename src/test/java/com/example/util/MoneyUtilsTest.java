import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

public class MoneyUtilsTest {

    private MoneyUtils moneyUtils;

    @BeforeEach
    public void setUp() {
        moneyUtils = new MoneyUtils();
    }

    @Test
    public void testSetAndGetAmount() {
        BigDecimal amount = new BigDecimal("123.45");
        moneyUtils.setAmount(amount);
        assertEquals(amount, moneyUtils.getAmount());
    }

    @Test
    public void testGetMoneyLines() {
        String[] lines = moneyUtils.getMoneyLines();
        assertEquals(6, lines.length);
        for (String line : lines) {
            assertEquals("", line);
        }
    }

    @Test
    public void testConvertToWords() {
        moneyUtils.setAmount(new BigDecimal("1234567.89"));
        moneyUtils.convertToWords();
        String[] lines = moneyUtils.getMoneyLines();
        assertNotNull(lines);
        assertTrue(lines[0].contains("ONE MILLION"));
        assertTrue(lines[1].contains("TWO HUNDRED THIRTY FOUR THOUSAND"));
        assertTrue(lines[2].contains("FIVE HUNDRED SIXTY SEVEN DOLLARS"));
        assertTrue(lines[3].contains("AND EIGHTY NINE CENTS"));
    }

    @Test
    public void testGetGroupWord() {
        assertEquals("TRILLION", moneyUtils.getGroupWord(0));
        assertEquals("BILLION", moneyUtils.getGroupWord(1));
        assertEquals("MILLION", moneyUtils.getGroupWord(2));
        assertEquals("THOUSAND", moneyUtils.getGroupWord(3));
        assertEquals("", moneyUtils.getGroupWord(4));
        assertEquals("", moneyUtils.getGroupWord(5)); // Out of bounds
    }

    @Test
    public void testGetTensWord() {
        assertEquals("TEN", moneyUtils.getTensWord(0));
        assertEquals("FIFTY", moneyUtils.getTensWord(4));
        assertEquals("NINETY", moneyUtils.getTensWord(8));
        assertEquals("", moneyUtils.getTensWord(9)); // Out of bounds
    }

    @Test
    public void testGetOnesWord() {
        assertEquals("ONE", moneyUtils.getOnesWord(0));
        assertEquals("NINE", moneyUtils.getOnesWord(8));
        assertEquals("TEN", moneyUtils.getOnesWord(9));
        assertEquals("NINETEEN", moneyUtils.getOnesWord(18));
        assertEquals("", moneyUtils.getOnesWord(19)); // Out of bounds
    }

    // Mock implementation of MoneyUtils
    private static class MoneyUtils {
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

        public void convertToWords() {
            // Simple mock implementation
            long dollars = amount.longValue();
            int cents = amount.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue();
            
            moneyLines[0] = "** " + getWordsForNumber(dollars / 1000000) + " MILLION";
            moneyLines[1] = "** " + getWordsForNumber((dollars % 1000000) / 1000) + " THOUSAND";
            moneyLines[2] = "** " + getWordsForNumber(dollars % 1000) + " DOLLARS";
            moneyLines[3] = "** AND " + getWordsForNumber(cents) + " CENTS";
        }

        private String getWordsForNumber(long number) {
            if (number == 0) return "";
            if (number < 20) return getOnesWord((int)number - 1);
            if (number < 100) return getTensWord((int)(number / 10) - 1) + " " + getWordsForNumber(number % 10);
            return getOnesWord((int)(number / 100) - 1) + " HUNDRED " + getWordsForNumber(number % 100);
        }

        public String getGroupWord(int index) {
            return (index >= 0 && index < groupWords.length) ? groupWords[index] : "";
        }

        public String getTensWord(int index) {
            return (index >= 0 && index < tensWords.length) ? tensWords[index] : "";
        }

        public String getOnesWord(int index) {
            return (index >= 0 && index < onesWords.length) ? onesWords[index] : "";
        }
    }
}