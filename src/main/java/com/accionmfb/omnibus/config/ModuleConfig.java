package com.accionmfb.omnibus.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@EnableEncryptableProperties
@EnableTransactionManagement
@EntityScan("com.accionmfb.omnibus.model")
@EnableConfigurationProperties(value = {EntityManagerProperties.class, DataSourceproperties.class})
public class ModuleConfig {

    private final EntityManagerProperties configurationProperties;
    private final DataSourceproperties dataSourceproperties;

    @Bean()
    public DataSource omniBusDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceproperties.getDriverClassName());
        dataSource.setUrl(dataSourceproperties.getUrl());
        dataSource.setUsername(dataSourceproperties.getUsername());
        dataSource.setPassword(dataSourceproperties.getPassword());
        return dataSource;
    }

    @Bean(name = "omniBusEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource omniBusDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(omniBusDataSource);
        em.setPackagesToScan(new String[]{"com.accionmfb.omnibus.model"});
        em.setPersistenceUnitName("omniBusPersistenceUnit");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", configurationProperties.getDdlAuto());
        properties.setProperty("hibernate.dialect", configurationProperties.getDialect());
        em.setJpaProperties(properties);

        return em;
    }

    @Bean(name = "omniBusTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean(name = "omniBusjasyptStringEncryptor")
    public StringEncryptor getPasswordEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("H*-lLo5,e:2.VN"); // encryptor's private key
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        return encryptor;
    }

}
