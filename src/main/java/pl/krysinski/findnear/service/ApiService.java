package pl.krysinski.findnear.service;

import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.krysinski.findnear.model.PlaceDTO;
import pl.krysinski.findnear.model.api.PlaceUrl;
import pl.krysinski.findnear.model.api.Result;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    @Value("${api-key}")
    private String apiKey;
    private String type;
    private String lat;
    private String lng;
    private String separator = ",";
    private String rad = "10000";
    private static final String GOOGLE_MAPS_BASE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    public void setType(String type) {
        this.type = type;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<PlaceDTO> getPlacesListFromApi() {
        List<PlaceDTO> placeDtoList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        PlaceUrl object = restTemplate.getForObject(encodeUrl(), PlaceUrl.class);
        object.getResults().forEach(o -> placeDtoList.add(modelMapper().map(o, PlaceDTO.class)));
        return placeDtoList;
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
            uriBuilder.addParameter("query", type);
            uriBuilder.addParameter("location", lat + separator + lng);
            uriBuilder.addParameter("radius", rad);
            uriBuilder.addParameter("key", apiKey);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    private String encodeUrl() {
        String str = String.valueOf(getApiUrl());
        String encodeStr = str.replace("%2C%2C",",");
        return encodeStr;
    }


}
