package pl.krysinski.findnear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.krysinski.findnear.model.PlaceDTO;
import pl.krysinski.findnear.service.ApiService;

import java.util.List;

@RestController
@RequestMapping(value="/places", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SpotController {

    private ApiService apiService;

    @Autowired
    public SpotController(ApiService apiService) {
        this.apiService = apiService;
    }

    @ResponseBody
    @PostMapping
    public List<PlaceDTO> getNearbyPlacesByType(@RequestParam(value = "lat") String lat, @RequestParam(value = "lng") String lng, @RequestParam(value = "type") String type){
        apiService.setLat(lat);
        apiService.setLng(lng);
        apiService.setType(type);
        List<PlaceDTO> placesList = apiService.getPlacesListFromApi();
        return placesList;
    }
}
