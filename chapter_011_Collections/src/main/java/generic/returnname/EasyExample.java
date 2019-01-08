package generic.returnname;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * @autor aoliferov
 * @since 07.11.2018
 */
public class EasyExample<T> extends ArrayList<String> {

    /**
     * В методе происходит выяснение обобщения у текущего класса после компиляции.
     * Необходимое условие - в родителе должен явно быть указан генерик.
     */
    public void getGeneric() throws Exception {
        Class<T> t = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T value = t.getDeclaredConstructor().newInstance();
        boolean b = value instanceof String;
        System.out.println(b);
    }

    public static void main(String[] args) throws Exception {
        EasyExample f = new EasyExample();
        f.getGeneric();
    }
}
