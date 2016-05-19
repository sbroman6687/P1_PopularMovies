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

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private String movie_title;
    private String movie_picture;
    private String movie_sypnosis;
    private String movie_Rating;
    private String movie_Release;
    private String movie_backdrop;


    public MovieDetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent!=null&&intent.hasExtra("Title")&&intent.hasExtra("Sypnosis")&&intent.hasExtra("Rating")
                &&intent.hasExtra("Release")&&intent.hasExtra("Image")&&intent.hasExtra("Backdrop")){
            movie_title = intent.getStringExtra("Title");
            movie_sypnosis = intent.getStringExtra("Sypnosis");
            movie_Rating=intent.getStringExtra("Rating");
            movie_Release = intent.getStringExtra("Release");
            movie_picture = intent.getStringExtra("Image");
            movie_backdrop= intent.getStringExtra("Backdrop");

            ((TextView)v.findViewById(R.id.MovieTitle)).setText(movie_title);
            ((TextView)v.findViewById(R.id.MovieSypnosis)).setText(movie_sypnosis);
            ((TextView)v.findViewById(R.id.MovieRating)).setText(movie_Rating);
            ((TextView)v.findViewById(R.id.MovieRelease)).setText(movie_Release);

            ImageView posterView = (ImageView)v.findViewById(R.id.MoviePoster);
            Picasso.with(getContext()).load(movie_picture).into(posterView);

            ImageView backdropView = (ImageView)v.findViewById(R.id.MovieBackdrop);
            Picasso.with(getContext()).load(movie_backdrop).into(backdropView);

        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
