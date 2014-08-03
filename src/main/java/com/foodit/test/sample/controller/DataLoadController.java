package com.foodit.test.sample.controller;

import com.foodit.test.solution.service.LoadDataServiceInterface;
import com.googlecode.objectify.Key;
import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.http.HttpSupport;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.view.jsp.JspView;
import com.threewks.thundr.view.string.StringView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class DataLoadController {

	public JspView instructions() {
		return new JspView("instructions.jsp");
	}

    private LoadDataServiceInterface loadDataService;

    public void setLoadDataService(LoadDataServiceInterface loadDataService) {
        this.loadDataService = loadDataService;
    }

    public StringView load() {
		Logger.info("Loading data");
        loadDataService.loadAllData();
        return new StringView("Data loaded.");
	}

    public void viewData(String restaurant, HttpServletResponse response) throws IOException {
        response.addHeader(HttpSupport.Header.ContentType, ContentType.ApplicationJson.value());
        RestaurantData restaurantLoadData = ofy().load().key(Key.create(RestaurantData.class, restaurant)).now();
        String data = restaurantLoadData.viewData();
        response.getWriter().write(data);
        response.setContentLength(data.getBytes().length);
    }





}
