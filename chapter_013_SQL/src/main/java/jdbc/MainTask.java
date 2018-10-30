package jdbc;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @autor aoliferov
 * @since 28.10.2018
 */
public class MainTask {

    /**
     * 1. Формируем данные в БД
     * 2. Получаем данные из БД
     * 3. Записываем данные в XML
     * 4. Конветируем XML по схеме XLST
     * 5. Парсим данные из нового XML
     * 6. Выводим сумму
     */
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, JAXBException,
            TransformerException, ParserConfigurationException, SAXException {
        File sourse = new File("chapter_013_SQL/src/main/resources/source.xml");
        File scheme = new File("chapter_013_SQL/src/main/resources/scheme.xsl");
        File dest = new File("chapter_013_SQL/src/main/resources/dest.xml");
        StoreSQL sql = new StoreSQL();
        sql.generate(1000001);
        List<StoreXML.Entry> data = sql.getData();
        new StoreXML().save(data, sourse);
        new ConvertXSLT().convert(sourse, dest, scheme);
        System.out.println(new ParseXML().parseSumm(dest));
    }
}
