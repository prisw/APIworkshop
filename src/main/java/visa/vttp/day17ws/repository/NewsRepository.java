package visa.vttp.day17ws.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonArray;

@Repository
public class NewsRepository {


    @Autowired @Qualifier("newsCache")
    private RedisTemplate<String,String> template;

    @Value("${newsapi.cache.timeout.mins}")
    long timeout;

    public void cacheNews(String category, String country, JsonArray news) {
        String key = mkKey(category, country);
        template.opsForValue()
        .set(key, news.toString(), timeout, TimeUnit.MINUTES);
    }

    public Optional<String> getNewsOptional(String category, String country) {
        String key = mkKey(category, country);
        String value = template.opsForValue().get(key);
        return Optional.ofNullable(value);
    }

      private String mkKey(String category, String country) {
        return "%s-%s".formatted(category,country);
    }
    
}
