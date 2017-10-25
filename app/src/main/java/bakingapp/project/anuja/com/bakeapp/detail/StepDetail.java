package bakingapp.project.anuja.com.bakeapp.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Step;
import butterknife.ButterKnife;

/**
 * Created by USER on 20-07-2017.
 */

public class StepDetail extends AppCompatActivity {

    private List<Step> step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        if(savedInstanceState==null)
        {
            step = getIntent().getParcelableArrayListExtra("step");

            if(step!=null) {
                StepDetailViewFragment stepDetailFragment = new StepDetailViewFragment();
                stepDetailFragment.setPosition(getIntent().getIntExtra("position",0));
                stepDetailFragment.setStep(step);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            }
        }
    }
}
