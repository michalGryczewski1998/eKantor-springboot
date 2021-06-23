package pl.gdynia.amw.exchangeRate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.gdynia.amw.exchangeRate.model.Data;
import pl.gdynia.amw.exchangeRate.model.ExchangeRateRepository;


@RestController
public class ApplicationController{

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

//    @RequestMapping("/database")
//    public StringBuilder findAll(){
//        StringBuilder result = new StringBuilder();
//
//        for(Data database : exchangeRateRepository.findAll()){
//            result.append(database.toString());
//        }
//        return result;
//    }

    @RequestMapping ("/convert/{currencyFrom}/{currencyTo}/{time}")
    public String getCurrency(@PathVariable String currencyFrom, @PathVariable String currencyTo, @PathVariable String time) {
        float from = exchangeRateRepository.querryToDatabaseCurrencyFrom(currencyFrom,time);
        float to = exchangeRateRepository.querryToDatabaseCurrencyTo(currencyTo,time);

        float rate = to/from;
        return "Aktualny kurs" + " " + currencyFrom + " " + " na " + " " + currencyTo + " " + "wynosi" + " " + rate;
    }
    @RequestMapping ("/convert/EUR/{currencyTo}/{time}")
    public  String getEuro(@PathVariable String currencyTo, @PathVariable String time){
        float rateEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyTo,time);
        return "Aktualny kurs" + " " + "Euro" + " na " + " " + currencyTo + " " + "wynosi" + " " + rateEuro;
    }

    @RequestMapping ("/convert/{currencyFrom}/EUR/{time}")
    public  String toEuro(@PathVariable String currencyFrom, @PathVariable String time){
        float toEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyFrom,time);
        float rateEuro = 1/toEuro;
        return "Aktualny kurs" + " " + currencyFrom + " na " + " " + "Euro" + " " + "wynosi" + " " + rateEuro;
    }
}
