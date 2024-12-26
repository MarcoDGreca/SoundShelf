package entity;

import java.util.List;

public class Product {

    private int productCode;
    private String name;
    private List<Artist> artists;  // Lista di artisti
    private String releaseDate;
    private String description;
    private boolean availability;
    private double salePrice;
    private double originalPrice;
    private String supportedDevice;
    private List<Genre> genres;  // Lista di generi
    private String image;

    // Costruttore completo
    public Product(int productCode, String name, List<Artist> artists, String releaseDate, String description, 
                   boolean availability, double salePrice, double originalPrice, String supportedDevice, 
                   List<Genre> genres, String image) {
        this.productCode = productCode;
        this.name = name;
        this.artists = artists;
        this.releaseDate = releaseDate;
        this.description = description;
        this.availability = availability;
        this.salePrice = salePrice;
        this.originalPrice = originalPrice;
        this.supportedDevice = supportedDevice;
        this.genres = genres;
        this.image = image;
    }

    // Costruttore vuoto
    public Product() {
    }

    // Getter e Setter per i vari campi
    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSupportedDevice() {
        return supportedDevice;
    }

    public void setSupportedDevice(String supportedDevice) {
        this.supportedDevice = supportedDevice;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }
}
