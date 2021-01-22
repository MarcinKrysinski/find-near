package pl.krysinski.findnear.controller;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pl.krysinski.findnear.model.PlaceDTO;
import pl.krysinski.findnear.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SpotControllerTest {

    @Test
    public void should_get_places() {

        ApiService apiService = mock(ApiService.class);
        when(apiService.getPlacesListFromApi()).thenReturn(prepareMockData());
        List<PlaceDTO> placeDtoList = apiService.getPlacesListFromApi();
        MatcherAssert.assertThat(placeDtoList, Matchers.hasSize(3));

    }

    private List<PlaceDTO> prepareMockData() {

        List<PlaceDTO> placeDtoList = new ArrayList<>();
        placeDtoList.add(new PlaceDTO("Kebab Sopocki od 1970", "Bohaterów Monte Cassino 58A, 81-759 Sopot, Poland", 3.6, 54.4442581, 18.568014, true));
        placeDtoList.add(new PlaceDTO("Kebab King", "aleja Grunwaldzka 491, 80-309 Gdańsk, Poland", 4.0, 54.4073702, 18.5700549, true));
        placeDtoList.add(new PlaceDTO("My Kebab", "Miszewskiego 18A, 80-239 Gdańsk, Poland", 4.0, 54.3766259, 18.615234, false));

        return placeDtoList;
    }

}
