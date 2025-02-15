package ding.co.backendportfolio.chapter5.async_operation.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    /**
     * 1. 실행 전 MDC 컨텍스트 설정
     * - 현재 스레드의 MDC 컨텍스트를 복사하여 새로운 스레드에 설정
     * - 만약 복사한 컨텍스트가 없다면 MDC를 초기화
     * 2. 비동기 작업 실행
     * 3. 실행 후 MDC 컨텍스트 초기화
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        // 메서드를 호출한 '현재 스레드'의 MDC 컨텍스트를 복사
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            try {
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                } else {
                    MDC.clear();
                }

                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}