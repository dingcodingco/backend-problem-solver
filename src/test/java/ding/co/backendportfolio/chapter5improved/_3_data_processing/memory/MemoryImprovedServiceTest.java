package ding.co.backendportfolio.chapter5improved._3_data_processing.memory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoryImprovedServiceTest {

    @Autowired
    private MemoryImprovedService memoryImprovedService;

    @DisplayName("초기 사이즈를 지정하여 최적화한 컬렉션 초기화")
    @RepeatedTest(30)
    public void testExecute() {
        memoryImprovedService.executeImproved();
    }
}
