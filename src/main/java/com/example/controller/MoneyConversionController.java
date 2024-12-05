package com.example.controller;

import com.example.service.MoneyConversionService;
import com.example.util.MoneyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/money")
@Api(value = "Money Conversion Controller", description = "Operations for money conversion")
public class MoneyConversionController {

    private final MoneyConversionService moneyConversionService;
    private final MoneyUtils moneyUtils;

    @Autowired
    public MoneyConversionController(MoneyConversionService moneyConversionService, MoneyUtils moneyUtils) {
        this.moneyConversionService = moneyConversionService;
        this.moneyUtils = moneyUtils;
    }

    @PostMapping("/convert")
    @ApiOperation(value = "Convert dollar amount", notes = "Converts a dollar amount to its text representation")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful conversion"),
        @ApiResponse(code = 400, message = "Invalid input")
    })
    public String convertMoney(
            @ApiParam(value = "Dollar amount to convert", required = true)
            @RequestParam BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "Conversion completed. Enter 0 to exit.";
        }

        moneyUtils.setAmount(amount);
        String res = moneyConversionService.convertToWords(amount);

        StringBuilder result = new StringBuilder();
//        result.append("C O N V E R T E D   D O L L A R   A M O U N T\n\n");
//        for (int i = 0; i < 6; i++) {
//            result.append(moneyUtils.getMoneyLine(i)).append("\n");
//        }
        String[] words = moneyUtils.getMoneyLines();
        for (String word : words) {
            result.append(word).append(" "); // Append each string with a space
        }


        return res;
    }
}