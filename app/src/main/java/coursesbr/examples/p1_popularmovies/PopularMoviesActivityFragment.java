package coursesbr.examples.p1_popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesActivityFragment extends Fragment {

    private AndroidMovieAdapter movieAdapter;
    //private ArrayAdapter<String> ListmovieAdapter;


    /**
     *

    AndroidMovie[] androidMovies = {
            new AndroidMovie ("Frozen","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Frozen","4.0","April 2012"),
            new AndroidMovie ("Kunfu Panda","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Kunfu Panda","3.5","May 2012"),
            new AndroidMovie ("Immigrant","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Immigrant","4.2","June 2013"),
            new AndroidMovie ("Lion King","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Lion King","5.0","December 1995"),
            new AndroidMovie ("Monkey Kingdom","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Monkey Kingdom","3.0","September 2010"),
            new AndroidMovie ("Peter Pan","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Peter Pan","4.3","July 1999"),
            new AndroidMovie ("Aladdin","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Aladdin","4.7","November 2000"),
            new AndroidMovie ("Toy Story","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Toy Story","3.8","August 2001"),
            new AndroidMovie ("The Jungle Book","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis The Jungle Book","4.6","January 2003"),
            new AndroidMovie ("Finding Dori","http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","Sypnosis Finding Dori","4.5","June 2014"),

    };
    */

    public PopularMoviesActivityFragment() {
    }


    private void updateMovies(){
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String popular = prefs.getString(getString(R.string.pref_movies_sort_key),getString(R.string.pref_sort_mostpopular));
        moviesTask.execute(popular);
    }
    @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_popular_movies,container,false);
        //movieAdapter = new AndroidMovieAdapter(getActivity(), Arrays.asList(androidMovies));

        /**
         *
        ListmovieAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.grid_item_movie,
                R.id.movieView,new ArrayList<String>());
         */
        //Get a reference to the GridView, and attach this adapter to it.

        GridView gridView = (GridView)rootView.findViewById(R.id.gridView_popularmovies);
        gridView.setAdapter(movieAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity();

                AndroidMovie androidmovie= movieAdapter.getItem(position);

                String detailtitle = androidmovie.originalTitle;
                String detailimage = androidmovie.image_url;
                String detailsypnosis = androidmovie.synopsis;
                String detailRating = androidmovie.userRating;
                String detailrelease = androidmovie.releaseDate;

                // Intent to pass data between fragments using the same structure that we used in the Sunshine App
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class).putExtra("Title",detailtitle).putExtra("Sypnosis",detailsypnosis)
                        .putExtra("Rating",detailRating).putExtra("Release",detailrelease).putExtra("Image",detailimage);
                startActivity(intent);

            }
        });

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String,Void,String[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private String[] getMovieDataFromJson(String movieJsonStr, int numMovies)throws JSONException{
            //These are the names of the JSON object that need to be extracted
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ORIGINAL_TITLE = "original_title";
            final String SYPNOSIS = "overview";
            final String MOVIE_RELEASE = "release_date";
            final String MOVIE_RATING = "vote_average";


            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            AndroidMovie[] androidMovies = new AndroidMovie[numMovies];

            String[] posterPaths = new String[numMovies];
            String[] original_titles = new String[numMovies];
            String[] sypnosis = new String[numMovies];
            String[] movie_release = new String[numMovies];
            String[] movie_rating = new String[numMovies];


            for (int i = 0; i<movieArray.length();i++){

                JSONObject moviedata = movieArray.getJSONObject(i);

                //Strings that we need
                posterPaths[i]= " http://image.tmdb.org/t/p/w185/" + moviedata.getString(POSTER_PATH);
                original_titles[i]=moviedata.getString(ORIGINAL_TITLE);
                sypnosis[i]= moviedata.getString(SYPNOSIS);
                movie_release[i] = moviedata.getString(MOVIE_RELEASE);
                movie_rating[i] = moviedata.getString(MOVIE_RATING);

                androidMovies[i] = new AndroidMovie (original_titles[i],posterPaths[i],sypnosis[i],movie_release[i],movie_rating[i]);
            }

            //adapter here
            movieAdapter = new AndroidMovieAdapter(getActivity(), Arrays.asList(androidMovies));

            return posterPaths;

        }


        @Override
        protected String[] doInBackground(String... params) {

            if(params.length ==0){
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String format = "json";
            int  numMovies = 20;

            try{
                //Construct the URL for the Popular Movies query
                //Possible parameters are available at https://www.themoviedb.org/documentation/api/discover

                String MOVIES_BASE_URL ="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
                String apiKey = "&api_key" + BuildConfig.OPEN_POPULAR_MOVIES_API_KEY;
                URL url = new URL(MOVIES_BASE_URL.concat(apiKey));

                //Create the request to themoviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    //movieJsonStr = null;
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    //movieJsonStr = null;
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie string: " + movieJsonStr);


            } catch (IOException e) {
                Log.e("PopularMoviesFragment","Error",e);
                //If the code didn't succesfully get the movies data, there is no point to parse it
                //movieJsonStr = null;
                return null;
            }finally {
                if (urlConnection!=null){
                    urlConnection.disconnect();
                }
                if (reader!=null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e("PopularMoviesFragment","Error closing stream",e);
                    }
                }
            }
            try{
                return getMovieDataFromJson(movieJsonStr,numMovies);
            }catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
                // This will only happen if there was an error getting or parsing the movies.

            return null;
        }



        @Override
        protected void onPostExecute(String[] result) {

            if (result != null){
                movieAdapter.clear();
                for (String moviedataStr  : result){

                     movieAdapter.addAll();

                }
                //new data is back from the server. yujuuu
            }
        }

    }

}
