package ir.jaryaan.matchmatch.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by ehsun on 5/12/2017.
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Card implements Parcelable {

    public static final int CARD_STATUS_NOTHING = 0;
    public static final int CARD_STATUS_WAITING_FOR_MATCH = 1;
    public static final int CARD_STATUS_MATCHED = 2;
    public static final int CARD_STATUS_NOT_MATCHED = 3;
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
    private int id;
    private CardImage cardImage;
    private boolean faceDown;
    private boolean collected;

    public Card(int id, @NonNull CardImage cardImage) {
        this.id = id;
        this.cardImage = cardImage;
        this.faceDown = true;
    }

    protected Card(Parcel in) {
        this.id = in.readInt();
        this.cardImage = in.readParcelable(CardImage.class.getClassLoader());
    }

    public void collect() {

        collected = true;
    }

    public boolean isMatchWith(Card newCard) {

        return this.getCardImage().getId() == newCard.getCardImage().getId();
    }

    public void flip() {
        faceDown = !faceDown;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.cardImage, flags);
    }

    @Retention(SOURCE)
    @IntDef({CARD_STATUS_WAITING_FOR_MATCH, CARD_STATUS_MATCHED, CARD_STATUS_NOT_MATCHED, CARD_STATUS_NOTHING})
    public @interface CardStatus {
    }
}
