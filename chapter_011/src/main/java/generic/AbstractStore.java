package generic;

/**
 * @autor Андрей
 * @since 10.07.2018
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> data = new SimpleArray<>(100);

    @Override
    public void add(T model) {
        data.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        T t = this.findById(id);
        return t != null && data.set(t, model);
    }

    @Override
    public boolean delete(String id) {
        T t = this.findById(id);
        return t != null && data.delete(t);
    }

    @Override
    public T findById(String id) {
        Base finded = null;
        for (Base base : data) {
            if (base.getId().equals(id)) {
                finded = base;
                break;
            }
        }
        return (T) finded;
    }






}
