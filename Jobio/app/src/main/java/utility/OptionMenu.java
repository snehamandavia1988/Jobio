package utility;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab360io.jobio.fieldapp.R;
import com.lab360io.jobio.fieldapp.acLogin;
import com.lab360io.jobio.fieldapp.acSplash;
/**
 * Created by SAI on 3/17/2016.
 */
public class OptionMenu {
    public MenuInflater getCommonMenu(AppCompatActivity act, Menu menu) {
        MenuInflater mi = act.getMenuInflater();
        mi.inflate(R.menu.common, menu);
        Logger.debug(act.getClass() +" "+ acLogin.class);
        if (act.getClass() != acLogin.class) {
            menu.removeItem(R.id.menuMasterClear);
        }
        return mi;
    }

    public void handleMenuItemClick(final AppCompatActivity ac, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuHelp:
                Logger.debug("Help");
                break;
            case R.id.menuAbout:
                Logger.debug("About");
                break;
            case R.id.menuContact:
                Logger.debug("Contact");
                break;
            case R.id.menuMasterClear:
                Logger.debug("Master clear");
                // Create the Snackbar
                final Snackbar snackbar = Snackbar.make(ac.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
// Get the Snackbar's layout view
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();


// Inflate our custom view
                LayoutInflater mInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View snackView = mInflater.inflate(R.layout.custom_snackbar_optionmenu, null);
// Configure the view
                TextView imageView = (TextView) snackView.findViewById(R.id.txtText);
                imageView.setText(ac.getString(R.string.strMastercleanMessage));
                Button btnConfirm = (Button) snackView.findViewById(R.id.btnConfirm);
                Button btnCancel = (Button) snackView.findViewById(R.id.btnCancel);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyFunction.masterClear(ac.getApplicationContext());
                        Intent i = new Intent(ac.getApplicationContext(), acSplash.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ac.startActivity(i);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

// Add the view to the Snackbar's layout
                layout.addView(snackView, 0);
// Show the Snackbar
                snackbar.show();

                break;
            case android.R.id.home:
                Logger.debug("android.R.id.home");
                break;
        }
    }
}
