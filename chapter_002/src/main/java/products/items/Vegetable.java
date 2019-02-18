package products.items;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public abstract class Vegetable extends Food {

    private StorageTemperature storageTemperature;

    public Vegetable(String name, BigDecimal price, LocalDate createDate, LocalDate expaireDate, StorageTemperature storageTemperature) {
        super(name, price, createDate, expaireDate);
        this.storageTemperature = storageTemperature;
    }

    public StorageTemperature getStorageTemperature() {
        return storageTemperature;
    }

    public static class StorageTemperature {
        private int from;
        private int to;

        public StorageTemperature(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }
    }
}
