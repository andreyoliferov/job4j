package factory;

import javax.swing.text.Document;
import java.io.File;

/**
 * Pattern Factory
 * @autor aoliferov
 * @since 01.01.2019
 */
public class FactoryMethod {

    public AbstractWriter getWriter(Object object) {
        AbstractWriter writer = null;
        if (object instanceof File) {
            writer = new ConcreteFileWriter();
        } else if (object instanceof Document) {
            writer = new ConcreteXmlWriter();
        }
        return writer;
    }
}
