package udacity.com.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import udacity.com.bakingapp.R;
import udacity.com.bakingapp.fragment.IngredientesFragment;
import udacity.com.bakingapp.fragment.MasterRecipesDetailsFragment;
import udacity.com.bakingapp.fragment.PassoFragment;
import udacity.com.bakingapp.model.Receita;

import static udacity.com.bakingapp.util.FragmentUtil.popBackStack;


public class ReceitaDetalhesActivity extends AppCompatActivity
        implements MasterRecipesDetailsFragment.OnRecipeClickListener,
        PassoFragment.OnFragmentInteractionListener {

    public static Receita mReceita;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        Toolbar menuToolbar = (Toolbar) findViewById(R.id.recipe_details_toolbar);
        setSupportActionBar(menuToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            mReceita = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
            menuToolbar.setTitle(mReceita.getNome());
        }

        if (findViewById(R.id.tablet_linear_layout) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.master_list_fragment, MasterRecipesDetailsFragment.newInstance(mReceita))
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.master_list_container, IngredientesFragment.newInstance(mReceita))
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_list_container, MasterRecipesDetailsFragment.newInstance(mReceita))
                    .commit();
        }


    }

    @Override
    public void onIngredientsSelected(View view, Receita receita) {

        if (mTwoPane) {
            if (view.getId() == R.id.ingredients_card) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, IngredientesFragment.newInstance(mReceita))
                        .commit();
            }
        } else {
            if (view.getId() == R.id.ingredients_card) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, IngredientesFragment.newInstance(mReceita))
                        .addToBackStack(IngredientesFragment.class.getSimpleName())
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, OpcoesActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_list_container, PassoFragment.newInstance(
                            mReceita.getPassos().get(position), position))
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_list_container, PassoFragment.newInstance(
                            mReceita.getPassos().get(position), position))
                    .addToBackStack(PassoFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onMoveToNextStep(int position) {
        if (mReceita.getPassos().size() > position) {
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, PassoFragment.newInstance(
                                mReceita.getPassos().get(position), position))
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, PassoFragment.newInstance(
                                mReceita.getPassos().get(position), position))
                        .commit();
            }
        }
    }

    @Override
    public void onMoveToPreviousStep(int position) {
        if (position >= 0) {
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, PassoFragment.newInstance(
                                mReceita.getPassos().get(position), position))
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, PassoFragment.newInstance(
                                mReceita.getPassos().get(position), position))
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
    }
}
