package store;

import java.util.Objects;

/**
 * @autor Андрей
 * @since 01.08.2018
 */
public class Info {

    public Info(int added, int changed, int deleted) {
        this.added = added;
        this.changed = changed;
        this.deleted = deleted;
    }

    public Info() {
        this.added = 0;
        this.changed = 0;
        this.deleted = 0;
    }

    private int added;
    private int changed;
    private int deleted;

    public void addAdded(int n) {
        this.added += n;
    }

    public void addChanged(int n) {
        this.changed += n;
    }

    public void addDeleted(int n) {
        this.deleted += n;
    }

    public int getAdded() {
        return added;
    }

    public int getChanged() {
        return changed;
    }

    public int getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Info info = (Info) o;
        return added == info.added
                && changed == info.changed
                && deleted == info.deleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(added, changed, deleted);
    }
}
