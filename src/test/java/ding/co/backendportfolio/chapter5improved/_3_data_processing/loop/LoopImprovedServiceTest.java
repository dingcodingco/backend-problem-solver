package ding.co.backendportfolio.chapter5improved._3_data_processing.loop;

import ding.co.backendportfolio.chapter5._3_data_processing.loop.LoopTestDataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoopImprovedServiceTest {

    @Autowired
    private LoopImprovedService loopImprovedService;

    @DisplayName("O(N) Loop 테스트")
    @RepeatedTest(20)
    void execute() {
        loopImprovedService.excuteImproved(LoopTestDataUtil.createTestData());
    }
}
