package ir.jaryaan.matchmatch.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ehsun on 5/12/2017.
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card implements Parcelable {
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

    public void flip() {
        faceDown = !faceDown;
    }

    public Card(int id, @NonNull CardImage cardImage)
    {
        this.id = id;
        this.cardImage = cardImage;
    }

    protected Card(Parcel in) {
        this.id = in.readInt();
        this.cardImage = in.readParcelable(CardImage.class.getClassLoader());
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Card)) {
            return false;
        }

        Card card = (Card) object;
        return this.id == card.getId();
    }
}
