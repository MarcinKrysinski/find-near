package pl.krysinski.findnear;

import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.krysinski.findnear.model.PlaceDTO;
import pl.krysinski.findnear.model.api.PlaceUrl;
import pl.krysinski.findnear.model.api.Result;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiClient {

    @Value("${api-key}")
    private String apiKey;
    private String query = "kebab";
    private String lat = "54.3507477,";
    private String lng = "18.6495267";
    private String separator = ",";
    private String rad = "10000";
    private static final String GOOGLE_MAPS_BASE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        List<PlaceDTO> placeDtoList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        PlaceUrl object = restTemplate.getForObject(encodeUrl(), PlaceUrl.class);
        object.getResults().forEach(o -> placeDtoList.add(modelMapper().map(o, PlaceDTO.class)));
        System.out.println("Lista: " + placeDtoList);
    }


    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Result, PlaceDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setFormattedAddress(source.getFormattedAddress());
                map().setLat(source.getGeometry().getLocation().getLat());
                map().setLng(source.getGeometry().getLocation().getLng());
                map().setOpenNow(source.getOpeningHours().getOpenNow());
                map().setPriceLevel(source.getPriceLevel());
                map().setRating(source.getRating());
                map().setUserRatingsTotal(source.getUserRatingsTotal());
            }
        });

        return modelMapper;
    }

    private URI getApiUrl() {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(GOOGLE_MAPS_BASE_API_URL);
            uriBuilder.addParameter("query", query);
            uriBuilder.addParameter("location", lat + separator + lng);
            uriBuilder.addParameter("radius", rad);
            uriBuilder.addParameter("key", apiKey);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String encodeUrl() {
        String str = String.valueOf(getApiUrl());
        String encodeStr = str.replace("%2C%2C",",");
        return encodeStr;
    }


}
