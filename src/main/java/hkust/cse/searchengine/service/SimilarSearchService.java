package hkust.cse.searchengine.service;

import hkust.cse.searchengine.engine.indexer.Indexer;
import hkust.cse.searchengine.vo.FrequentVo;
import hkust.cse.searchengine.vo.PageVo;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

@Service
public class SimilarSearchService {

    private RecordManager recman;

    private Indexer indexer;

    @Autowired
    private SearchService searchService;

    public SimilarSearchService() throws IOException {
        recman = RecordManagerFactory.createRecordManager("fetch");
        indexer = new Indexer(recman);
    }

    public List<PageVo> similarSearch(int pageID) throws IOException {
        List<FrequentVo> topFiveWords = searchService.getTopFiveWords(pageID);

        List<String> words = new Vector<>();
        for (FrequentVo frequentVo: topFiveWords) {
            words.add(frequentVo.keyword);
        }

        List<PageVo> results = searchService.search(words);
        results.removeIf(pageVo -> pageVo.pageID == pageID);
        return results;
    }
}
