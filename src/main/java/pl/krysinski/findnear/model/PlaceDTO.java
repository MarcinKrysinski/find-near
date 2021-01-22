package pl.krysinski.findnear.model;


public class PlaceDTO {

    private String name;

    private String formattedAddress;

    private Double lat;

    private Double lng;

    private Boolean openNow;

    private Integer priceLevel;

    private Double rating;

    private Integer userRatingsTotal;

    public PlaceDTO(String name, String formattedAddress, Double rating, Double lat, Double lng, Boolean openNow) {
        this.name = name;
        this.formattedAddress = formattedAddress;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
        this.openNow = openNow;
    }

    public PlaceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(Integer userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "name='" + name + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", openNow=" + openNow +
                ", priceLevel=" + priceLevel +
                ", rating=" + rating +
                ", userRatingsTotal=" + userRatingsTotal +
                '}';
    }
}
