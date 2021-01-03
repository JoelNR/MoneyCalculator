package app;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import model.Currency;
import model.Money;
import view.MoneyDialog;


public class SwingMoneyDialog extends JPanel implements MoneyDialog{
    private final Currency[] currencies;
    private String amount;
    private Currency currency;

    public SwingMoneyDialog(List<Currency> currencies) {
        this.currencies = currencies.toArray(new Currency[0]);
        this.add(amount());
        this.add(currency());
        
    } 
    
    @Override
    public Money get() {
        return new Money(currency, Double.parseDouble(amount));
    }

    private Component amount() {
        JTextField textField = new JTextField("0");
        textField.setColumns(10);
        textField.getDocument().addDocumentListener(amountChanged());
        amount = textField.getText();
        return textField;
    }

    private Component currency() {
        JComboBox comboBox = new JComboBox(currencies);
        comboBox.addItemListener(currencyChanged());
        currency = (Currency) comboBox.getSelectedItem();
        return comboBox;
    }
    
    private DocumentListener amountChanged() {
        return new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent documentEvent){
                amountChanged(documentEvent.getDocument());
            }
            
            @Override
            public void removeUpdate(DocumentEvent documentEvent){
                amountChanged(documentEvent.getDocument());
            }
            
            @Override
            public void changedUpdate(DocumentEvent documentEvent){
                amountChanged(documentEvent.getDocument());
            }
            
            private void amountChanged(Document document){
                try{
                    amount = document.getText(0,document.getLength());
                } catch (BadLocationException exception){
                    System.out.println("ERROR FileCurrencyListLoader (Bad location) " + exception.getMessage());
                }
            }
        };
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
