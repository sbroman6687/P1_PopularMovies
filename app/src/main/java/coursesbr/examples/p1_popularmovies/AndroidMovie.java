package coursesbr.examples.p1_popularmovies;

/**
 * Created by Soledad on 4/29/2016.
 */
public class AndroidMovie {
    String originalTitle;
    int image; //drawable reference id
    String synopsis;
    String userRating;
    String releaseDate;

    public AndroidMovie(String oTitle, int image, String sypno,String uRating,String rDate){
        this.originalTitle=oTitle;
        this.image=image;
        this.synopsis=sypno;
        this.userRating=uRating;
        this.releaseDate=rDate;
    }
}
