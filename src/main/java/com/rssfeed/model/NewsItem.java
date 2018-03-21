package com.rssfeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "news_item")
public class NewsItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;

    private String title;

    @Column(length = 10000)
    private String description;

    private Date publicationDate;

 //   private Date updateDate;

    private String uri;

    @Lob
    private byte[] image;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        NewsItem newsItem = (NewsItem) o;
//
//        return new EqualsBuilder().append(title, newsItem.title).append(description, newsItem.description)
//                .append(publicationDate, newsItem.publicationDate).append(updateDate, newsItem.updateDate)
//                .append(uri, newsItem.uri).append(image, newsItem.image).isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 37).append(title).append(description).append(publicationDate).append(updateDate)
//                .append(uri).append(image).toHashCode();
//    }
}
