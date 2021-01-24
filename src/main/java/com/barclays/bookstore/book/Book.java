package com.barclays.bookstore.book;

import com.barclays.bookstore.DefaultZero;
import com.barclays.bookstore.image.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @Column(columnDefinition = "text", length = 10485760)
    private String title;

    @Column(columnDefinition = "text", length = 10485760)
    private String authors;

    @JsonDeserialize(using = DefaultZero.class, as = Double.class)
    @JsonProperty("average_rating")
    private Double averageRating;

    @Column(columnDefinition = "text", length = 10485760)
    private String isbn;

    @Column(columnDefinition = "text", length = 10485760)
    @JsonProperty("language_code")
    private String languageCode;

    @Column(columnDefinition = "text", length = 10485760)
    @JsonProperty("ratings_count")
    private String ratingCount;

    private double price;

    private String imageUrl;

    public void setImage(Image image) {
        setImageUrl(image.getImage());
    }

}
