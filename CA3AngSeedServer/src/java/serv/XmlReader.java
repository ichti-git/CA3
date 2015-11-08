/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import facades.CurrencyFacade;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ichti
 */
//Should have a better name.
public class XmlReader extends DefaultHandler {

    CurrencyFacade facade = new CurrencyFacade();
    Date date;
    
    @Override
    public void startDocument() throws SAXException { 
        //System.out.println("Start Document (Sax-event)");
    }
    @Override
    public void endDocument() throws SAXException {
        //System.out.println("End Document (Sax-event)");
        facade.updateExchangeRates(date);
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        switch(localName) {
            case "exchangerates": //Don't care about it
                break;
            case "dailyrates": //set the date
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(attributes.getValue("id"));
                } 
                catch (ParseException ex) {
                    System.out.println("error");
                    Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "currency": //set currency with the current date
                String code = attributes.getValue("code");
                String name = attributes.getValue("desc");
                facade.addCurrency(code, name); //Add currency, in case it doesn't exist
                Double rate;
                if (!attributes.getValue("rate").equals("-")) { 
                    rate = Double.parseDouble(attributes.getValue("rate"));
                }
                else {rate = 0.0; }
                facade.addExchangeRate(code, date, rate);
                break;
            default:
                //nothing
                
        }
                /*
        System.out.print("Element: " + localName+": " );
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.print("[Atribute: NAME: "+ attributes.getLocalName(i) + " VALUE: " + attributes.getValue(i)+"] ");
        }
        System.out.println("");
                        */
    }
    
    public static void main(String[] argv) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new XmlReader());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en"); //today
            xr.parse(new InputSource(url.openStream()));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
