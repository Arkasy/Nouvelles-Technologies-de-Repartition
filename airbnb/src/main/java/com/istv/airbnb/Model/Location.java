package com.istv.airbnb.Model;

import javax.persistence.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Entity
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String address;

    private String postalCode;

    private String city;

    private String type;

    private Double price;

    private int countPersons;

    @ManyToOne
    private User owner;

    private boolean available;

    @Column(name="image", length=100000000)
    private byte[] image;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "images", length=100000000)
    private List<byte[]> images;

    @Transient
    private String imageString;

    @Transient
    private List<String> imagesString = new ArrayList<String>();

    public Location() {
    }

    public Location(String title, String address, String postalCode, String city, String type, Double price, int countPersons, User owner, boolean available, byte[] image, List<byte[]> images, String imageString, List<String> imagesString) {
        this.title = title;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.type = type;
        this.price = price;
        this.countPersons = countPersons;
        this.owner = owner;
        this.available = available;
        this.image = image;
        this.images = images;
        this.imageString = imageString;
        this.imagesString = imagesString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCountPersons() {
        return countPersons;
    }

    public void setCountPersons(int countPersons) {
        this.countPersons = countPersons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageString() {
        return new String(this.getImage(), StandardCharsets.UTF_8);
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }


    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = new ArrayList<>(images);
    }

    public List<String> getImagesString() {
        this.imagesString = new ArrayList<>();
        this.images.forEach(image ->{
            this.imagesString.add(new String(image, StandardCharsets.UTF_8));
        });
        return imagesString;
    }

    public void setImagesString(List<String> imagesString) {
        this.imagesString = new ArrayList<>(imagesString);
    }

    /*public void setImage(Blob image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            URL url = null;
            is = new BufferedInputStream(image.getBinaryStream());
            byte[] byteChunk = new byte[4096];
            int n;
            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }
            this.image = baos.toByteArray();
        }
        catch (IOException | SQLException e) {e.printStackTrace ();}
        finally {
            if (is != null) { is.close(); }
        }
    }*/

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", City='" + city + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", countPersons=" + countPersons +
                ", owner=" + owner +
                ", available=" + available +
                '}';
    }
}
