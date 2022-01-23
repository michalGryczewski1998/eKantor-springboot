package pl.gdynia.amw.exchangeRate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.gdynia.amw.exchangeRate.model.Data;
import pl.gdynia.amw.exchangeRate.model.ExchangeRateRepository;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;


@RestController
public class ApplicationController{

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    private static final String POST_API_URL  = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";


    @RequestMapping("/save")
    public void addToDatabase(){
        exchangeRateRepository.save(new Data(
                "EUR",
                "PLN",
                4.5318,
                "2022-01-21"
        ));

        exchangeRateRepository.save(new Data(
                "EUR",
                "USD",
                1.1348,
                "2022-01-21"
        ));
    }

    @RequestMapping("/download")
    public void downloadXml() throws ParserConfigurationException, URISyntaxException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(String.valueOf(new URI(POST_API_URL)));

        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("Cube");

        for(int i = 2; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Node nodeTime = nodeList.item(1);

            Element element = (Element) node;
            Element elementTime = (Element) nodeTime;

            exchangeRateRepository.save( new Data(
                    "EUR",
                    element.getAttribute("currency"),
                    Double.parseDouble(element.getAttribute("rate")),
                    elementTime.getAttribute("time")
            ));
        }
    }

    @RequestMapping("/database")
    public StringBuilder findAll(){
        StringBuilder result = new StringBuilder();

        for(Data database : exchangeRateRepository.findAll()){
            result.append(database.toString());
        }
        return result;
    }
    @RequestMapping("/start")
    public String testAplikacji(){
        return "Aplikacja działa poprawnie";
    }

    @RequestMapping(path="/message", produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String processForm(@RequestParam String currencyFrom, @RequestParam String currencyTo, @RequestParam String time) {
        try{
            if(Objects.equals(currencyFrom, "EUR")){
                float rateEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyTo,time);
                return "Aktualny kurs" + " " + "Euro" + " na " + " " + currencyTo + " " + "wynosi" + " " + rateEuro;
            }
            else if(currencyTo == "EUR"){
                float toEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyFrom,time);
                float rateEuro = 1/toEuro;
                return "Aktualny kurs" + " " + currencyFrom + " na " + " " + "Euro" + " " + "wynosi" + " " + rateEuro;
            }
            else {
                float from = exchangeRateRepository.querryToDatabaseCurrencyFrom(currencyFrom,time);
                float to = exchangeRateRepository.querryToDatabaseCurrencyTo(currencyTo,time);

                float rate = to/from;
                return "Aktualny kurs" + " " + currencyFrom + " " + " na " + " " + currencyTo + " " + "wynosi" + " " + rate;
            }
        }
        catch (Exception e){
            return "Błąd!!!" + " " + e;
        }
    }

//    @RequestMapping ("/convert/EUR/{currencyTo}/{time}")
//    public  String getEuro(String currencyTo, String time){
//        float rateEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyTo,time);
//        return "Aktualny kurs" + " " + "Euro" + " na " + " " + currencyTo + " " + "wynosi" + " " + rateEuro;
//    }

//    @RequestMapping ("/convert/{currencyFrom}/{currencyTo}/{time}")
//    public String getCurrency(@PathVariable String currencyFrom, @PathVariable String currencyTo, @PathVariable String time) {
//        float from = exchangeRateRepository.querryToDatabaseCurrencyFrom(currencyFrom,time);
//        float to = exchangeRateRepository.querryToDatabaseCurrencyTo(currencyTo,time);
//
//        float rate = to/from;
//        return "Aktualny kurs" + " " + currencyFrom + " " + " na " + " " + currencyTo + " " + "wynosi" + " " + rate;
//    }

//    @RequestMapping ("/convert/{currencyFrom}/EUR/{time}")
//    public  String toEuro(@PathVariable String currencyFrom, @PathVariable String time){
//        float toEuro = exchangeRateRepository.querryToDatabaseCurrencyEuro(currencyFrom,time);
//        float rateEuro = 1/toEuro;
//        return "Aktualny kurs" + " " + currencyFrom + " na " + " " + "Euro" + " " + "wynosi" + " " + rateEuro;
//    }

}
