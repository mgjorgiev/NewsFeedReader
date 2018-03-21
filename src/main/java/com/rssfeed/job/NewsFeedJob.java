package com.rssfeed.job;

import com.rssfeed.service.NewsFeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsFeedJob {

    private static final Logger logger = LoggerFactory.getLogger(NewsFeedJob.class);

    private NewsFeedService newsFeedService;

    public NewsFeedJob(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void newsFeedsJob() {
        logger.info("Read and save RSS feeds job started");

        newsFeedService.executeNewsFeedJob();

        logger.info("Read and save RSS feeds job ended");

    }


}
