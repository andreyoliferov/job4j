package jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Collections;
import java.util.TreeSet;


/**
 * @autor aoliferov
 * @since 28.10.2018
 */
public class ParseXML extends DefaultHandler {

    /**
     * Поле - сумма значений
     */
    private static long summ = 0;

    /**
     * Процедура - выполняет действие перед каждым тегом
     * @param namespaceURI пространство имен
     * @param localName локальное имя
     * @param qName имя тега
     * @param atts аттрибут тега
     */
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {
        if (qName.equals("entry")) {
            summ = summ + Integer.parseInt(atts.getValue("field"));
        }
    }

    /*
    /**
     * Процедура - парсинг элементов внутри тега
     * в задании не используется
     * @param ch символы
     * @param start начальная позиция
     * @param length длина
     */
    /*
    @Override
    public void characters(char[] ch, int start, int length){
        System.out.println(new String(ch, start, length));
    }
    */

    /**
     * Функция - инициализация парсера, парсинг
     * @param file файл для парсинга
     * @return сумма значений
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public long parseSumm(File file) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);  //валидация
        factory.setNamespaceAware(false); //поддержка пространства имен
        SAXParser parser = factory.newSAXParser();
        InputStream xmlData = new FileInputStream(file);
        parser.parse(xmlData, new ParseXML());
        return summ;
    }

    public static long getSumm() {
        return summ;
    }
}
