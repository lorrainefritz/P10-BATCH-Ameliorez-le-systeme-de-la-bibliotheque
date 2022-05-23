package com.oc.p7v2batch.p7v2batch.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.p7v2batch.p7v2batch.bean.BorrowBean;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.util.List;

@NoArgsConstructor
@Log4j2
@Component
public class BorrowRetrieverUtil {

    public List<BorrowBean> getAllBorrows() throws IOException {
        log.info("in BorrowRetrieverUtil in getAllBorrows");
        String url = "http://localhost:8083/allBorrows";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        int responseCode = connection.getResponseCode();
        log.info("in BorrowRetrieverUtil in getAllBorrows sending GET request to url {} ", url);
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = reader.readLine()) != null) {
            log.info("in BorrowRetrieverUtil in getAllBorrows in while reader.readLine() loop ");
            response.append(inputline);
        }
        reader.close();

        String jsonArray = response.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BorrowBean> borrowBeanList = objectMapper.readValue(jsonArray, new TypeReference<List<BorrowBean>>() {
        });

        for (BorrowBean borrow : borrowBeanList) {
            log.info("in BorrowRetrieverUtil in getAllBorrows in borrowBeanList foreach loop where username is {} bookTitle is {} and libraryName is {} ", borrow.getUsername(), borrow.getBookTitle(), borrow.getLibraryName());
        }
        return borrowBeanList;
    }


}
