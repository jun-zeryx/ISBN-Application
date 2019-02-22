package com.example.isbnapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView isbnOutputText;
    Button isbnButton;
    EditText isbnInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isbnOutputText = findViewById(R.id.isbnOutput);
        isbnButton = findViewById(R.id.isbnGenerate);
        isbnInputText = findViewById(R.id.isbnInput);

        isbnInputText.setText("978155192370");
        isbnOutputText.setText("ISBN Number : ");

        isbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isbnInputText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (isbnInputText.length() == 12) {
                    generateISBN();
                }
                else if (isbnInputText.length() == 10) {
                    validateISBN();
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid Product ID/ISBN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateISBN() {
        String inputString = isbnInputText.getText().toString();
        String isbnString = inputString.substring(3);
        Integer sum = 0;
        int j=isbnString.length() + 1;

        for(int i=0;i<isbnString.length();i++) {
            sum += Character.getNumericValue(isbnString.charAt(i)) * j;
            j--;
        }

        for(int i=0;i<11;i++) {
            if ((sum + i) % 11 == 0) {

                if (i == 10) {
                    isbnString = isbnString.concat("X");
                }
                else {
                    isbnString = isbnString.concat(String.valueOf(i));

                }
                isbnOutputText.setText(String.format("ISBN Number : %s",isbnString));
                return;
            }
        }
    }

    private void validateISBN() {
        String inputString = isbnInputText.getText().toString();
        Integer sum = 0;
        int j=inputString.length();

        for(int i=0;i<inputString.length();i++) {
            if (inputString.charAt(i) == 'X') {
                sum += 10 * j;
            }
            else {
                sum += Character.getNumericValue(inputString.charAt(i)) * j;
            }
            j--;
        }

        if (sum % 11 == 0) {
            Toast.makeText(MainActivity.this, "Valid ISBN", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Invalid ISBN", Toast.LENGTH_SHORT).show();
        }
    }
}
