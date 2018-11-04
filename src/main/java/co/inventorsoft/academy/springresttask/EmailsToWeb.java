package co.inventorsoft.academy.springresttask;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailsToWeb {
	
	public List<VerySimpleMail> convert(List<SimpleMailMessage> emails) {
		return IntStream.range(0,emails.size()).mapToObj( i -> new VerySimpleMail(i,emails.get(i)) ).collect(toList());
	}

}
