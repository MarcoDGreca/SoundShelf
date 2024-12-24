package entity;

import java.util.List;

public class Product {

	private int productCode;
    private String name;
    private List<Artist> artists;
    private String releaseDate;
    private String description;
    private boolean availability;
    private double salePrice;
    private double originalPrice;
    private String supportedDevice;
    private List<Genre> genre;
    private String image;

    public Product(int code, String name, List<Artist> artists, String releaseDate, String description, boolean availability, double salePrice, double originalPrice, String supportedDevice, List<Genre> genre, String image) {
        this.productCode = code;
    	this.name = name;
        this.artists = artists;
        this.releaseDate = releaseDate;
        this.description = description;
        this.availability = availability;
        this.salePrice = salePrice;
        this.originalPrice = originalPrice;
        this.supportedDevice = supportedDevice;
        this.genre = genre;
        this.image = image;
    }

    public Product() {
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

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int i) {
		this.productCode = i;
	}
}
