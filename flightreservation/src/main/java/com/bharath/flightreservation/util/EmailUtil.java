package com.bharath.flightreservation.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailUtil {
	@Value("${com.bharath.flightreservation.itinerary.email.body}")
	private String EMAIL_BODY = "Please find yor Itinerary attached.";

	@Value("${com.bharath.flightreservation.itinerary.email.subject}")
	private String EMAIL_SUBJECT = "Itinerary for you Flight";

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	@Autowired
	private JavaMailSender sender;
	
	public void sendItinerary(String toAddress, String filePath) {
		LOGGER.info("Inside sendIntinerary()");
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(toAddress);
			messageHelper.setSubject(EMAIL_SUBJECT);
			//messageHelper.addAttachment("Itinerary", new File(filePath));
			messageHelper.setText(EMAIL_BODY);
			FileSystemResource file = new FileSystemResource(new File(filePath));
			//System.out.println("llega archivo: " +filePath);
			//System.out.println(" ");
			//System.out.println("llega archivo 2: " +file.getFile());
			messageHelper.addAttachment("Itinerary", file.getFile());
			sender.send(message);
		} catch (MessagingException e) {
			//e.printStackTrace();
			LOGGER.error("Exception inside sendItinerary" + e);
		}
	}
}
