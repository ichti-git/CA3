/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.ExchangeRate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ichti
 */
public interface CurrencyFacadeInterface {
    public void addExchangeRate(String currency, Date date, Double rate);
    public List<ExchangeRate> getExchangeRates();
    public void updateExchangeRates(Date date);
    public Double getExchangeRate(String currencycode);
    public Double getExchangeRate(String currencycode, Date date);
    public void addCurrency(String code, String name);
}
