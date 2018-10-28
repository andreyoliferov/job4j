package jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * @autor aoliferov
 * @since 22.10.2018
 */
public class StoreXML {

    /**
     * Процедура сериализации данных в XML
     * @param list данные
     * @param target файл xml
     */
    public void save(List<Entry> list,  File target) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(
                new StoreXML.Entries(list),
                new StreamResult(target)
        );
    }

    @XmlRootElement
    public static class Entries {
        private List<Entry> values;

        public Entries() {
            //
        }

        public Entries(List<Entry> values) {
            this.values = values;
        }

        @XmlElement(name = "entry")
        public List<Entry> getValues() {
            return values;
        }

        public void setValues(List<Entry> values) {
            this.values = values;
        }
    }

    @XmlRootElement
    public static class Entry {
        private int value;

        public Entry() {
            //
        }

        public Entry(int value) {
            this.value = value;
        }

        @XmlElement(name = "field")
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
