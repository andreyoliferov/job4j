package util;

import java.util.HashMap;

/**
 * @autor aoliferov
 * @since 15.12.2018
 */
public class Param {

    private HashMap<String, String[]> data = new HashMap<>();

    public Param(String key, String... value) {
        data.put(key, value);
    }

    public Param add(String key, String ... value) {
        data.put(key, value);
        return this;
    }

    public HashMap<String, String[]> get() {
        return data;
    }
}
