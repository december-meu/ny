package com.geovis;

import com.geovis.metadata.support.annotation.EnableGeovisMetaData;
import com.spring4all.swagger.EnableSwagger2Doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */


@SpringBootApplication
@EnableSwagger2Doc
@EnableEurekaClient
@EnableDiscoveryClient
@EnableGeovisMetaData
public class App {
    public static void main(String[] args) {


        SpringApplication application = new SpringApplication(App.class);
        ConfigurableApplicationContext context = application.run(args);


        // GeovisMetaData geovisMetaData = context.getBean(GeovisMetaData.class);

        System.out.println("geovisMetaData");
    }
}
