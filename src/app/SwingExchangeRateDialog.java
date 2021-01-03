package app;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.Currency;
import view.ExchangeRateDialog;


public class SwingExchangeRateDialog extends JPanel implements ExchangeRateDialog {
    private final Currency[] currencies;
    private Currency currency;

    public SwingExchangeRateDialog(List<Currency> currencies) {
        this.currencies = currencies.toArray(new Currency[0]);
        this.add(currency());
    }

    @Override
    public Currency get() {
        return currency;
    }

    private Component currency() {
        JComboBox comboBox = new JComboBox(currencies);
        comboBox.addItemListener(currencyChanged());
        currency = (Currency) comboBox.getSelectedItem();
        return comboBox;
    }
    
    private ItemListener currencyChanged() {
        return new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent itemEvent){
                if(itemEvent.getStateChange() == ItemEvent.DESELECTED) return;
                currency = (Currency) itemEvent.getItem();
            }
        };
    }

}
