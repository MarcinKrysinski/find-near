package pl.krysinski.findnear;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.krysinski.findnear.model.PlaceDTO;
import pl.krysinski.findnear.model.api.PlaceUrl;
import pl.krysinski.findnear.model.api.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ApiClient {


    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        List<PlaceDTO> placeDtoList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        PlaceUrl object = restTemplate.getForObject("https://maps.googleapis.com/maps/api/place/textsearch/json?query=kebab&location=54.385327,18.606870&radius=10000&key=AIzaSyCPl_X8_l5ZBIQYLKCotZUV2uw4T6K93Xk", PlaceUrl.class);
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
}
