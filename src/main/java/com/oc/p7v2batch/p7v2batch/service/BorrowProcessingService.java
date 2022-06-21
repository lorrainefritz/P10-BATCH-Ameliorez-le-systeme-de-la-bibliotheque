package com.oc.p7v2batch.p7v2batch.service;

import com.oc.p7v2batch.p7v2batch.bean.BorrowBean;
import com.oc.p7v2batch.p7v2batch.util.BorrowRetrieverUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Log4j2
public class BorrowProcessingService {
    private final BorrowRetrieverUtil borrowRetrieverUtil;


    public void borrowBatchProcessing() throws IOException {
        log.info("in borrowBatchProcessing in batchProcessing method");
        List<BorrowBean> allBorrowList = borrowRetrieverUtil.getAllBorrows();
        Date today = Date.from(Instant.now());
        for (BorrowBean borrow : allBorrowList) {
            Date returnDate = borrow.getReturnDate();
            // SI les dates sont == retourne 0 si today>returnDate =-1
            // today<returnDate = 1
            if (today.compareTo(returnDate) > 0) {
                log.info("in borrowBatchProcessing in batchProcessing method in isOutdated from user " + borrow.getUsername());
                try {
                    sendmail(borrow);
                } catch (AddressException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (MessagingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
    private void sendmail(BorrowBean borrow) throws AddressException, MessagingException, IOException {
        log.info("in borrowBatchProcessing in sendmail method");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aehlinepomme@gmail.com", "fyarfjwtdcosywtl");
            }
        });
        String emailBody = "Bonjour " + borrow.getFirstName() + " " + borrow.getLastName() + ","
                + "\nLe livre " + borrow.getBookTitle() +" de " + borrow.getBookAuthor()
                + " est à rendre au plus vitre car sa date de retour est dépassée. \nMerci de le rendre au plus vite à la bibliothèque de "
                + borrow.getLibraryName() +"\nLes horaires d'ouverture sont : " +borrow.getOpeningTime()+"\nCordialement. \nLes Bibliothèques de Katzenheim";
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("aehlinepomme@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(borrow.getUsername()));
        msg.setSubject("Date de retour dépassée");
        msg.setText(emailBody);
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailBody, toString());
        Transport.send(msg);
    }
}