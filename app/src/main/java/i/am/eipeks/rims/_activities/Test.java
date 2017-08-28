package i.am.eipeks.rims._activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import i.am.eipeks.rims.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;


public class Test extends AppCompatActivity {

    private Button clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        clickMe = (Button) findViewById(R.id.click_me);
    }

    private Observable<String> createButtonObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> observableEmitter) throws Exception {
                clickMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        observableEmitter.onNext("Clicked");
                    }
                });
                observableEmitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        clickMe.setOnClickListener(null);
                    }
                });
            }
        });
    }
}
