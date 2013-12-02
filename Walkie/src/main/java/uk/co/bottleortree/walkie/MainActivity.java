package uk.co.bottleortree.walkie;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity{

    private MainFragment mainFragment;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        private StepService stepService;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            stepService = ((StepService.StepBinder)service).getService();
            mainFragment.setStepCountDay(stepService.getStepCount());
            mainFragment.refreshValues();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            stepService = null;
        }

        public StepService getService() {
            return stepService;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mainFragment = new MainFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit();
        }

        super.onStart();
        startService(new Intent(this, StepService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this, StepService.class), serviceConnection, BIND_ABOVE_CLIENT);
    }

    @Override
    protected void onPause() {
        unbindService(serviceConnection);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
