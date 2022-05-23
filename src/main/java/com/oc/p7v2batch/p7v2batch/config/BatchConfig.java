package com.oc.p7v2batch.p7v2batch.config;

import com.oc.p7v2batch.p7v2batch.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class BatchConfig {
    private final BatchService batchService;
    private boolean jobEnable = true;
    @Scheduled(cron=" * 21 12 * * *") // (s min h j m jourSemaine)
    public Job job() throws IOException {
        log.info("in BatchConfig in job method");
        if (jobEnable==true){
            log.info("in BatchConfig in job method when job is enable");
            batchService.batchProcessing();
            jobEnable= false;
         }
        return null;

    }
}
