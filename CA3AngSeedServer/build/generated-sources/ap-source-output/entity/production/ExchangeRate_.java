package entity.production;

import entity.Currency;
import entity.ExchangeRate;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-06T16:02:14")
@StaticMetamodel(ExchangeRate.class)
public class ExchangeRate_ { 

    public static volatile SingularAttribute<ExchangeRate, Double> rate;
    public static volatile SingularAttribute<ExchangeRate, Date> date;
    public static volatile SingularAttribute<ExchangeRate, Currency> currency;

}