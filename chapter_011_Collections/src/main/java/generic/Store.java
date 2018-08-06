package generic;

/**
 * @autor Андрей
 * @since 10.07.2018
 */
public interface Store<T extends Base> {

    void add(T model);

    boolean replace(String id, T model);

    boolean delete(String id);

    T findById(String id);
}
