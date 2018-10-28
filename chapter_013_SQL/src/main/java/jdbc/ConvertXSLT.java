package jdbc;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * @autor aoliferov
 * @since 24.10.2018
 */
public class ConvertXSLT {

    /**
     * Процедура преобразования xml по схеме xsl
     * @param source исходный файл xml
     * @param dest новый файл xml
     * @param scheme схема xsl
     */
    public void convert(File source, File dest, File scheme) throws FileNotFoundException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(scheme));
        transformer.transform(new StreamSource(source), new StreamResult(dest));
    }
}
