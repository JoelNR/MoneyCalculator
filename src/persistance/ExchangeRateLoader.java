package persistance;

import model.Currency;
import model.ExchangeRate;

public interface ExchangeRateLoader {
    public ExchangeRate load(Currency from, Currency to);
}
