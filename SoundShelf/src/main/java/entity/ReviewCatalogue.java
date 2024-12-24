package entity;

import java.util.ArrayList;
import java.util.List;

public class ReviewCatalogue {

    private List<Review> reviews;

    public ReviewCatalogue() {
        this.reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
