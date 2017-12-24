package eus.ehu.tta.ttaejemplo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class newTestActivity extends AppCompatActivity {
    private BusinessInterface business;
    private boolean isRadioButtonSelected = false;
    BusinessInterface.TestTTA currentTest;
    private static final String TAG = newTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        business = new Business();
        createViews();
    }

    private void createViews()
    {
        final LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        currentTest = business.getNewTest();
        TextView questionView = new TextView(this);
        questionView.setText(currentTest.getPregunta());
        linearLayout.addView(questionView);
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.getChildAt(2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isRadioButtonSelected)
                {
                    isRadioButtonSelected=true;
                    final Button sendButton = new Button(newTestActivity.this);
                    sendButton.setText(R.string.send);
                    sendButton.setOnClickListener(new Button.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            int radioButtonID = radioGroup.getCheckedRadioButtonId();
                            View radioButton = radioGroup.findViewById(radioButtonID);
                            int index = radioGroup.indexOfChild(radioButton);
                            Log.d(TAG,"Radio button " + index + " selected");
                            if (business.sendTest(currentTest.getIdEjercicio(),index))
                            {
                                Toast.makeText(newTestActivity.this,R.string.success, Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                radioButton.setBackgroundColor(getResources().getColor(R.color.red));
                                Toast.makeText(newTestActivity.this,R.string.failure, Toast.LENGTH_SHORT).show();
                                Button helpButton = new Button(newTestActivity.this);
                                helpButton.setText(R.string.getHelp);
                                helpButton.setOnClickListener(new helpOnClickListener());
                                linearLayout.addView(helpButton);
                            }
                            View correctRadioButton = radioGroup.getChildAt(currentTest.getCorrecta());
                            correctRadioButton.setBackgroundColor(getResources().getColor(R.color.green));
                            linearLayout.removeView(sendButton);
                        }
                    });
                    linearLayout.addView(sendButton);
                }
            }
        });
        for (String opcion : currentTest.getOpciones())
        {
            RadioButton optionView = new RadioButton(this);
            optionView.setText(opcion);
            radioGroup.addView(optionView);
        }
        linearLayout.addView(radioGroup);
    }

    private class helpOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View view)
        {
            LinearLayout linearLayout = findViewById(R.id.newTestLayout);
            TextView helpTextView = new TextView(newTestActivity.this);
            helpTextView.setText(Html.fromHtml(currentTest.getAyuda()));
            helpTextView.setTextColor(Color.BLACK);
            linearLayout.addView(helpTextView);
        }
    }
}
