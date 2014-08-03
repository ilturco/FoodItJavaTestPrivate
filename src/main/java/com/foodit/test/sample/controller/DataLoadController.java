package com.foodit.test.sample.controller;

import com.foodit.test.solution.bean.dto.LineItem;
import com.foodit.test.solution.bean.dto.Meal;
import com.foodit.test.solution.bean.dto.Order;
import com.foodit.test.solution.bean.dto.Restaurant;
import com.foodit.test.solution.service.MenuServiceInterface;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class DataLoadController {

	public JspView instructions() {
		return new JspView("instructions.jsp");
	}

    private MenuServiceInterface menuService;
    public void setMenuService(MenuServiceInterface menuService) {
        this.menuService = menuService;
    }


    public StringView load() {
		Logger.info("Loading data");
		List<String> restaurants = Lists.newArrayList("bbqgrill", "butlersthaicafe", "jashanexquisiteindianfood", "newchinaexpress");
		List<RestaurantData> restaurantData = Lists.newArrayList();
		for (String restaurant : restaurants) {
			restaurantData.add(loadData(restaurant));
		}
		ofy().save().entities(restaurantData);



        for (String restaurant : restaurants) {
            List<Meal> mealList = loadDataRestaurantMenu(restaurant);
            for (int i = 0; i < mealList.size(); i++) {
                Meal meal = mealList.get(i);
                ofy().save().entity(meal).now();
            }
        }

        // TODO put this logic elsewhere...in a controller in the package com.foodit.test.solution.controller
        // Or, better, in a specific service to implement a separation of concerns

        //external cycle on restaurants
        for (String restaurant : restaurants) {
            Order[] orders =  loadDataTest(restaurant);
            Restaurant restaurantObject = new Restaurant(restaurant);
            //medium cycle on each order
            for (int i = 0; i < orders.length; i++) {

                Order order = orders[i];
                ofy().save().entities(order);

                float oldTotalAmountOfSales = restaurantObject.getTotalAmountOfSales();
                int oldTotalNumberOfOrders = restaurantObject.getTotalNumberOfOrders();

                restaurantObject.setTotalAmountOfSales(oldTotalAmountOfSales + order.getTotalValue());
                restaurantObject.setTotalNumberOfOrders(++oldTotalNumberOfOrders);
                //internal cycle on lineItems
                Set<LineItem> lineItemSet = order.getLineItems();
                for (Iterator<LineItem> iterator = lineItemSet.iterator(); iterator.hasNext(); ) {
                    LineItem lineItem = iterator.next();
                    //get category
                    //System.out.printf("\nline item id = " + lineItem.getId() + " restaurant = " + restaurant + " order = " + order.getId());
                    List<Meal> mealList = ofy().load().type(Meal.class).filter("mealId", lineItem.getId()).filter("restaurantName", restaurant).list();
                    Meal meal;
                    if(mealList.size() == 0){
                        Logger.warn("THERE IS NO ENTRY IN THE MENU FOR THE ORDER: " + order.getId() +
                        " RESTAURANT " + restaurant + " ITEM " + lineItem.getId());
                    } else {
                        meal = mealList.get(0);
                        Map<String, Long> map = restaurantObject.getCategoryMap();
                        int quantity = lineItem.getQuantity();
                        String mealCategory = meal.getMealCategory();
                        Long count = map.get(mealCategory);
                        if(count == null) {
                            count = new Long(quantity);
                        } else {
                            count = count + quantity;
                        }
                        map.put(mealCategory, count);
                        restaurantObject.setCategoryMap(map);
                    }
                }

            }
            ofy().save().entity(restaurantObject);
        }



		return new StringView("Data loaded.");
	}

    //the following three methods must be refactored. Called from the same cycle and moved to a service since I wont
    //to see no logic in the controller.
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

    private List<Meal> loadDataRestaurantMenu(String restaurantName){
        //TODO the file has already been read. Refactor!
        String ordersJson = readFile(String.format("menu-%s.json", restaurantName));
        try {
            return menuService.parse(ordersJson, restaurantName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
