package com.example.demostopcontextmanually;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class DemostopcontextmanuallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemostopcontextmanuallyApplication.class, args);
	}

	//@PreDestroy
	public void onDestroy() throws Exception {
		Thread.sleep(10000);
		System.out.println("Spring Container is destroyed!");
	}

}

@RestController
class ApplicationController {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DependentBean dependentBean;

	@Autowired
	private BeanFactory beanFactory;

	@GetMapping("ping")
	public String ping(){
		dependentBean.dependenPing();
		return "PONG";
	}

	@GetMapping("stop")
	public void stopApplicationContext(){
		((ConfigurableApplicationContext)applicationContext).close();
	}


}

@Service
class DependentBean implements DisposableBean {

	public String dependenPing(){
		return "dependent";
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Destroying");
	}
}