package generic.returnNameGeneric;

import java.lang.reflect.ParameterizedType;

/**
 * @autor aoliferov
 * @since 08.11.2018
 */
public class ReflectionUtilsTest {

    private class A<T> {}

    private class B extends A<String> {}

    private class C<T> extends B {}

    private class D extends C<Object> {}

    public static void main(String[] args) throws Exception
    {
        Class<?> clazz = C.class;
        System.out.println(clazz.getGenericSuperclass() instanceof ParameterizedType);

        Class value = ReflectionUtils.getGenericParameterClass(D.class, A.class, 0);
        System.out.println(value.getName());
    }
}
