package com.oc.p7v2batch.p7v2batch.service;

import com.oc.p7v2batch.p7v2batch.bean.BookBean;
import com.oc.p7v2batch.p7v2batch.bean.BorrowBean;
import com.oc.p7v2batch.p7v2batch.bean.ReservationBean;
import com.oc.p7v2batch.p7v2batch.util.BookRetrieverUtil;
import com.oc.p7v2batch.p7v2batch.util.ReservationIdsComparator;
import com.oc.p7v2batch.p7v2batch.util.ReservationRetrieverUtil;
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
public class ReservationProcessingService {
    private final ReservationRetrieverUtil reservationRetrieverUtil;

    public void reservationBatchProcessing() throws IOException {
        log.info("in reservationBatchProcessing in reservationBatchProcessing method");
        List<ReservationBean> reservationsToSendAnEmailTo = reservationRetrieverUtil.getAllReservationsToRetrieveFromLibrary();

        for (ReservationBean currentReservation:reservationsToSendAnEmailTo) {
            log.info("in reservationBatchProcessing in reservationBatchProcessing method in for each reservation where currentReservation id is{} book title is {} and user firstName is {}", currentReservation.getId(), currentReservation.getBookId(), currentReservation.getFirstName());

            try {
                sendmail(currentReservation);
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


    private void sendmail(ReservationBean reservationBean) throws AddressException, MessagingException, IOException {
        log.info(" in reservationBatchProcessing in sendmail method");
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
        String emailBody = "Bonjour " + reservationBean.getFirstName() + " " + reservationBean.getLastName() + ","
                + "\nLe livre " + reservationBean.getBookTitle() + " de " + reservationBean.getBookAuthor()
                + " est de nouveau disponible. \nMerci de venir au plus vite à la bibliothèque de "
                + reservationBean.getLibraryName() + " pour le récupérer. Il est mis de côté pour vous jusqu'au " + reservationBean.getEndDate()
                + ". Passé ce délai votre réservation sera automatiquement annulée!"
                + "\n Les horaires d'ouverture sont : " + reservationBean.getOpeningTime() + "\nCordialement. \nLes Bibliothèques de Katzenheim";
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("aehlinepomme@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reservationBean.getUsername()));
        msg.setSubject("Le livre que vous avez réservé est disponible au retrait");
        msg.setText(emailBody);
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailBody, toString());
        Transport.send(msg);
    }

}
