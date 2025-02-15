package ding.co.backendportfolio.chapter5improved._3_data_processing.filter;

import ding.co.backendportfolio.chapter5.util.FilterUtil;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class FilterImprovedService {

    private static final int START_INCLUSIVE = 0;
    private static final int END_EXCLUSIVE = 1_000_000;

    /**
     * - 필터를 오름차순으로 최적화 버전
     */
    public void filter() {
        IntStream.range(START_INCLUSIVE, END_EXCLUSIVE)
                .filter(number -> FilterUtil.filterScore2())
                .filter(number -> FilterUtil.filterScore4())
                .filter(number -> FilterUtil.filterScore20())
                .filter(number -> FilterUtil.filterScore40())
                .sum();
    }
}
