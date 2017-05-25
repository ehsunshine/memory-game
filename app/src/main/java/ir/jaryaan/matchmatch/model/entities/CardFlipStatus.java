package ir.jaryaan.matchmatch.model.entities;

import ir.jaryaan.matchmatch.entities.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ehsun on 5/15/2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CardFlipStatus {

    @Card.CardStatus
    private int status;
    private Card firstCard;
    private Card secondCard;
}
