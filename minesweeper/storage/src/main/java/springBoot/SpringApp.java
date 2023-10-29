package springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

@SpringBootApplication
public class SpringApp implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private static int portNumber;

    public static void isTest(boolean test) {
        if (test) {
            portNumber = 6969;
        } else {
            portNumber = 8080;
        }
    }

    public static void main(String[] args) {
        isTest(false);
        SpringApplication.run(SpringApp.class, args);
    }

    public static Object testRun() {
        isTest(true);
        return SpringApplication.run(SpringApp.class);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(portNumber);
    }
}
