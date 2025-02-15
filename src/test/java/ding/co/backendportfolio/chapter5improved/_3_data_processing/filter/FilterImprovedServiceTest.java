package ding.co.backendportfolio.chapter5improved._3_data_processing.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

class FilterImprovedServiceTest {

    FilterImprovedService filterImprovedService = new FilterImprovedService();

    @DisplayName("오름차순 필터 테스트")
    @RepeatedTest(20)
    void filterImproved() {
        filterImprovedService.filter();
    }
}