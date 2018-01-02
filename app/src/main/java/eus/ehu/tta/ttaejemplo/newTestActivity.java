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
    private static final java.lang.String STATE = "state";
    private static final String CURRENT_TEST = "current_test";
    private static final java.lang.String ANSWER_CHOSEN = "answer_chosen";
    private static final java.lang.String IS_HELP_OPEN = "is_help_open";
    private static final short TEST_START = 0;
    private static final short RADIO_BUTTON_PRESSED = 1;
    private static final short INCORRECT_ANSWER =2;
    private static final short CORRECT_ANSWER = 3;
    private BusinessInterface business;
    private short state;
    private TestTTA currentTest;
    private int answerChosen = -1;
    private boolean isHelpOpen=false;
    private static final String TAG = newTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        business = new Business();
        if (savedInstanceState != null)
        {
            state = savedInstanceState.getShort(STATE);
            currentTest = savedInstanceState.getParcelable(CURRENT_TEST);
            answerChosen = savedInstanceState.getInt(ANSWER_CHOSEN);
            isHelpOpen = savedInstanceState.getBoolean(IS_HELP_OPEN);
        }

        else
        {
            state = TEST_START;
            currentTest = business.getNewTest();
        }
        createViews();
    }

    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putShort(STATE,state);
        savedInstanceState.putParcelable(CURRENT_TEST,currentTest);
        savedInstanceState.putInt(ANSWER_CHOSEN,answerChosen);
        savedInstanceState.putBoolean(IS_HELP_OPEN,isHelpOpen);
    }

    private void createViews()
    {
        LinearLayout linearLayout = findViewById(R.id.newTestLayout);

        TextView questionView = findViewById(R.id.newTestTextView);
        questionView.setText(currentTest.getPregunta());
        RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
        radioGroup.setOnCheckedChangeListener(new radioOnCheckedListener ());
        for (String opcion : currentTest.getOpciones())
        {
            RadioButton optionView = new RadioButton(this);
            optionView.setText(opcion);
            radioGroup.addView(optionView);
        }
        firstTimeSetStateViews();
    }

    private void firstTimeSetStateViews ()
    {
        setStateViews();
        if (answerChosen!=-1)
        {
            RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
            ((RadioButton) radioGroup.getChildAt(answerChosen)).setChecked(true);
        }
        if (isHelpOpen)
        {
            openHelp();
        }
    }

    private void setStateViews() {
        if (state == RADIO_BUTTON_PRESSED)
        {
            LinearLayout linearLayout = findViewById(R.id.newTestLayout);
            Button sendButton = new Button(newTestActivity.this);
            sendButton.setText(R.string.send);
            sendButton.setOnClickListener(new sendButtonOnClickListener());
            sendButton.setId(R.id.newTestSendButton);
            linearLayout.addView(sendButton);
        }
        else if (state == INCORRECT_ANSWER)
        {
            LinearLayout linearLayout = findViewById(R.id.newTestLayout);
            RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
            View radioButton = radioGroup.getChildAt(answerChosen);
            radioButton.setBackgroundColor(getResources().getColor(R.color.red));
            Button helpButton = new Button(newTestActivity.this);
            helpButton.setText(R.string.getHelp);
            helpButton.setOnClickListener(new helpOnClickListener());
            helpButton.setId(R.id.newTestHelpButton);
            linearLayout.addView(helpButton);
        }
        if (state == CORRECT_ANSWER || state == INCORRECT_ANSWER)
        {
            RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
            View correctRadioButton = radioGroup.getChildAt(currentTest.getCorrecta());
            correctRadioButton.setBackgroundColor(getResources().getColor(R.color.green));
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
        }
    }

    private class helpOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View view)
        {
            if (!isHelpOpen)
            {
                openHelp();
            }
            else
            {
                closeHelp();
            }
            isHelpOpen = !isHelpOpen;
        }
    }

    private void openHelp() {
        TextView helpTextView = new TextView(newTestActivity.this);
        helpTextView.setText(Html.fromHtml(currentTest.getAyuda()));
        helpTextView.setTextColor(Color.BLACK);
        helpTextView.setId(R.id.newTestHelpText);
        LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        linearLayout.addView(helpTextView);
    }

    private void closeHelp() {
        LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        linearLayout.removeView(findViewById(R.id.newTestHelpText));
    }

    private class radioOnCheckedListener implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            answerChosen = group.indexOfChild(findViewById(checkedId));
            if (state == TEST_START)
            {
                state=RADIO_BUTTON_PRESSED;
                setStateViews();
            }
        }
    }

    private class sendButtonOnClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (business.sendTest(currentTest.getIdEjercicio(),answerChosen))
            {
                state = CORRECT_ANSWER;
                Toast.makeText(newTestActivity.this,R.string.success, Toast.LENGTH_SHORT).show();
            }

            else
            {
                state = INCORRECT_ANSWER;
                Toast.makeText(newTestActivity.this,R.string.failure, Toast.LENGTH_SHORT).show();
            }
            LinearLayout linearLayout = findViewById(R.id.newTestLayout);
            View sendButton = findViewById(R.id.newTestSendButton);
            linearLayout.removeView(sendButton);
            setStateViews();
        }
    }
}
