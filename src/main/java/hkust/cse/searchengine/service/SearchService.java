package hkust.cse.searchengine.service;

import hkust.cse.searchengine.engine.*;
import hkust.cse.searchengine.engine.entity.fPair;
import hkust.cse.searchengine.engine.entity.Page;
import hkust.cse.searchengine.engine.entity.ScoreMap;
import hkust.cse.searchengine.engine.indexer.Indexer;
import hkust.cse.searchengine.vo.FrequentVo;
import hkust.cse.searchengine.vo.PageVo;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class SearchService {

    private RecordManager recman;

    private Indexer indexer;

    public SearchService() throws IOException {
        recman = RecordManagerFactory.createRecordManager("fetch");
        indexer = new Indexer(recman);
    }

    public List<PageVo> search(String keywords) throws IOException {
        String[] words = keywords.split(Pattern.quote(" "));
        return search(Arrays.asList(words));
    }

    public List<PageVo> search(List<String> keywords) throws IOException {

        Vector<String> input = new Vector<>(keywords);

        List<ScoreMap> searchPages = SearchEngine.search(input);

        if (searchPages == null) {
            return null;
        }

        List<PageVo> pageResult = new ArrayList<>();

        for (ScoreMap scoredPage : searchPages) {
            int pageID = scoredPage.getID();

            Page page = indexer.getPageContent(pageID);

            Vector<Integer> parents = indexer.getParentLink(pageID);
            Vector<Integer> children = indexer.getChildLink(pageID);

            pageResult.add(new PageVo(
                    pageID,
                    scoredPage.getScore(),
                    page.getTitle(),
                    page.getURL(),
                    page.getModifiedDate(),
                    page.getPageSize(),
                    getTopFiveWords(pageID),
                    getURLs(parents),
                    getURLs(children)
            ));
        }

        return pageResult;
    }

    public List<FrequentVo> getTopFiveWords(int pageID) throws IOException {
        Vector<fPair> topFiveWords = indexer.getTopTenWord(pageID);
        List<FrequentVo> words = new ArrayList<>();

        int count = 0;
        for (int m = 0; m < 5 + count; m++) {
            int wordID = topFiveWords.get(m).getID();
            if (indexer.getWord(wordID) == null) {
                count++;
                continue;
            }

            words.add(new FrequentVo(
                    indexer.getWord(wordID),
                    indexer.getFrequency(wordID, pageID)
            ));
        }
        return words;
    }

    private List<String> getURLs(Vector<Integer> pageIDs) throws IOException {
        List<String> links = new ArrayList<>();
        if (pageIDs != null) {
            for (Integer id: pageIDs) {
                Page p = indexer.getPageContent(id);
                if (p != null) {
                    links.add(p.getURL());
                }
            }
        }
        return links;
    }
}
