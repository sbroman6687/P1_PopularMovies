package coursesbr.examples.p1_popularmovies;

import android.content.Context;
import android.content.Intent;
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
    private String movie_title;
    private Integer movie_picture;
    private String movie_sypnosis;
    private String movie_Rating;
    private String movie_Release;


    public MovieDetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent!=null&&intent.hasExtra("Title")&&intent.hasExtra("Sypnosis")&&intent.hasExtra("Rating")
                &&intent.hasExtra("Release")&&intent.hasExtra("Image")){
            movie_title = intent.getStringExtra("Title");
            movie_sypnosis = intent.getStringExtra("Sypnosis");
            movie_Rating=intent.getStringExtra("Rating");
            movie_Release = intent.getStringExtra("Release");
            movie_picture = intent.getIntExtra("Image", 0);
            ((TextView)v.findViewById(R.id.MovieTitle)).setText(movie_title);
            ((TextView)v.findViewById(R.id.MovieSypnosis)).setText(movie_sypnosis);
            ((TextView)v.findViewById(R.id.MovieRating)).setText(movie_Rating);
            ((TextView)v.findViewById(R.id.MovieRelease)).setText(movie_Release);
            ((ImageView)v.findViewById(R.id.MoviePoster)).setImageResource(movie_picture);

        }


        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
