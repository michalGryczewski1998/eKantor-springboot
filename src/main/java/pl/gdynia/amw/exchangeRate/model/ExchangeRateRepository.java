package pl.gdynia.amw.exchangeRate.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends CrudRepository<Data,Long> {
 //interfejs do obsługi tabeli
    List<Data> findAll();

    @Query("select d.rateCurrency from Data d where d.currencySecond = :currencyFrom and d.time = :time")
    float querryToDatabaseCurrencyFrom(@Param("currencyFrom") String currencyFrom, @Param("time") String time);

    @Query("select d.rateCurrency from Data d where d.currencySecond = :currencyTo and d.time = :time")
    float querryToDatabaseCurrencyTo(@Param("currencyTo") String currencyTo, @Param("time") String time);

    @Query("select d.rateCurrency from Data d where d.currencySecond = :currencyFrom and d.currencyFirst = 'EUR' and d.time = :time")
    float querryToDatabaseCurrencyEuro(@Param("currencyFrom") String currencyFrom, @Param("time") String time);

}