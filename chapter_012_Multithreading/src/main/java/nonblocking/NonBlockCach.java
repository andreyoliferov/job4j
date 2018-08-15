package nonblocking;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @autor Андрей
 * @since 14.08.2018
 */
public class NonBlockCach {

    private ConcurrentHashMap<UUID, Base> data = new ConcurrentHashMap<>();

    public void add(Base model) {
        if (data.get(model.getId()) != null) {
            throw new OptimisticException("Уже существует!");
        }
        data.putIfAbsent(model.getId(), model);
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
        if (model.getVersion() != data.get(model.getId()).getVersion()) {
            throw new OptimisticException("Объект изменен или удален!");
        }
        data.remove(key, model);
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
    }

    public class OptimisticException extends RuntimeException {
        public OptimisticException(String message) {
            super(message);
        }
    }
}
