package dev.felipeazsantos.exchange_service.controller;

import dev.felipeazsantos.exchange_service.environment.InstanceInformationService;
import dev.felipeazsantos.exchange_service.exceptions.UnsupportedCurrencyException;
import dev.felipeazsantos.exchange_service.model.Exchange;
import dev.felipeazsantos.exchange_service.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange-service")
public class ExchangeController {


    @Autowired
    private InstanceInformationService instanceInformationService;

    @Autowired
    private ExchangeRepository exchangeRepository;

    // http://localhost:8000/exchange-service/5/BRL/USD
    @GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exchange> getExchange(@PathVariable("amount") BigDecimal amount,
                                                @PathVariable("from") String from,
                                                @PathVariable("to") String to) {
        Exchange exchange = exchangeRepository.findByFromAndTo(from, to);
        if (exchange == null) throw new UnsupportedCurrencyException("Unsupported Currency!");

        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);

        exchange.setConvertedValue(convertedValue);
        exchange.setEnvironment("PORT " + instanceInformationService.retrieveServerPort());

        return ResponseEntity.ok(exchange);
    }
}
