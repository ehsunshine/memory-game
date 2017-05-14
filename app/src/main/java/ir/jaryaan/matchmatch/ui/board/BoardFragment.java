package ir.jaryaan.matchmatch.ui.board;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ir.jaryaan.matchmatch.R;
import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.ui.base.BaseFragment;
import ir.jaryaan.matchmatch.ui.board.adapter.BoardAdapter;

/**
 * Created by ehsun on 5/12/2017.
 */

public class BoardFragment extends BaseFragment implements BoardContract.View, BoardAdapter.BoardEventListener {

    private static final int SPAN_COUNT = 4;
    @Inject
    BoardContract.Presenter presenter;
    @BindView(R.id.root_container)
    View rootContainer;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_container)
    View loadingView;

    private BoardAdapter adapter;

    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        adapter.flipCards(firstCard, secondCard);
    }

    @Override
    protected void injectDependencies() {
        applicationComponent.inject(this);
        presenter.onBindView(this);

    }

    @Override
    protected void initViews() {

        updateScreenTitle(getString(R.string.app_name));
        adapter = new BoardAdapter(this, SPAN_COUNT);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        presenter.onViewInitialized();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onViewDestroyed();
    }

    @Override
    public void onCardClick(@NonNull View view, @NonNull Card card) {
        presenter.onCardClicked(card);
    }
}
