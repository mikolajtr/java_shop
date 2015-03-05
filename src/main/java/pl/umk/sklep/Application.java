package pl.umk.sklep;

import com.lyncode.jtwig.mvc.JtwigViewResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {
//aby sie polaczyc wystarczy wpisac w przegladarce localhost:8080/nazwa_widoku

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ViewResolver viewResolver() {
        JtwigViewResolver viewResolver = new JtwigViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".twig");
        viewResolver.setUseThemeInViewPath(true);
        return viewResolver;
    }

    @Bean
    public String error() {

        return "error";
    }
}
