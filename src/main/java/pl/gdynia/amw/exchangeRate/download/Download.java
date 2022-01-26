package pl.gdynia.amw.exchangeRate.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

//@EnableScheduling
//@Component //służy do ładowania na starcie projektu
public class Download {

    private static final String POST_API_URL  = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    @Autowired
    private final ExchangeRateRepository exchangeRateRepository;

    public Download(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }


    //metoda pobierająca dane z pliku XML i zapisuje je do bazy danych
    //metoda wykonuję się o 16:10 od PON-PIA
//    @Scheduled(cron = "0 10 16 * * MON-FRI")
    public void addToDatabase(){

        try{
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
        catch (ParserConfigurationException | SAXException | IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }
}
