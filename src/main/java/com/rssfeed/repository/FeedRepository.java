package com.rssfeed.repository;

import com.rssfeed.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<NewsItem, Short> {

    NewsItem findFeedItemByUri(String uri);

}
