package ssgssak.ssgpointappevent.global.config.replication;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {

    // DB 연결을 결정하는 데 사용하는 상수를 정의
    private static final String MASTER_DATASOURCE = "masterDataSource";
    private static final String SLAVE_DATASOURCE = "slaveDataSource";

    // masterDataSource Bean을 생성
    @Bean(MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari") // 접두사로 시작하는 속성을 사용해서 Bean을 구성
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                // HikariDataSource 타입의 DataSource 객체를 생성함
                .type(HikariDataSource.class)
                .build();
    }

    // slaveDataSource Bean을 생성
    @Bean(SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari") // 접두사로 시작하는 속성을 사용해서 Bean을 구성
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                // HikariDataSource 타입의 DataSource 객체를 생성함
                .type(HikariDataSource.class)
                .build();
    }


    @Bean
    public DataSource routingDataSource(
            // masterDataSource와 slaveDataSource를 주입받는다
            @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
            @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> datasourceMap = ImmutableMap.<Object, Object>builder()
                .put("master", masterDataSource)
                .put("slave", slaveDataSource)
                .build();

        // RoutingDataSource의 대상 데이터 소스를, 위에서 생성한 맵으로 지정
        routingDataSource.setTargetDataSources(datasourceMap);
        // 기본 대상 데이터 소스를, masterDataSource로 설정
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    // 동일한 타입의 여러 Bean중에서 우선적으로 사용되는 기본 Bean을 설정
    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        // 지연 연결 기능을 제공하기 위해서 사용한다 -> DB 연결의 지연 실행을 지원하고, 필요한 시점에서만 연결을 수행하도록 구성한다.
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
