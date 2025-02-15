package ding.co.backendportfolio.chapter5._3_data_processing.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

class FilterServiceTest {

    FilterService service = new FilterService();

    @DisplayName("내림차순 필터 순서 테스트")
    @RepeatedTest(20)
    void filter() {
        service.filter();
    }
}