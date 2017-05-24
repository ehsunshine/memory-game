package ir.jaryaan.matchmatch.ui.board;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.entities.ScoreboardLevel;
import ir.jaryaan.matchmatch.model.manager.GameManager.GameStatus;
import ir.jaryaan.matchmatch.ui.base.BaseFragment;
import ir.jaryaan.matchmatch.ui.board.adapter.BoardAdapter;
import ir.jaryaan.matchmatch.utils.ConvertUtil;

/**
 * Created by ehsun on 5/12/2017.
 */

public class BoardFragment extends BaseFragment implements
        BoardContract.View, BoardAdapter.BoardEventListener {

    public static final String EXTRA_SCORE_ID = "SCORE_ID";
    private static final int SPAN_COUNT = 4;

    @Inject
    BoardContract.Presenter presenter;
    @BindView(R.id.root_container)
    View rootContainer;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_container)
    View loadingView;


    private Handler handler = new Handler();
    private Runnable flipCardRunnable;
    private Runnable winCardRunnable;
    private BoardAdapter adapter;
    private AlertDialog gameOverDialog;
    private String scoreID;
    private BoardFragmentEventListener boardFragmentEventListener;

    public static BoardFragment newInstance(@NonNull String scoreID) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_SCORE_ID, scoreID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof BoardFragmentEventListener) {
            boardFragmentEventListener = (BoardFragmentEventListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.scoreID = getArguments().getString(EXTRA_SCORE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void generateBoard(List<Card> cards) {
        adapter.setCards(cards);
    }

    @Override
    public void flipCardsBack(Card firstCard, Card secondCard) {
        flipCardRunnable = () -> adapter.flipCards(firstCard, secondCard);
        handler.postDelayed(flipCardRunnable, 1000L);
    }

    @Override
    public void winCards(Card firstCard, Card secondCard) {
        winCardRunnable = () -> adapter.winCards(firstCard, secondCard);
        handler.postDelayed(winCardRunnable, 1000L);
    }

    @Override
    public void showNickname(String nickname) {
        updateScreenTitle(nickname);
    }

    @Override
    public void showCards() {
        adapter = new BoardAdapter(this, SPAN_COUNT);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        presenter.onGameStarted();
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);
    }

    @Override
    protected void initViews() {
        boardFragmentEventListener.onTimerValueChanged(getString(R.string.timer, "0:00"));
        presenter.onViewInitialized();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(flipCardRunnable);
        handler.removeCallbacks(winCardRunnable);
        presenter.onViewDestroyed();
    }

    @Override
    public void onCardClick(@NonNull Card card) {
        presenter.onCardClicked(card);
    }


    @Override
    public void showGameOverDialog(@NonNull @GameStatus String gameStatus, @NonNull ScoreboardLevel scoreboardLevel) {
        if (gameOverDialog == null) {
            this.gameOverDialog = new AlertDialog.Builder(getContext())
                    .setTitle(gameStatus)
                    .setView(R.layout.view_game_over)
                    .create();

            this.gameOverDialog.setCanceledOnTouchOutside(false);
            this.gameOverDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    presenter.onGameRetried();
                    return true;
                }
                return false;
            });
            this.gameOverDialog.setOnShowListener(dialogInterface -> {
                TextView scoreTextView = (TextView) this.gameOverDialog.findViewById(R.id.score_text_view);
                TextView remainedTimeTextView = (TextView) this.gameOverDialog.findViewById(R.id.remaining_time_text_view);
                scoreTextView.setText(String.valueOf(scoreboardLevel.getScore()));
                remainedTimeTextView.setText(getString(R.string.timer,
                        ConvertUtil.convertMillisecondToMinutesAndSecond(scoreboardLevel.getTimeRemaining())));
                Button retryButton = (Button) this.gameOverDialog.findViewById(R.id.retry_button);
                Button submitButton = (Button) this.gameOverDialog.findViewById(R.id.submit_button);
                retryButton.setOnClickListener(view -> presenter.onGameRetried());
                submitButton.setOnClickListener(view -> {
                    presenter.onScoreSubmitted(scoreboardLevel);
                });
            });
        }
        this.gameOverDialog.show();
    }

    @Override
    public void hideGameOverDialog() {
        if (this.gameOverDialog != null) {
            this.gameOverDialog.dismiss();
            this.gameOverDialog = null;
        }
    }

    @NonNull
    @Override
    public String getScoreId() {
        return scoreID;
    }

    @Override
    public void showHomeScreen() {
        getActivity().onBackPressed();
    }

    @Override
    public void updateTimer(String remainingTime) {
        boardFragmentEventListener.onTimerValueChanged(getString(R.string.timer, remainingTime));
    }

    @Override
    public void updateScore(long score) {
        boardFragmentEventListener.onScoreValueChanged(getString(R.string.score, score));
    }

    public interface BoardFragmentEventListener {
        void onTimerValueChanged(String time);

        void onScoreValueChanged(String score);
    }
}
