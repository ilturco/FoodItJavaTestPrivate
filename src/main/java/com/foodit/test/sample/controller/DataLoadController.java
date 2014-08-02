package com.foodit.test.sample.controller;

import com.foodit.test.solution.dto.Order;
import com.foodit.test.solution.dto.Restaurant;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.http.HttpSupport;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.view.jsp.JspView;
import com.threewks.thundr.view.string.StringView;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class DataLoadController {

	public JspView instructions() {
		return new JspView("instructions.jsp");
	}

	public StringView load() {
		Logger.info("Loading data");
		List<String> restaurants = Lists.newArrayList("bbqgrill", "butlersthaicafe", "jashanexquisiteindianfood", "newchinaexpress");
		List<RestaurantData> restaurantData = Lists.newArrayList();
		for (String restaurant : restaurants) {
			restaurantData.add(loadData(restaurant));
		}
		ofy().save().entities(restaurantData);

        // TODO put this logic elsewhere...in a controller in the package com.foodit.test.solution.controller
        // Or, better, in a specific service to implement a separation of concerns

        for (String restaurant : restaurants) {
            Order[] orders =  loadDataTest(restaurant);
            Restaurant restaurantObject = new Restaurant(restaurant);

            //TODO can I avoid this cycle and save the whole array?
            for (int i = 0; i < orders.length; i++) {

                Order order = orders[i];
                ofy().save().entities(order);

                float oldTotalAmountOfSales = restaurantObject.getTotalAmountOfSales();
                int oldTotalNumberOfOrders = restaurantObject.getTotalNumberOfOrders();

                restaurantObject.setTotalAmountOfSales(oldTotalAmountOfSales + order.getTotalValue());
                restaurantObject.setTotalNumberOfOrders(++oldTotalNumberOfOrders);

            }
            ofy().save().entities(restaurantObject);
        }
		return new StringView("Data loaded.");
	}

	private RestaurantData loadData(String restaurantName) {
		String orders = readFile(String.format("orders-%s.json", restaurantName));
		String menu = readFile(String.format("menu-%s.json", restaurantName));
		RestaurantData restaurantLoadData = new RestaurantData(restaurantName, menu, orders);
		return restaurantLoadData;
	}

    private Order[] loadDataTest(String restaurantName) {
        //TODO the file has already been read. Refactor!
        String ordersJson = readFile(String.format("orders-%s.json", restaurantName));
        //TODO probably not the best place where declare and initialize the Gson serializer.
        Gson gson = new Gson();
        Order[] orders = gson.fromJson(ordersJson, Order[].class);
        return orders;
    }



    private String readFile(String resourceName) {
		URL url = Resources.getResource(resourceName);
		try {
			return IOUtils.toString(new InputStreamReader(url.openStream()));
		} catch (IOException e) {
			Logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void viewData(String restaurant, HttpServletResponse response) throws IOException {
		response.addHeader(HttpSupport.Header.ContentType, ContentType.ApplicationJson.value());
		RestaurantData restaurantLoadData = ofy().load().key(Key.create(RestaurantData.class, restaurant)).now();
		String data = restaurantLoadData.viewData();
		response.getWriter().write(data);
		response.setContentLength(data.getBytes().length);
	}

}
