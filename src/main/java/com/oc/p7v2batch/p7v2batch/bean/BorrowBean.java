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
public class BorrowBean {
    private Integer id;
    private Date startDate;
    private Date returnDate;
    private String username;
    private String lastName;
    private String firstName;
    private boolean alreadyExtended;
    private boolean outdated;
    private String bookTitle;
    private String bookAuthor;
    private String libraryName;
    private String openingTime;
}
