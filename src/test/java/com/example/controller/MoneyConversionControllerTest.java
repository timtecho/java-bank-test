//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class MoneyConversionControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private MoneyConversionService moneyConversionService;
//
//    @Mock
//    private MoneyUtils moneyUtils;
//
//    private MoneyConversionController moneyConversionController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        moneyConversionController = new MoneyConversionController(moneyConversionService, moneyUtils);
//        mockMvc = MockMvcBuilders.standaloneSetup(moneyConversionController).build();
//    }
//
//    @Test
//    public void testConvertMoneyWithZeroAmount() throws Exception {
//        mockMvc.perform(post("/api/money/convert")
//                .param("amount", "0")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Conversion completed. Enter 0 to exit."));
//
//        verify(moneyUtils, never()).setAmount(any(BigDecimal.class));
//        verify(moneyConversionService, never()).convertMoney();
//    }
//
//    @Test
//    public void testConvertMoneyWithValidAmount() throws Exception {
//        BigDecimal amount = new BigDecimal("123.45");
//        List<String> mockLines = new ArrayList<>();
//        mockLines.add("** ONE HUNDRED TWENTY THREE DOLLARS AND **");
//        mockLines.add("** FORTY FIVE CENTS                     **");
//
//        when(moneyUtils.getMoneyLine(anyInt())).thenAnswer(invocation -> {
//            int index = invocation.getArgument(0);
//            return index < mockLines.size() ? mockLines.get(index) : "";
//        });
//
//        mockMvc.perform(post("/api/money/convert")
//                .param("amount", amount.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        "C O N V E R T E D   D O L L A R   A M O U N T\n\n" +
//                        "** ONE HUNDRED TWENTY THREE DOLLARS AND **\n" +
//                        "** FORTY FIVE CENTS                     **\n" +
//                        "\n\n\n\n"
//                ));
//
//        verify(moneyUtils).setAmount(amount);
//        verify(moneyConversionService).convertMoney();
//        verify(moneyUtils, times(6)).getMoneyLine(anyInt());
//    }
//
//    // Mock implementations
//
//    private static class MoneyConversionController {
//        private final MoneyConversionService moneyConversionService;
//        private final MoneyUtils moneyUtils;
//
//        public MoneyConversionController(MoneyConversionService moneyConversionService, MoneyUtils moneyUtils) {
//            this.moneyConversionService = moneyConversionService;
//            this.moneyUtils = moneyUtils;
//        }
//
//        public String convertMoney(BigDecimal amount) {
//            if (amount.compareTo(BigDecimal.ZERO) == 0) {
//                return "Conversion completed. Enter 0 to exit.";
//            }
//
//            moneyUtils.setAmount(amount);
//            moneyConversionService.convertMoney();
//
//            StringBuilder result = new StringBuilder();
//            result.append("C O N V E R T E D   D O L L A R   A M O U N T\n\n");
//            for (int i = 0; i < 6; i++) {
//                result.append(moneyUtils.getMoneyLine(i)).append("\n");
//            }
//
//            return result.toString();
//        }
//    }
//
//    private interface MoneyConversionService {
//        void convertMoney();
//    }
//
//    private interface MoneyUtils {
//        void setAmount(BigDecimal amount);
//        String getMoneyLine(int index);
//    }
//}