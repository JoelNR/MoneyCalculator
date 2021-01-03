package persistence.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.Currency;
import model.ExchangeRate;
import persistance.ExchangeRateLoader;


public class FileExchangeRateLoader implements ExchangeRateLoader{
    private final String fileName;

    public FileExchangeRateLoader() {
        this.fileName = "ExchangeRate.txt";
    }

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        
        try{
            Scanner scanner = new Scanner (new File(fileName));
            String fromCode, toCode;
            double rate;
            while(scanner.hasNext()){
                fromCode = scanner.next();
                toCode = scanner.next();
                rate = scanner.nextDouble();
                if(from.getCode().equals(fromCode)){
                    if (to.getCode().equals(toCode)){
                        return new ExchangeRate(from,to,rate);
                    } 
                }
            }
        } catch (FileNotFoundException exception){
            System.out.println("ERROR FileCurrencyListLoader (File not fount)" + exception.getMessage());
        } 
        
        return new ExchangeRate(from,to,0);
    }

}
