package hkust.cse.searchengine.engine.entity;

import java.io.Serializable;
import java.util.Vector;

public class Posting implements Serializable {
    public int pageID;
    public int freq;
    public Vector<Integer> wordPosition;

    public Posting(int pageID, int freq) {
        this.pageID = pageID;
        this.freq = freq;
        wordPosition = new Vector<Integer>();
    }

    public Vector<Integer> getPosition() {
        return wordPosition;
    }

    public int getPageID() {
        return pageID;
    }

    public int getFrequency() {
        return freq;
    }


    public boolean containsWordPos(int wordPos) {
        return wordPosition.contains(wordPos);
    }
}
//
