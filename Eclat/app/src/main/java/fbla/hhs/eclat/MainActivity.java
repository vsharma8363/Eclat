package fbla.hhs.eclat;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pushbots.push.Pushbots;

import fbla.hhs.eclat.tindercard.FlingCardListener;
import fbla.hhs.eclat.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;
    Button Message;
    LinearLayout Linear;
    Toolbar tb;
   // final String m_Text;
    public static void removeBackground() {


        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }

    /**
     *
     <Button
     android:layout_width="36dp"
     android:layout_height="20dp"
     android:background="@drawable/ic_drawer"
     android:id="@+id/startDrawer"
     android:onClick="startDrawer"
     android:layout_marginTop="10dp"
     android:layout_marginLeft="10dp"
     android:layout_gravity="left|top" />
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message = (Button) findViewById(R.id.SendMessage);
        Linear = (LinearLayout) findViewById(R.id.LinearLayoutmain);
        tb = (Toolbar) findViewById(R.id.toolbar);
        Linear.setVisibility(View.INVISIBLE);
        tb.setTitle("Home");
        setSupportActionBar(tb);



        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        al.add(new Data("http://greycubelabs.com/eclat/image1.jpg", "By: Vikram Sharma in California", "xxx"));
        al.add(new Data("http://www.greycubelabs.com/eclat/image2.png", "By: Ethan Yim in Washington DC", "xxy"));
        al.add(new Data("http://www.greycubelabs.com/eclat/image3.jpg", "By: Sherry Luo in South Carolina", "xxy"));

        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override



            public void removeFirstObjectInAdapter() {

            }


            public void RemoveACard()
            {
                al.remove(0);
            }
//----------------------------
// ---------------------------------------------------------------------------------------------------------------------
            //If the the card is swiped left
            public void onLeftCardExit(Object dataObject) {

                Data x = al.get(0);

                String UserID = x.getUSERID();

                al.remove(0);

                if(al.size() == 0){
                    Linear.setVisibility(View.VISIBLE);
                }

                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!

                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override

//-------------------------------------------------------------------------------------------------------------------------------------------------
            //If the the card is swiped right
            public void onRightCardExit(Object dataObject) {

                Data x = al.get(0);
                String UserID = x.getUSERID();

                al.remove(0);


                if(al.size() == 0){
                    Linear.setVisibility(View.VISIBLE);
                }


                myAppAdapter.notifyDataSetChanged();


        }
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

//-------------------------------------------------------------------------------------------------------------------------------------------------
       //If the Skip Button Command is clicked


         //-------------------------------------------------------------------------------------------------------------------------------------------------

     /**    //If the Message Button Command is clicked
         Message.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {

         //DO SOME CODE STUFF TO SEND MESSAGE__________________________________________

         }
         });

         **/

    }


    public void SendAUser(View v) {
       // m_Text = new String();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send a Message to a User!");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // m_Text = input.getText().toString();
                Toast.makeText(getApplicationContext(), "Your message has been sent!", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
                  //DO SOME CODE STUFF TO SEND MESSAGE__________________________________________

    }




    //-------------------------------------------------------------------------------------------------------------------------------------------------
    //StartActivityDrawer
    public void startnavDrawer(View v){
        Intent i = new Intent(getApplicationContext(), NavDrawer.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");

    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder

                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);






            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

               viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");
            Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);


            return rowView;

            }



    }
}
