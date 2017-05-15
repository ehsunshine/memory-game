package ir.jaryaan.matchmatch.ui.board.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.jaryaan.matchmatch.entities.Card;
import ir.jaryaan.matchmatch.ui.board.viewholder.CardViewHolder;

/**
 * Created by ehsun on 5/12/2017.
 */

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CARD = 1;
    private static final int TYPE_INVISIBLE = 2;
    private final int spanCount;

    private List<Card> cardList = new ArrayList<>();
    private BoardEventListener boardEventListener;
    private Map<Integer, CardViewHolder> viewholders = new HashMap<>();

    public BoardAdapter(@NonNull BoardEventListener boardEventListener, int spanCount) {

        this.boardEventListener = boardEventListener;
        this.spanCount = spanCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder.Builder()
                .parent(parent)
                .cardListener(card -> boardEventListener.onCardClick(card))
                .cardNumber(cardList.size())
                .spanNumber(spanCount)
                .build();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Card card = cardList.get(position);
        ((CardViewHolder) holder).onBindView(card);
        viewholders.put(position, ((CardViewHolder) holder));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CARD;
    }

    public void setCards(@NonNull List<Card> cardList) {
        this.cardList.clear();
        this.cardList.addAll(cardList);

        notifyDataSetChanged();
    }

    public void flipCards(Card firstCard, Card secondCard) {
        viewholders.get(cardList.indexOf(firstCard)).flipCardLeft();
        viewholders.get(cardList.indexOf(secondCard)).flipCardLeft();
    }

    public void winCards(Card firstCard, Card secondCard) {
        viewholders.get(cardList.indexOf(firstCard)).moveCardToDeck();
        viewholders.get(cardList.indexOf(secondCard)).moveCardToDeck();
    }

    public interface BoardEventListener {
        void onCardClick(@NonNull Card card);
    }


}
