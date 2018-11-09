package spring.rest.task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.rest.task.repository.EmailRepository;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class Application {
    EmailRepository emailRepository;
    public static void main(String[] args){
     SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    void saveEmailList(){
       emailRepository.save();
    }
}
