package coursesbr.examples.p1_popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soledad on 4/29/2016.
 */
public class AndroidMovieAdapter extends ArrayAdapter<AndroidMovie> {
    private static final String LOG_TAG = AndroidMovieAdapter.class.getSimpleName();
    /**
     * This is my own custom constructor (it doesn't mirror a superclass constructor)
     * The context (current context) is used to inflate the layout file, and the list (androidMovies)
     * is the list data to populate into the grid
     */
    public AndroidMovieAdapter(Activity context, List<AndroidMovie> result){
        super(context,0,result);

    }
    /**
     * Provides a view for an AdapterView (GridView in this case)
     * position: The AdapterView position that is requesting a view
     * convertView: The recycled view to populate.
     * parent: The parent ViewGroup that is used for inflation
     * return: The View for the position in the AdapterView
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. Gets the AndroidMovie object from the ArrayAdapter at the appropriate position
        AndroidMovie result = getItem(position);

        //2. Adapters recycle views to AdapterViews. If this is a new View Object, then inflate the layout.
        //If not, this view already has the layout inflate from previous call to getView, we just modify the view widgets.

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie,parent,false);
        }
        ImageView posterView = (ImageView)convertView.findViewById(R.id.movieView);
        //Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext()).load(result.image_url).into(posterView);
        return convertView;
    }
}
