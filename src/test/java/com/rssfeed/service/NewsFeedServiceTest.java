package com.rssfeed.service;

import com.rssfeed.repository.FeedRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsFeedServiceTest {

    @Autowired
    private NewsFeedService newsFeedService;

    @Autowired
    private FeedRepository feedRepository;

//    @Test
//    public void readRssFeeds() throws Exception {
//        List<NewsItem> feedItems = newsFeedService.readRssFeeds();
//        assertFalse(feedItems.isEmpty());
//
//    }
//
//    @Test
//    public void saveOrUpdateRssFeeds() throws Exception {
//        feedRepository.deleteAll();
//        List<NewsItem> feedItems = newsFeedService.readRssFeeds();
//        assertFalse(feedItems.isEmpty());
//        newsFeedService.saveOrUpdateRssFeeds(feedItems);
//
//        List<NewsItem> feedItemsfromDB = feedRepository.findAll();
//        assertEquals(feedItems.size(),feedItemsfromDB.size());
//        for(int i=0;i<feedItems.size();i++){
//            assertEquals(feedItemsfromDB.get(i).getUri(), feedItems.get(i).getUri());
//            assertEquals(feedItemsfromDB.get(i).getTitle(), feedItems.get(i).getTitle());
//            assertEquals(feedItemsfromDB.get(i).getDescription(), feedItems.get(i).getDescription());
//            assertEquals(feedItemsfromDB.get(i).getUpdateDate(), feedItems.get(i).getUpdateDate());
//
//        }
//
//    }


}