package is.hi.hbv601g.networkingexampleloopj;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;
import is.hi.hbv601g.networkingexampleloopj.network.APIClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import is.hi.hbv601g.networkingexampleloopj.model.Character;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mStatus;
    private TextView mOrigin;
    private TextView mLocation;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.tvName);
        mStatus = findViewById(R.id.tvStatus);
        mOrigin = findViewById(R.id.tvOrigin);
        mLocation = findViewById(R.id.tvLocation);
        mImage = findViewById(R.id.ivImage);

        getNewRandomCharacter();

        findViewById(R.id.btnRandomCharacter).setOnClickListener(l -> getNewRandomCharacter());
    }

    public void getNewRandomCharacter(){

        long randomCharacterId = (long)(Math.random() * 200 + 1);
        APIClient.get("/character/" + randomCharacterId, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("DATA_OBJ",response.toString());
                try {
                    updateUI(new Character(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateUI(Character character) {
        mName.setText(character.getName());
        mStatus.setText(character.getStatus());
        mOrigin.setText(character.getOrigin().getName());
        mLocation.setText(character.getLocation().getName());
        new GetImageFromUrl().execute(character.getImage());
    }

    class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        GetImageFromUrl(){}

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            try {
                URL imageUrl = new URL(stringUrl);
                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            mImage.setImageBitmap(bitmap);
        }
    }
}
