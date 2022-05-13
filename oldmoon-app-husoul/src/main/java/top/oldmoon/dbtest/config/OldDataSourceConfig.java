package top.oldmoon.dbtest.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "top.oldmoon.dbtest.dao", sqlSessionFactoryRef = "oldSqlSessionFactory")
public class OldDataSourceConfig {

    @Bean(name = "oldDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.old")
    public DataSource gfDataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "oldSqlSessionFactory")
    public SqlSessionFactory gfSqlSessionFactory(@Qualifier("oldDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean(name = "oldTransactionManager")
    public DataSourceTransactionManager gfTransactionManager(@Qualifier("oldDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oldSqlSessionTemplate")
    public SqlSessionTemplate gfSqlSessionTemplate(
            @Qualifier("oldSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
