package hkust.cse.searchengine.controller;

import hkust.cse.searchengine.service.SearchService;
import hkust.cse.searchengine.service.SimilarSearchService;
import hkust.cse.searchengine.vo.PageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController("/")
public class SearchEngineController {

    final SearchService searchService;

    final SimilarSearchService similarSearchService;

    public SearchEngineController(SearchService searchService, SimilarSearchService similarSearchService) {
        this.searchService = searchService;
        this.similarSearchService = similarSearchService;
    }

    @GetMapping("/similar_search")
    public List<PageVo> similarSearch(@RequestParam Integer pageID) throws IOException {
        return similarSearchService.similarSearch(pageID);
    }

    @GetMapping("/search")
    public List<PageVo> search(@RequestParam String keywords) throws IOException {
        return searchService.search(keywords);
    }

}
