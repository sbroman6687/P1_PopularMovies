package coursesbr.examples.p1_popularmovies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {


    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = this.getArguments();

        if (extras!=null){
            String movie_title = extras.getString("movieTitle");
            Integer movie_picture = extras.getInt("moviePicture");
            String movie_sypnosis = extras.getString("movieSypnosis");
            String movie_Rating = extras.getString("movieRating");
            String movie_Release = extras.getString("movieRelease");
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        TextView myTitle= (TextView)v.findViewById(R.id.MovieTitle);
        myTitle.setText(movie_title);

        ImageView mymovie = (ImageView)v.findViewById(R.id.MoviePoster);
        mymovie.setImageResource(movie_picture);

        TextView mySypnosis= (TextView)v.findViewById(R.id.MovieSypnosis);
        mySypnosis.setText(movie_sypnosis);

        TextView myRating= (TextView)v.findViewById(R.id.MovieRating);
        myRating.setText(movie_Rating);

        TextView myRelease= (TextView)v.findViewById(R.id.MovieRelease);
        myRelease.setText(movie_Release);

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
