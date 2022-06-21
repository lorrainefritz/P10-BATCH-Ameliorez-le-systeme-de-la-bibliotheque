package com.oc.p7v2batch.p7v2batch.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.p7v2batch.p7v2batch.bean.BorrowBean;
import com.oc.p7v2batch.p7v2batch.bean.ReservationBean;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@NoArgsConstructor
@Log4j2
@Component
public class ReservationRetrieverUtil {
    public List<ReservationBean> getAllReservationsToRetrieveFromLibrary() throws IOException {
        log.info("in ReservationRetrieverUtil in getAllReservations");
        String url = "http://localhost:8083/users/allReservationsToRetrieveFromLibrary";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        int responseCode = connection.getResponseCode();
        log.info("in ReservationRetrieverUtil in getAllReservationsToRetrieve sending GET request to url {} ", url);
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = reader.readLine()) != null) {
            log.info("in ReservationRetrieverUtil in getAllReservationsToRetrieve in while reader.readLine() loop ");
            response.append(inputline);
        }
        reader.close();

        String jsonArray = response.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReservationBean> reservationBeanList = objectMapper.readValue(jsonArray, new TypeReference<List<ReservationBean>>() {
        });

        for (ReservationBean reservationBean : reservationBeanList) {
            log.info("in ReservationRetrieverUtil in getAllReservationsToRetrieve foreach loop where username is {} bookTitle is {} and libraryName is {} ", reservationBean.getUsername(), reservationBean.getBookTitle(), reservationBean.getLibraryName());
        }
        return reservationBeanList;

    }
}
