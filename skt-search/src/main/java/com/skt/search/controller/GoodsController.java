package com.skt.search.controller;

import com.skt.search.pojo.SearchRequest;
import com.skt.search.pojo.RequestResult;
import com.skt.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GoodsController {
    @Autowired
    private SearchService searchService;
    @PostMapping("page")
    public ResponseEntity<RequestResult> search(@RequestBody SearchRequest searchRequest){
        RequestResult result = this.searchService.search(searchRequest);
        if(result==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
