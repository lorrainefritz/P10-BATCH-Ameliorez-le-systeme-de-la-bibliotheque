package com.oc.p7v2batch.p7v2batch.util;


import com.oc.p7v2batch.p7v2batch.bean.ReservationBean;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;

@Log4j2
public class ReservationIdsComparator implements Comparator <ReservationBean> {

    public int compare(ReservationBean r1, ReservationBean r2){
        log.info("in ReservationBeanIdsComparator in compare => ascending order");
        //sort by ascending order
        return r1.getId()-r2.getId();
    }


}
