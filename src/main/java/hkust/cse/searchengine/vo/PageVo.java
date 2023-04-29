package hkust.cse.searchengine.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PageVo implements Serializable {

    public int pageID;

    public double score;

    public String title;

    public String url;

    public Date lastModifiedDate;

    public int size;


    public List<FrequentVo> freqList;

    public List<String> parentLinks;

    public List<String> childLinks;

    public PageVo(int pageID, double score, String title, String url, Date lastModifiedDate, int size,
                  List<FrequentVo> freqList, List<String> parentLinks,
                  List<String> childLinks) {
        this.pageID = pageID;
        this.score = score;
        this.title = title;
        this.url = url;
        this.lastModifiedDate = lastModifiedDate;
        this.size = size;
        this.freqList = freqList;
        this.parentLinks = parentLinks;
        this.childLinks = childLinks;
    }
}
