package control;

import model.Currency;
import model.ExchangeRate;
import model.Money;
import persistence.file.FileExchangeRateLoader;

import view.ExchangeRateDialog;
import view.MoneyDialog;
import view.MoneyDisplay;


public class ExchangeCommand implements Command{
    private final MoneyDialog moneyDialog;
    private final MoneyDisplay moneyDisplay;
    private final ExchangeRateDialog exchangeRateDialog;

    public ExchangeCommand(MoneyDialog moneyDialog, MoneyDisplay moneyDisplay, ExchangeRateDialog exchangeRateDialog) {
        this.moneyDialog = moneyDialog;
        this.moneyDisplay = moneyDisplay;
        this.exchangeRateDialog = exchangeRateDialog;
    }
    
    
    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = exchangeRateDialog.get();
        ExchangeRate exchangeRate = new FileExchangeRateLoader().load(money.getCurrency(), currency);
        Money result = exchange(money, exchangeRate);
        moneyDisplay.display(result);
    }
    
    private Money exchange(Money money, ExchangeRate exchangeRate) {
        return new Money(exchangeRate.getTo(), money.getAmount() * exchangeRate.getRate());
    }
    
}
