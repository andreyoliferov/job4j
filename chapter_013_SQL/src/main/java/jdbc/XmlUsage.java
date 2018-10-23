package jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @autor aoliferov
 * @since 22.10.2018
 */
public class XmlUsage {

    @XmlRootElement
    public static class User {
        private List<Field> values;

        public User() {
        }

        public User(List<Field> values) {
            this.values = values;
        }

        @XmlElementWrapper(name="wild-animals", nillable = true)
        @XmlElement(name = "catname")
        public List<Field> getValues() {
            return values;
        }

        public void setValues(List<Field> values) {
            this.values = values;
        }

        @XmlAttribute(name = "age")
        public int age;
    }

    @XmlRootElement
    public static class Field {
        private int value;


        public Field() {
        }

        public Field(int value, int age) {
            this.value = value;
            this.age = age;
        }


        @XmlElement(name = "catname")
        public int getValue() {
            return value;
        }


        public void setValue(int value3) {
            this.value = value3;
        }

        @XmlAttribute(name = "age")
        public int age;
    }





    public static void main(String[] args) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(
                new User(Arrays.asList(new Field(1, 6), new Field(2, 9))),
                System.out
        );

//        StringWriter writer = new StringWriter();
//        jaxbMarshaller.marshal(
//                new Cat("kuzya", 1, 3),
//                //new User2(Arrays.asList(new Field(1), new Field(2))),
//                System.out
//        );

    }
}
