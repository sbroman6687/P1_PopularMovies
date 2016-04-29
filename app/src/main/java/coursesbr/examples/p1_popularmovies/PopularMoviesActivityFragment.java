package coursesbr.examples.p1_popularmovies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesActivityFragment extends Fragment {

    private AndroidMovieAdapter movieAdapter;


    AndroidMovie[] androidMovies = {
            new AndroidMovie ("Frozen",R.drawable.m1,"Sypnosis Frozen","4.0","April 2012"),
            new AndroidMovie ("Kunfu Panda",R.drawable.m2,"Sypnosis Kunfu Panda","3.5","May 2012"),
            new AndroidMovie ("Immigrant",R.drawable.m3,"Sypnosis Immigrant","4.2","June 2013"),
            new AndroidMovie ("Lion King",R.drawable.m4,"Sypnosis Lion King","5.0","December 1995"),
            new AndroidMovie ("Monkey Kingdom",R.drawable.m5,"Sypnosis Monkey Kingdom","3.0","September 2010"),
            new AndroidMovie ("Peter Pan",R.drawable.m6,"Sypnosis Peter Pan","4.3","July 1999"),
            new AndroidMovie ("Aladdin",R.drawable.m7,"Sypnosis Aladdin","4.7","November 2000"),
            new AndroidMovie ("Toy Story",R.drawable.m8,"Sypnosis Toy Story","3.8","August 2001"),
            new AndroidMovie ("The Jungle Book",R.drawable.m9,"Sypnosis The Jungle Book","4.6","January 2003"),
            new AndroidMovie ("Finding Dori",R.drawable.m10,"Sypnosis Finding Dori","4.5","June 2014"),

    };

    public PopularMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies,container,false);
        movieAdapter = new AndroidMovieAdapter(getActivity(), Arrays.asList(androidMovies));

        //Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView)rootView.findViewById(R.id.gridView_popularmovies);
        gridView.setAdapter(movieAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity();
                //PopularMoviesActivity activity = (PopularMoviesActivity)getActivity();

                AndroidMovie androidmovie= movieAdapter.getItem(position);

                String detailtitle = androidmovie.originalTitle;
                int detailimage = androidmovie.image;
                String detailsypnosis = androidmovie.synopsis;
                String detailRating = androidmovie.userRating;
                String detailrelease = androidmovie.releaseDate;


                //fragment and bundle to pass data from: http://stackoverflow.com/questions/15392261/android-pass-dataextras-to-a-fragment
                Fragment fragment = new Fragment();

                Bundle extras = new Bundle();
                extras.putString("movieTitle", detailtitle);
                extras.putInt("moviePicture", detailimage);
                extras.putString("movieSypnosis",detailsypnosis);
                extras.putString("movieRating", detailRating);
                extras.putString("movieRelease", detailrelease);

                fragment.setArguments(extras);

            }
        });
        return rootView;
    }
}
