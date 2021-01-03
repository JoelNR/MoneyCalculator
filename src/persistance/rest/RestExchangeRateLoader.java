package persistance.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import model.Currency;
import model.ExchangeRate;
import persistance.ExchangeRateLoader;

/*
Esta implementación es incorrecta debido a que el API endpoint ha quedado obsoleto
*/
public class RestExchangeRateLoader implements ExchangeRateLoader{

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return new ExchangeRate(from, to, read(from.getCode(), to.getCode()));
        }   catch (MalformedURLException exception){
            System.out.println("ERROR RestExchangeRateLoader (Mal formed URL)" + exception.getMessage());
            return null;
        } catch (IOException exception){
            System.out.println("ERROR RestExchangeRateLoader (IO)" + exception.getMessage());
            return null;
        }
    }

    private double read(String from, String to) throws IOException,MalformedURLException{
        String line = read(new URL("http://api.fixer.io/latest?base=" + from + "&sysmbols=" + to));
        return Double.parseDouble(line.substring(line.indexOf(to) +5, line.indexOf("}")));
    }
    
    private String read (URL url) throws IOException {
        InputStream is = url.openStream();
        byte[] buffer = new byte[1024];
        int length = is.read(buffer);
        return new String(buffer,0,length);
    }

}
