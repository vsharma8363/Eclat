package fbla.hhs.eclat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

import javax.security.auth.callback.PasswordCallback;

public class Signup extends AppCompatActivity {

    EditText Password;
    EditText ConfirmPassword;
    EditText Email;
    Firebase myFirebaseRef;
    EditText FullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        myFirebaseRef = DataStorage.getRef();
        setContentView(R.layout.activity_signup);
        Password = (EditText) findViewById(R.id.SignUpPasswordInput);
        ConfirmPassword = (EditText) findViewById(R.id.SignUpPasswordConfirm);
        FullName = (EditText) findViewById(R.id.SignUpFirstNameInput);
        Email = (EditText) findViewById(R.id.SignUpEmailInput);
    }

    public void SubmitSignup(View view) {

                    if (Password.getText().toString().equals(ConfirmPassword.getText().toString()) && !TextEmpty()){

                        MakeAUser();


                    } else {

                        Toast.makeText(Signup.this, "All text fields must be full, " +
                                        "Passwords must match as well",
                                Toast.LENGTH_SHORT).show();

                        Password.setText(null);
                        ConfirmPassword.setText(null);

                    }
                    //Submit information to the firebase database
                    //Put a dialog here to let user know about redirect


    }

    public void HaveAnAccount(View view) {
                    Intent i = new Intent(this, Login.class);
                    startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }


    public void MakeAUser() {

                myFirebaseRef.createUser(Email.getText().toString(),
                        Password.getText().toString(), new Firebase.ResultHandler() {

                            @Override
                            public void onSuccess() {

                                myFirebaseRef.authWithPassword(Email.getText().toString(),
                                        Password.getText().toString(), new Firebase.AuthResultHandler() {

                                            @Override
                                            public void onAuthenticated(AuthData authData) {

                                                myFirebaseRef.child("users").child(authData.getUid()).child("full_name")
                                                        .setValue(FullName.getText().toString());
                                                myFirebaseRef.child("users").child(authData.getUid()).child("email")
                                                        .setValue(Email.getText().toString());

                                                Toast.makeText(Signup.this, "Success, your account was made!",
                                                        Toast.LENGTH_SHORT).show();


                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(i);
                                            }

                                            @Override
                                            public void onAuthenticationError(FirebaseError firebaseError) {
                                                //Auth Error
                                                Toast.makeText(Signup.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {

                                Toast.makeText(Signup.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
    }



       private boolean TextEmpty() {
                         if (Email.getText().length() == 0 && FullName.length() == 0)
                         return true;
                         else
                         return false;
     }


}

