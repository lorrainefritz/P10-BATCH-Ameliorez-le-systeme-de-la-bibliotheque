package com.oc.p7v2batch.p7v2batch.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class BookBean {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private String type;
    private String summary;
    private int numberOfCopiesAvailable;
    private String libraryName;
    private Date nearestReturnDate;
    private int numberOfReservation;
    private int maxReservationListSize;

}
