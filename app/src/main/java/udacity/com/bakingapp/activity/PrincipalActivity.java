package udacity.com.bakingapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import udacity.com.bakingapp.idlingresource.SimpleIdlingResource;
import udacity.com.bakingapp.R;
import udacity.com.bakingapp.util.BaixarReceitas;
import udacity.com.bakingapp.adapter.PrincipalAdapter;
import udacity.com.bakingapp.model.Receita;

public class PrincipalActivity extends AppCompatActivity implements PrincipalAdapter.ListItemClickListener,
        BaixarReceitas.DelayerCallback {

    private static final int SPAN_COUNT = 2;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected PrincipalAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Receita> mReceitas = new ArrayList<>();
    private ProgressBar mProgressBar;
    private TextView mErrorMessageText;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipes_recycler);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorMessageText = (TextView) findViewById(R.id.error_text);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        } else {
            setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaixarReceitas.downloadRecipe(PrincipalActivity.this, mIdlingResource);
            }
        });
        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("RECIPES")) {
                ArrayList<Receita> receitas = savedInstanceState
                        .getParcelableArrayList("RECIPES");
                mAdapter = new PrincipalAdapter(this, receitas, this);
                mRecyclerView.setAdapter(mAdapter);
            }
        } else {

            mAdapter = new PrincipalAdapter(this, mReceitas, this);
            mRecyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.VISIBLE);

            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessageText.setVisibility(View.INVISIBLE);

            getIdlingResource();
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BaixarReceitas.downloadRecipe(this, mIdlingResource);
    }

    @Override
    public void onDone(ArrayList<Receita> receitas) {
        mReceitas = receitas;
        mSwipeRefreshLayout.setRefreshing(false);
        showDataContent();
        mAdapter.refill(receitas);
    }

    @Override
    public void onFailed(Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
        showErrorMessage();
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessageText.setVisibility(View.VISIBLE);
    }

    private void showDataContent() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessageText.setVisibility(View.INVISIBLE);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onListItemClick(int selectedItemIndex) {
        startActivity(new Intent(this, ReceitaDetalhesActivity.class).putExtra(Intent.EXTRA_TEXT,
                mReceitas.get(selectedItemIndex)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                BaixarReceitas.downloadRecipe(this, mIdlingResource);
                return true;
            case R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, OpcoesActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Receita> recipesContents = mReceitas;
        outState.putParcelableArrayList("RECIPES", recipesContents);
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
}
