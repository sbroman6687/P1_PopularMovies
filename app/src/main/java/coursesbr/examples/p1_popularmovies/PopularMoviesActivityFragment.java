package coursesbr.examples.p1_popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesActivityFragment extends Fragment {

   // public AndroidMovie[] androidMovies;
    public AndroidMovieAdapter movieAdapter;
    public GridView gridView;
    public View rootView;
    public String sortValue;


    public PopularMoviesActivityFragment() {
    }

    private void updateMovies(){
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortType = prefs.getString(getString(R.string.pref_movies_sort_key), getString(R.string.pref_sort_value_popular));

        if (sortType.equals(getString(R.string.pref_sort_value_popular))){
            sortValue = "popularity.desc";

        }else{
            sortValue = "vote_average.desc";
        }
        moviesTask.execute(sortType);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_popular_movies,container,false);


        return rootView;
    }
    public class FetchMoviesTask extends AsyncTask<String,Void,AndroidMovie[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();


        private AndroidMovie[] getMovieDataFromJson(String movieJsonStr)throws JSONException{
            //These are the names of the JSON object that need to be extracted
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ORIGINAL_TITLE = "original_title";
            final String SYPNOSIS = "overview";
            final String MOVIE_RELEASE = "release_date";
            final String MOVIE_RATING = "vote_average";
            final String BACKDROP = "backdrop_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            AndroidMovie[] resultStr = new AndroidMovie[movieArray.length()];

            //Extract movie data and build movie objects

            for (int i = 0; i<movieArray.length();i++){
                //AndroidMovie[] resultStr = new AndroidMovie[i];
                String img_path;
                String title;
                String sypnosis;
                String movie_release;
                String movie_rating;
                String movie_backdrop;

                JSONObject moviedata = movieArray.getJSONObject(i);
                img_path="http://image.tmdb.org/t/p/w500/" + moviedata.getString(POSTER_PATH);
                title=moviedata.getString(ORIGINAL_TITLE);
                sypnosis = moviedata.getString(SYPNOSIS);
                movie_release=moviedata.getString(MOVIE_RELEASE);
                movie_rating=moviedata.getString(MOVIE_RATING);
                movie_backdrop= "http://image.tmdb.org/t/p/w780/" + moviedata.getString(BACKDROP);

                AndroidMovie element = new AndroidMovie(title,img_path,sypnosis,movie_release,movie_rating,movie_backdrop);

                resultStr[i]= element;
            }
            return resultStr;
        }

        @Override
        protected AndroidMovie[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try{
                //Construct the URL for the Popular Movies query
                //Possible parameters are available at https://www.themoviedb.org/documentation/api/discover


                final String MOVIES_BASE_URL ="http://api.themoviedb.org/3/discover/movie/";
                final String SORT_PARAM = sortValue;
                final String SORT_BY = "sort_by";
                final String APIKEY_PARAM = "api_key";
                final String API_PAGE = "page";

                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(API_PAGE, "1")
                        .appendQueryParameter(SORT_BY,sortValue)
                        .appendQueryParameter(APIKEY_PARAM, BuildConfig.OPEN_POPULAR_MOVIES_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                //Create the request to themoviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    movieJsonStr = null;

                }else{
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie string: " + movieJsonStr);

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NetworkOnMainThreadException e) {
                Log.d("Error: ", e.toString());
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AndroidMovie[] result) {

            if (result!=null){
                try {
                    movieAdapter = new AndroidMovieAdapter(getActivity(), Arrays.asList(result));
                    gridView = (GridView)rootView.findViewById(R.id.gridView_popularmovies);
                    gridView.setAdapter(movieAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Context context = getActivity();

                            //AndroidMovie androidmovie = movieAdapter.getItem(position);
                            AndroidMovie result = movieAdapter.getItem(position);

                            String detailtitle = result.originalTitle;
                            String detailimage = result.image_url;
                            String detailsypnosis = result.synopsis;
                            String detailRating = result.userRating;
                            String detailrelease = result.releaseDate;
                            String detailBackdrop = result.backdrop_url;

                            // Intent to pass data between fragments using the same structure that we used in the Sunshine App
                            Intent intent = new Intent(getActivity(), MovieDetailActivity.class).putExtra("Title", detailtitle).putExtra("Sypnosis", detailsypnosis)
                                    .putExtra("Rating", detailRating).putExtra("Release", detailrelease).putExtra("Image", detailimage).putExtra("Backdrop", detailBackdrop);
                            startActivity(intent);

                        }
                    });


                }catch(NullPointerException e){
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(getContext(),"Nothing to show",Toast.LENGTH_SHORT).show();
            }

        }

    }
}
