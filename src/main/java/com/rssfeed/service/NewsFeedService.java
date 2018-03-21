package com.rssfeed.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rssfeed.model.NewsItem;
import com.rssfeed.repository.FeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsFeedService {

    private static final Logger logger = LoggerFactory.getLogger(NewsFeedService.class);

    private static final String URL = "http://feeds.nos.nl/nosjournaal?format=xml";

    private static final int MAXNUMBEROFFEEDS = 10;

    private FeedRepository feedRepository;

    public NewsFeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public void executeNewsFeedJob() {
        try (XmlReader reader = new XmlReader(new URL(URL));
             Stream<SyndEntry> syndEntryStream = new SyndFeedInput().build(reader).getEntries().stream()) {

            List<NewsItem> newsItems = syndEntryStream.map(this::convertToFeedItem).limit(MAXNUMBEROFFEEDS).collect(Collectors.toList());

            saveOrUpdateRssFeeds(newsItems);
            logger.debug("End and save RSS feeds job end");
        } catch (IOException e) {
            logger.error("Can not read RSS feeds ", e);
        } catch (FeedException e) {
            logger.error("Can not read RSS feeds ", e);
        } catch (IllegalArgumentException e) {
            logger.error("Unexpected error ", e);
        }
    }

    private NewsItem convertToFeedItem(SyndEntry feed) {
        NewsItem newsItem = new NewsItem();
        newsItem.setDescription(feed.getDescription().getValue());
        newsItem.setImage(getImageFromURL(feed));
        newsItem.setTitle(feed.getTitle());
        newsItem.setPublicationDate(feed.getPublishedDate());
        newsItem.setUri(feed.getUri());
        return newsItem;
    }


    public void saveOrUpdateRssFeeds(List<NewsItem> itemList) {
        logger.info("Start of save or update feeds");

        for (NewsItem newsItem : itemList) {

            NewsItem newsItemFromDb = getExistingFeedItem(newsItem);

            saveOrUpdateRssFeed(itemList, newsItem, newsItemFromDb);
        }

    }

    @Transactional(readOnly = true)
    protected NewsItem getExistingFeedItem(NewsItem newsItem) {
        logger.debug("Get feed from db with publication date = {}, title = {} and URI = {}",
                newsItem.getPublicationDate(), newsItem.getTitle(), newsItem.getUri());

        return feedRepository.findFeedItemByUri(newsItem.getUri());
    }

    @Transactional
    protected void saveOrUpdateRssFeed(List<NewsItem> itemList, NewsItem newsItem, NewsItem newsItemFromDb) {
        if (newsItemFromDb == null) {
            logger.debug("New feed with URI = {} saved in DB", newsItem.getUri());
            feedRepository.save(itemList);
        } else {
            logger.debug("Existing feed with  URI = {} updated in DB", newsItem.getUri());
            updateDbFeedItem(newsItem, newsItemFromDb);
            feedRepository.save(newsItemFromDb);
        }
    }

    private void updateDbFeedItem(NewsItem newsItem, NewsItem newsItemFromDb) {
        newsItemFromDb.setDescription(newsItem.getDescription());
        newsItemFromDb.setImage(newsItem.getImage());
        newsItemFromDb.setPublicationDate(newsItem.getPublicationDate());
        newsItemFromDb.setTitle(newsItem.getTitle());
        newsItemFromDb.setUri(newsItem.getUri());
    }


    private byte[] getImageFromURL(SyndEntry feed) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            URL imageURL = new URL(feed.getEnclosures().get(0).getUrl());
            BufferedImage originalImage = ImageIO.read(imageURL);
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Could not read image ", e);
        }
        return null;
    }

}
