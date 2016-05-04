package coursesbr.examples.p1_popularmovies;

/**
 * Created by Soledad on 4/29/2016.
 */
public class AndroidMovie {
    String originalTitle;
    //int image;
    String image_url;
    String synopsis;
    String userRating;
    String releaseDate;

    public AndroidMovie(String oTitle, String imageUrl, String sypno,String uRating,String rDate){
        this.originalTitle=oTitle;
        this.image_url=imageUrl;
        this.synopsis=sypno;
        this.userRating=uRating;
        this.releaseDate=rDate;
    }
}
