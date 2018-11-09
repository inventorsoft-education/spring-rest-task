package spring.rest.task.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailRepository {
    private String filename;
    private List<SimpleMailMessage> emailList;
    public EmailRepository(@Value("${email.filename}") String filename){
        this.filename = filename;
        this.emailList = new ArrayList<>();
        if(!Files.exists(Paths.get(filename))){
            try {
                Files.createFile(Paths.get(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            emailList = (ArrayList<SimpleMailMessage>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<SimpleMailMessage> findAll(){
        return emailList;
    }
    public void deleteAll(){
        emailList.clear();
    }
    public void delete(SimpleMailMessage simpleMailMessage){
        emailList.remove(simpleMailMessage);
    }
    public void save(){
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(filename))) {
            ous.writeObject(emailList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(List<SimpleMailMessage> simpleMailMessages){
        emailList = simpleMailMessages;
    }
    public void save(SimpleMailMessage simpleMailMessage){
        emailList.add(simpleMailMessage);
    }
}
