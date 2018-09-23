package nonblocking;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Неблокирующие кэш
 * Выполняется алгоритм изменения данных в data
 * если после выполнения алгоритма до записи данные изменились, алгоритм прерывается и выкидывается OptimisticException
 * @autor Андрей
 * @since 14.08.2018
 */
public class NonBlockCach {

    private ConcurrentHashMap<UUID, Base> data = new ConcurrentHashMap<>();

    public void add(Base model) {
        if (data.get(model.getId()) != null || data.putIfAbsent(model.getId(), model) != null) {
            throw new OptimisticException("Уже существует!");
        }
    }

    public void update(Base model) {
        BiFunction<UUID, Base, Base> function = (key, value) -> {
            if (value.getVersion() != data.get(key).getVersion()) {
                throw new OptimisticException("Объект изменен!");
            }
            model.setVersion(value.getVersion() + 1);
            return model;
        };
        data.computeIfPresent(model.getId(), function);
    }

    public void delete(Base model) {
        UUID key = model.getId();
        if (!data.remove(key, model)) {
            throw new OptimisticException("Объект не существует!");
        }
    }

    public class Base {
        private UUID id;
        private int version = 0;
        private String name;
        private String description;

        public Base(String name) {
            this.id = UUID.randomUUID();
            this.name = name;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public void setDescription(String description) {
            this.version++;
            this.description = description;
        }

        public void setName(String name) {
            this.version++;
            this.name = name;
        }

        public int getVersion() {
            return version;
        }

        public String getName() {
            return name;
        }

        public UUID getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Base base = (Base) o;
            return version == base.version
                    && Objects.equals(id, base.id)
                    && Objects.equals(name, base.name)
                    && Objects.equals(description, base.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, version, name, description);
        }
    }

    public class OptimisticException extends RuntimeException {
        public OptimisticException(String message) {
            super(message);
        }
    }
}
