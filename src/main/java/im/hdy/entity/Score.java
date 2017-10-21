package im.hdy.entity;

import java.util.LinkedList;

/**
 * Created by hdy on 21/10/2017.
 */
public class Score {

    private LinkedList<ScoreDetail> scoreDetails;

    public LinkedList<ScoreDetail> getScoreDetails() {
        return scoreDetails;
    }

    public void setScoreDetails(LinkedList<ScoreDetail> scoreDetails) {
        this.scoreDetails = scoreDetails;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreDetails=" + scoreDetails +
                '}';
    }
}
