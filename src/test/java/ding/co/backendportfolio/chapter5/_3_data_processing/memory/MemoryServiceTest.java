package ding.co.backendportfolio.chapter5._3_data_processing.memory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoryServiceTest {

    @Autowired
    private MemoryService memoryService;

    @DisplayName("일반적인 컬렉션 초기화")
    @RepeatedTest(30)
    public void testExecute() {
        memoryService.execute();
    }
}
