package com.oc.p7v2batch.p7v2batch.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.p7v2batch.p7v2batch.bean.BookBean;
import com.oc.p7v2batch.p7v2batch.bean.BorrowBean;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@NoArgsConstructor
@Log4j2
@Component
public class BookRetrieverUtil {
    public List<BookBean> getAllBooks() throws IOException {
        log.info("in BookRetrieverUtil in getAllBooks");
        String url = "http://localhost:8083/books";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        int responseCode = connection.getResponseCode();
        log.info("in BookRetrieverUtil in getAllBooks sending GET request to url {} ", url);
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = reader.readLine()) != null) {
            log.info("in BookRetrieverUtil in getAllBooks in while reader.readLine() loop ");
            response.append(inputline);
        }
        reader.close();

        String jsonArray = response.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BookBean> bookBeanList = objectMapper.readValue(jsonArray, new TypeReference<List<BookBean>>() {
        });

        for (BookBean bookBean : bookBeanList) {
            log.info("in BookRetrieverUtil in getAllBooks in bookBeanList foreach loop where bookTitle is {} NumberOfReservation is {} and numberOfCopiesAvailable is {} ", bookBean.getTitle(), bookBean.getNumberOfReservation(), bookBean.getNumberOfCopiesAvailable());
        }
        return bookBeanList;
    }

}
