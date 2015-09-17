package test.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.webtrekk.webbtrekksdk.TrackingParameter;
import com.webtrekk.webbtrekksdk.TrackingParameter.Parameter;
import com.webtrekk.webbtrekksdk.Webtrekk;
import com.webtrekk.webbtrekksdk.WebtrekkApplication;


public class ShopExampleActivity extends ActionBarActivity {
    private Webtrekk webtrekk;
    TrackingParameter tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_example);
        webtrekk = Webtrekk.getInstance();
        webtrekk.track();
        tp = new TrackingParameter();
        tp.add(Parameter.PRODUCT, "Brauner Herrenschuh Leder: Mike")
                .add(Parameter.PRODUCT_CAT, "1", "Herren")
                .add(Parameter.PRODUCT_CAT, "2", "Schuhe")
                .add(Parameter.PRODUCT_CAT, "3", "Leder")
                .add(Parameter.PRODUCT_COUNT, "1")
                .add(Parameter.PRODUCT_COST, "99,95")
                .add(Parameter.PRODUCT_STATUS, "view")
                .add(Parameter.CURRENCY, "EUR");
        webtrekk.track(tp);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        webtrekk.startActivity("ShopActivity");
        webtrekk.track();
    }

    @Override
    public void onStop()
    {
        webtrekk.stopActivity();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonOrderClicked(View view) {
        tp.add(Parameter.PRODUCT_STATUS, "add")
        .add(Parameter.ACTION_NAME, "orderButton");
        webtrekk.track(tp);
    }
}