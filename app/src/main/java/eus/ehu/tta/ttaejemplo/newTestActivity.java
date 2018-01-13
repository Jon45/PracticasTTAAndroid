package eus.ehu.tta.ttaejemplo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import junit.framework.Test;

import java.io.IOException;

public class newTestActivity extends AppCompatActivity {
    private static final java.lang.String STATE = "state";
    private static final String CURRENT_TEST = "current_test";
    private static final java.lang.String ANSWER_CHOSEN = "answer_chosen";
    private static final java.lang.String IS_HELP_OPEN = "is_help_open";
    private static final String CURRENT_POSITION = "current_position";
    private static final short TEST_START = 0;
    private static final short RADIO_BUTTON_PRESSED = 1;
    private static final short INCORRECT_ANSWER =2;
    private static final short CORRECT_ANSWER = 3;
    private BusinessInterface business;
    private short state;
    private TestTTA currentTest;
    private int answerChosen = -1;
    private boolean isHelpOpen=false;
    MediaController.MediaPlayerControl mediaPlayerControl;
    private int currentPositionPlayer = 0;
    private static final String TAG = newTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        UserData userData = UserData.getInstance();
        String dni = userData.getDni();
        String password = userData.getPassword();
        business = new BusinessReal(getResources().getString(R.string.baseURL),dni,password);
        if (savedInstanceState != null)
        {
            state = savedInstanceState.getShort(STATE);
            currentTest = savedInstanceState.getParcelable(CURRENT_TEST);
            answerChosen = savedInstanceState.getInt(ANSWER_CHOSEN);
            isHelpOpen = savedInstanceState.getBoolean(IS_HELP_OPEN);
            currentPositionPlayer = savedInstanceState.getInt(CURRENT_POSITION,0);
            createViews();
        }

        else
        {
            state = TEST_START;
            new getNewTestTask().execute();
        }
    }

    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putShort(STATE,state);
        savedInstanceState.putParcelable(CURRENT_TEST,currentTest);
        savedInstanceState.putInt(ANSWER_CHOSEN,answerChosen);
        savedInstanceState.putBoolean(IS_HELP_OPEN,isHelpOpen);
        if (mediaPlayerControl != null)
        {
            savedInstanceState.putInt(CURRENT_POSITION,mediaPlayerControl.getCurrentPosition());
        }
    }

    private void createViews()
    {
        TextView questionView = findViewById(R.id.newTestTextView);
        questionView.setText(currentTest.getPregunta());
        RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
        radioGroup.setOnCheckedChangeListener(new radioOnCheckedListener ());
        for (Opcion opcion : currentTest.getOpciones())
        {
            RadioButton optionView = new RadioButton(this);
            optionView.setText(opcion.getTexto());
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
            if (currentTest.getOpciones().get(answerChosen).getAyuda() !=null)
            {
                Button helpButton = new Button(newTestActivity.this);
                helpButton.setText(R.string.getHelp);
                helpButton.setOnClickListener(new helpOnClickListener());
                helpButton.setId(R.id.newTestHelpButton);
                linearLayout.addView(helpButton);
            }
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
        Opcion opcion = currentTest.getOpciones().get(answerChosen);
        String mimetype = opcion.getMimeTypeAyuda();
        if (mimetype.equals("text/html"))
        {
            if (URLUtil.isValidUrl(opcion.getAyuda()))
            {
                openExternalURL(opcion.getAyuda());
            }

            else
            {
                createWebView(opcion.getAyuda());
            }
        }
        else if(mimetype.startsWith("video"))
        {
            addVideoView(opcion.getAyuda());
        }

        else if (mimetype.startsWith("audio"))
        {
            addAudioView(opcion.getAyuda());
        }
    }

    private void openExternalURL(String ayuda) {
        Intent intent = new Intent (Intent.ACTION_VIEW);
        intent.setData(Uri.parse(ayuda));
        startActivity(intent);
    }

    private void createWebView(String ayuda) {
        WebView webView = new WebView(this);
        webView.loadData(ayuda,"text/html",null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        webView.setId(R.id.newTestHelp);
        LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        linearLayout.addView(webView);
    }

    private void addVideoView(String ayuda) {
        VideoView videoView = new VideoView(this);
        videoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        videoView.setVideoURI(Uri.parse(ayuda));
        videoView.setId(R.id.newTestHelp);
        MediaController controller = new myMediaController(this);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(currentPositionPlayer);
            }
        });

        LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        linearLayout.addView(videoView);

        mediaPlayerControl = videoView;
    }

    private void addAudioView(String ayuda) {
        AudioPlayer audioPlayer = new AudioPlayer(findViewById(R.id.newTestLayout), currentPositionPlayer, new Runnable() {
            @Override
            public void run()
            {

            }
        });
        try {
            audioPlayer.setAudioUri(Uri.parse(ayuda));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayerControl = audioPlayer;
    }

    private void closeHelp() {
        LinearLayout linearLayout = findViewById(R.id.newTestLayout);
        linearLayout.removeView(findViewById(R.id.newTestHelp));
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
            new sendTestTask().execute();
        }
    }

    private class myMediaController extends MediaController {
        public myMediaController(Context context) {
            super(context);
        }

        @Override
        public void hide() {
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event)
        {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            {
                finish();
            }
            return super.dispatchKeyEvent(event);
        }
    }


    public class getNewTestTask extends AsyncTask<Void,Void,TestTTA>
    {
        @Override
        protected TestTTA doInBackground(Void... voids) {
            return business.getNewTest(1);
        }

        @Override
        protected void onPostExecute(TestTTA testTTA) {
            super.onPostExecute(testTTA);
            currentTest = testTTA;
            createViews();
        }
    }

    private class sendTestTask extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Button sendButton = findViewById(R.id.newTestSendButton);
            sendButton.setEnabled(false);
            setRadioButtonsEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            int userId = UserData.getInstance().getUserID();
            return business.sendTest(userId,answerChosen);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue())
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

    private void setRadioButtonsEnabled(boolean enabled)
    {
        RadioGroup radioGroup = findViewById(R.id.newTestRadioGroup);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(enabled);
        }
    }
}
