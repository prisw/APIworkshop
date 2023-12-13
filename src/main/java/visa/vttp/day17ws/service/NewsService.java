package visa.vttp.day17ws.service;


import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import visa.vttp.day17ws.model.News;
import visa.vttp.day17ws.repository.NewsRepository;

@Service
public class NewsService {


    @Value("${newsapi.key}")
    private String NEWSAPI_KEY;

    @Autowired
    NewsRepository newsRepo;

     public List<String> getCategories() {
        String[] categories = {"business","entertainment","general","health","science","sports","technology"};
        return List.of(categories);
    }


    public Map<String,String> getCountries() {

        Map<String, String> countries = new HashMap<>();
        
         String[] countryCodes ={"ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp",
        "kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","us","ve","za"};
    
        String urlCountry = UriComponentsBuilder
        .fromUriString("https://restcountries.com/v3.1/alpha")
        .queryParam("codes", List.of(countryCodes))
        .build()
        .toString();

        RequestEntity<Void> result =  RequestEntity.get(urlCountry)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> respond = template.exchange(result, String.class);

        JsonReader jsonReader = Json.createReader(new StringReader(respond.getBody()));
        

        for(JsonValue jvalue: jsonReader.readArray()) {
            JsonObject jobject = jvalue.asJsonObject();
            String countryCode = jobject.getString("cca2");
            String countryName = jobject.getJsonObject("name").getString("common");
            countries.put(countryCode,countryName);
        } 
        return countries;    
    }


    public List<News> getNews(String category, String country){

        List<News> news = new LinkedList<>();
        String payload;
        JsonArray articles;
        Optional<String> opt = newsRepo.getNewsOptional(category, country);

        if(opt.isEmpty()) {

            String url = UriComponentsBuilder
            .fromUriString("https://newsapi.org/v2/top-headlines")
            .queryParam("country", country)
            .queryParam("category", category)
            .queryParam("apiKey", NEWSAPI_KEY)
            .build()
            .toString();
        

        RequestEntity<Void> request =  RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = null;

        try {
            response = template.exchange(request, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LinkedList<>();
        }

        payload = response.getBody();
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject result = jsonReader.readObject();
        articles = result.getJsonArray("articles");

    }
        else {
            System.out.println("-----------------result from cache-----------");
            payload = opt.get();
            JsonReader jsonReader = Json.createReader(new StringReader(payload));
            articles = jsonReader.readArray();
        }
        //to cache news

        if(opt.isEmpty())
        newsRepo.cacheNews(category, country, articles);
        
        for(JsonValue value : articles){
            JsonObject obj = value.asJsonObject();
            String title = obj.getString("title");
            String urlToImage = obj.getString("urlToImage", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/310px-Placeholder_view_vector.svg.png");
            String author = obj.getString("author", "Anonymous");
            String description = obj.getString("description","No description");
            String publishedAt = obj.getString("publishedAt");
            String articleUrl = obj.getString("url");
            news.add(new News(title, urlToImage, author, description, publishedAt, articleUrl));
        } return news;
    }    
}

