/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText simpleEditText = findViewById(R.id.name);
        String nameValue = simpleEditText.getText().toString();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = (createOrderSummary(price, hasWhippedCream, hasChocolate, nameValue));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java) + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Create summary of the order.
     *
     * @param addChocolate    is true or false for chocolate
     * @param addWhippedCream is true or false for whipped cream
     * @param price           of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String addName) {
        String priceMessage = getString(R.string.order_summary_name) + addName;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.add_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.total_quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is true or false to add calculation
     * @param addChocolate    is true or false to add calculation
     * @return total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += +1;
        }
        if (addChocolate) {
            basePrice += +2;
        }
        return quantity * basePrice;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        if (number <= 0) {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number >= 100) {
            Toast.makeText(getApplicationContext(), "You cannot order more than 99 cups of coffee at a time", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}

