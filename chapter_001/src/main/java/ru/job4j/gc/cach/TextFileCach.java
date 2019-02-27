package ru.job4j.gc.cach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @autor aoliferov
 * @since 23.02.2019
 */
public class TextFileCach extends Cach<String, String> {

    private String path;

    public TextFileCach(String path) {
        this.path = path;
    }

    @Override
    protected String load(String name) {
        String result = "";
        try (BufferedReader rf = new BufferedReader(new FileReader(path.concat(name)))) {
            String temp = rf.readLine();
            while (temp != null) {
                result = result.concat(temp).concat(System.lineSeparator());
                temp = rf.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
}
