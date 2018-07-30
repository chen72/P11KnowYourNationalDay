package sg.edu.rp.webservices.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> aLs = new ArrayList<String>();
    ArrayAdapter<String> aa;

    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        aLs.add("Singapore National Day is on 9 Aug");
        aLs.add("Singapore is 53 years old");
        String theme = "We are Singapore";
        aLs.add("Theme is " + theme);
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, aLs);
        lv.setAdapter(aa);
    }

    @Override
    protected void onResume() {
        super.onResume();


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isCode = prefs.getBoolean("isCode", false);

        if (isCode == true) {

        } else {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passPhrase =
                    (LinearLayout) inflater.inflate(R.layout.access_code, null);
            final EditText etCode = (EditText) passPhrase
                    .findViewById(R.id.editTextCode);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(passPhrase)
                    .setNegativeButton("No Access code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }
                    })
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            if (etCode.getText().toString().trim().equalsIgnoreCase("738964")) {

                                prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor prefEdit = prefs.edit();
                                prefEdit.putBoolean("isCode", true);
                                prefEdit.commit();


                            } else {
                                finish();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);

            alertDialog.show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.send) {


            final String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                String msg = "";
                                for(int i=0;i< aLs.size();i++){
                                    msg+=aLs.get(i)+"\n";
                                }
                                // The action you want this intent to do;
                                // ACTION_SEND is used to indicate sending text
                                Intent email = new Intent(Intent.ACTION_SEND);
                                // Put essentials like email address, subject & body text
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Know Your National Day");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        msg);
                                // This MIME type indicates email
                                email.setType("message/rfc822");
                                // createChooser shows user a list of app that can handle
                                // this MIME type, which is, email
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Email has been send", Snackbar.LENGTH_SHORT).show();

                            } else {
                                String msg = "";
                                for(int i=0;i< aLs.size();i++){
                                    msg+=aLs.get(i)+"\n";
                                }

                                Uri uri = Uri.parse("smsto:" + null);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                intent.putExtra("sms_body", msg);
                                startActivity(intent);
                                Snackbar.make(getWindow().getDecorView().getRootView(), "SMS has been send", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quiz) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout quiz =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);

            final RadioGroup rg1 = (RadioGroup) quiz.findViewById(R.id.radioGroup1);


            final RadioGroup rg2 = (RadioGroup) quiz.findViewById(R.id.radioGroup2);


            final RadioGroup rg3 = (RadioGroup) quiz.findViewById(R.id.radioGroup3);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(quiz)
                    .setNegativeButton("DON'T KNOW LAH", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                            int selectedButton1 = rg1.getCheckedRadioButtonId();
                            RadioButton rb1 = (RadioButton) quiz.findViewById(selectedButton1);
                            int selectedButton2 = rg2.getCheckedRadioButtonId();
                            RadioButton rb2 = (RadioButton) quiz.findViewById(selectedButton2);
                            int selectedButton3 = rg3.getCheckedRadioButtonId();
                            RadioButton rb3 = (RadioButton) quiz.findViewById(selectedButton3);

                            int mark = 0;
                            if (rb1.getText().toString().trim().equalsIgnoreCase("No")) {
                                mark = mark + 1;
                            }
                            if (rb2.getText().toString().trim().equalsIgnoreCase("Yes")) {
                                mark = mark + 1;
                            }
                            if (rb3.getText().toString().trim().equalsIgnoreCase("Yes")) {
                                mark = mark + 1;
                            }
                            Toast.makeText(MainActivity.this, "You got " + mark + "/3", Toast.LENGTH_SHORT).

                                    show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);

            alertDialog.show();
//

        } else if (item.getItemId() == R.id.quit)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                        }
                    })

                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("NOT REALLY", null);
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }
        return super.

                onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }


}
