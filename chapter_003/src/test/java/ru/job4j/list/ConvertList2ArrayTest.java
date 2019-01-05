package ru.job4j.list;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей
 * @since 28.06.2018
 */
public class ConvertList2ArrayTest {

    private ConvertList2Array convertList = new ConvertList2Array();

    @Test
    public void when7ElementsThen9() {
        int[][] result = convertList.toArray(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                3
        );
        int[][] expect = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 0}
        };
        assertThat(result, is(expect));
    }

    @Test
    public void when3ArrayConvertArrayList() {
        List<int[]> list = List.of(
                new int[]{1, 2},
                new int[]{3, 4, 5, 6},
                new int[]{7, 8, 9}
        );
        List<Integer> result = convertList.convert(list);
        assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
}
