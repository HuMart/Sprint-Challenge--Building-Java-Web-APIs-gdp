package com.lambda.countries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GdpApplication
{
    public static GdpList ourGdpList;
    public static void main(String[] args)
    {
        ourGdpList = new GdpList();
        SpringApplication.run(GdpApplication.class, args);

    }
}
