package com.mmmgdzl.search.service;

import com.mmmgdzl.common.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String keyword, Integer page, Integer rows) throws Exception;

}
