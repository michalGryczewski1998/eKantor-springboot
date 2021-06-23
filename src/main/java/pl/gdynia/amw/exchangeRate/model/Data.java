package pl.gdynia.amw.exchangeRate.model;

import javax.persistence.*;

@Entity
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String currencyFirst  ;
    private String currencySecond ;
    private double rateCurrency ;
    private String time;

    public Data(String currencyFirst, String currencySecond, double rateCurrency, String time) {
        this.currencyFirst = currencyFirst;
        this.currencySecond = currencySecond;
        this.rateCurrency = rateCurrency;
        this.time = time;
    }

    public Data() {

    }

    @Override
    public String toString() {
        return
                "<div>" +
                "id=" + id +
                ", Waluta główna = '" + currencyFirst + '\'' +
                ", Waluta docelowa = '" + currencySecond + '\'' +
                ", Wartość = '" + rateCurrency + '\'' +
                ", Data = '" + time + '\'' + "<hr>" + "</div>";
    }
}
