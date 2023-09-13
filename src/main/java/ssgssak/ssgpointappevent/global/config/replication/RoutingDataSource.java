package ssgssak.ssgpointappevent.global.config.replication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    // 현재 db 연결을 결정하기 위해 호출하는 메서드
    @Override
    protected Object determineCurrentLookupKey() {

        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (isReadOnly) {
            log.info("Slave DataSource 호출 => ");
        } else {
            log.info("Master DataSource 호출 => ");
        }

        // 현재 트랜잭션이 읽기 전용인 경우는 slave, 아닌경우는 master를 반환 -> 트랜잭션 속성에 따라 db 연결을 결정
        return isReadOnly ? "slave" : "master";
    }
}
