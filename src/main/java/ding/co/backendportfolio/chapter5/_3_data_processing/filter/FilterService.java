package ding.co.backendportfolio.chapter5._3_data_processing.filter;

import ding.co.backendportfolio.chapter5.util.FilterUtil;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class FilterService {

    private static final int START_INCLUSIVE = 0;
    private static final int END_EXCLUSIVE = 1_000_000;

    /**
     * - 스코어를 내림차순 정렬해서 비효율적으로 만든다.
     */
    public void filter() {
        IntStream.range(START_INCLUSIVE, END_EXCLUSIVE)
                .filter(number -> FilterUtil.filterScore40())
                .filter(number -> FilterUtil.filterScore20())
                .filter(number -> FilterUtil.filterScore4())
                .filter(number -> FilterUtil.filterScore2())
                .sum();
    }
}
