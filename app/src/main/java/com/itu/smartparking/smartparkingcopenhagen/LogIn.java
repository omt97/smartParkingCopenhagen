package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private EditText user_log;
    private EditText pass_log;
    private Button button_log;
    private Button button_reg;

    private DataAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        user_log = findViewById(R.id.login_id_us);
        pass_log = findViewById(R.id.login_id_pass);
        button_log = findViewById(R.id.login_id_buttlog);
        button_reg = findViewById(R.id.login_id_buttreg);

        da = new DataAccess(this);

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_log.getText().length() == 0) Toast.makeText(LogIn.this,
                        "Write username",
                        Toast.LENGTH_SHORT).show();
                if (pass_log.getText().length() == 0) Toast.makeText(LogIn.this,
                        "Write password",
                        Toast.LENGTH_SHORT).show();
                User user = da.getUser(user_log.getText().toString());
                if (user != null) {
                    if (user.getPassword().equals(pass_log.getText().toString())){
                        Intent intent = Map.newIntent(LogIn.this, user.getUser());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LogIn.this,
                                "Wrong password",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LogIn.this,
                            "User not exist",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
