package a1klik.csp203.a1klik;

import android.content.Context;
import android.content.Intent;

public class ExceptionHandlerRedirector {
    private Context stored;

    public ExceptionHandlerRedirector(Context stored) {
        this.stored = stored;
    }

    public ExceptionHandlerRedirector() {
        MainActivity tmp=new MainActivity();
        stored=tmp.stored;
    }
    public void loadNewActivity()
    {
        Intent iNewActivity = new Intent(stored, MainActivity.class);
        stored.startActivity(iNewActivity);
    }

}
