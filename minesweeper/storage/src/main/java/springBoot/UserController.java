package springBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private final UserService userService;
    private static int portNumber;

    public static final void isTest(boolean test) {
        if (test) {
            portNumber = 8080;
        } else {
            portNumber = 6969;
        }
    }

    @Autowired
    public UserController(UserService userService){
        isTest(false);
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "Welcome to the springboot application.\n\nUse /user?id=1 to get a user.";
    }

    @GetMapping("/user")
    public User getUser(@RequestParam Integer id){
        Optional<User> user = userService.getUser(id);
        return (User) user.orElse(null);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(portNumber);
    }
}
