
package hkust.cse.searchengine.engine.entity;


public class ScoreMap {
    public ScoreMap(int pageID_input, double score_input) {
        pageID = pageID_input;
        score = score_input;
    }

    int pageID;
    double score;

    public double getScore() {
        return score;
    }

    public int getID() {
        return pageID;
    }

    public void set(int ID, double sc) {
        pageID = ID;
        score = sc;
    }

};
//
