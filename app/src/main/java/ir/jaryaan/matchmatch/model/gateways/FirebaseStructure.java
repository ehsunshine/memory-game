package ir.jaryaan.matchmatch.model.gateways;

/**
 * Created by ehsun on 5/16/2017.
 */

public class FirebaseStructure {

    public static final String ONLINE = "Online";
    public static final String SCOREBOARD = "Scoreboards";

    public static final class Scoreboards {
        public static final String LEVEL = "Level";
        public static final String SCORE = "Scores";

        public static final class Scores {
            public static final String NICKNAME = "Nickname";
            public static final String SCORE = "Score";
            public static final String REMAINING_TIME = "RemainingTime";
            public static final String SUBMIT_DATETIME = "SubmitDateTime";
        }
    }
}
