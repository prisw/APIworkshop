package visa.vttp.day17ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    public String title;
    public String urlToImage;
    public String author;
    public String description;
    public String publishedAt;
    public String articleUrl;

    // public String getTitle() {return title;}
    // public void setTitle(String title) {this.title = title;}

    // public String getUrlToImage() {return urlToImage;}
    // public void setUrlToImage(String urlToImage) {this.urlToImage = urlToImage;}

    // public String getAuthor() {return author;}
    // public void setAuthor(String author) {this.author = author;}

    // public String getDescription() {return description;}
    // public void setDescription(String description) {this.description = description;}

    // public String getPublishedAt() {return publishedAt;}
    // public void setPublishedAt(String publishedAt) {this.publishedAt = publishedAt;}

    // public String getUrl() {return url;}
    // public void setUrl(String url) {this.url = url;}


    // @Override
    // public String toString() {
    //     return "News [title=" + title + ", urlToImage=" + urlToImage + ", author=" + author + ", description="
    //             + description + ", publishedAt=" + publishedAt + ", url=" + url + "]";
    // } 
}
