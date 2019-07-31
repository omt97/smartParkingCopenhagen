package com.itu.smartparking.smartparkingcopenhagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText user_reg;
    private EditText pass_reg;
    private Button button_reg;

    private DataAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_reg = findViewById(R.id.reg_id_us);
        pass_reg = findViewById(R.id.reg_id_pass);
        button_reg = findViewById(R.id.reg_id_button);

        da = new DataAccess(this);

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_reg.getText().length() == 0) Toast.makeText(Register.this,
                        "Write username",
                        Toast.LENGTH_SHORT).show();
                if (pass_reg.getText().length() == 0) Toast.makeText(Register.this,
                        "Write password",
                        Toast.LENGTH_SHORT).show();

                if (da.getUser(user_reg.getText().toString()) == null) {
                    da.addUser(new User(user_reg.getText().toString(), pass_reg.getText().toString(), 1));
                    finish();
                }
                else{
                    Toast.makeText(Register.this,
                            "Username exist",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
