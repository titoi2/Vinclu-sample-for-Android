
package sample.titoi2.vinclu.sample;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private VincleLed mVincleLed;
    private Button mButtonLightning;
    private Button mButtonOff;
    private Button mButtonBlinking;
    private Button mButtonViolentlyBlinking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonLightning = (Button) findViewById(R.id.buttonLightning);
        mButtonLightning.setOnClickListener(this);
        mButtonBlinking = (Button) findViewById(R.id.buttonBlinking);
        mButtonBlinking.setOnClickListener(this);
        mButtonViolentlyBlinking = (Button) findViewById(R.id.buttonViolentlyBlinking);
        mButtonViolentlyBlinking.setOnClickListener(this);
        mButtonOff = (Button) findViewById(R.id.buttonOff);
        mButtonOff.setOnClickListener(this);

        mVincleLed = VincleLed.getInstannce();

        // ボリュームコントロールを有効化
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();

        mVincleLed.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLightning:
                mVincleLed.lighting();
                break;
            case R.id.buttonBlinking:
                mVincleLed.blinking();
                break;
            case R.id.buttonViolentlyBlinking:
                mVincleLed.violentlyBlinking();
                break;
            case R.id.buttonOff:
                mVincleLed.stop();
                break;
        }

    }

}
