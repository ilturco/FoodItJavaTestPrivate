import com.foodit.test.sample.controller.DataLoadController;
import com.foodit.test.solution.controller.DataSearchController;
import com.threewks.thundr.action.method.MethodAction;
import com.threewks.thundr.route.Route;
import com.threewks.thundr.route.Routes;

import static com.threewks.thundr.route.RouteType.GET;

public class ApplicationRoutes {
	public static class Names {
		public static final String ListTasks = "list";
		public static final String CreateTask = "create-task";
		public static final String ViewTask = "view-task";
		public static final String UpdateTask = "update-task";
		public static final String StartTask = "start-task";
		public static final String StopTask = "stop-task";
		public static final String FinishedTask = "finished-task";
		public static final String ArchiveTask = "archive-task";

		public static final String LoadData = "load-data";

		public static final String ViewInstructions = "view-instructions";
		public static final String ViewData = "view-data";
        public static final String TotalOrders = "total-orders";
        public static final String TotalOrdersEachRestaurant = "total-orders-for-each-restaurant";
        public static final String TotalAmountOfMoney = "total-amount-of-money";
        public static final String TotalAmountOfMoneyEachRestaurant = "total-amount-of-money-for-each-restaurant";
        public static final String MostFrequentlyOrderedMeal = "most-frequently-ordered-meal";
        public static final String MostFrequentlyOrderedCategory = "most-frequently-ordered-category";

    }

	public void addRoutes(Routes routes) {

		// Loader
		routes.addRoute(new Route(GET, "/load/", Names.LoadData), new MethodAction(DataLoadController.class, "load"));

		// Instructions
		routes.addRoute(new Route(GET, "/", Names.ViewInstructions), new MethodAction(DataLoadController.class, "instructions"));
		routes.addRoute(new Route(GET, "/restaurant/{restaurant}/download", Names.ViewData), new MethodAction(DataLoadController.class, "viewData"));

        // Route for API #1 - Total number of orders for each restaurant
        routes.addRoute(new Route(GET, "/totalNumberOfOrders/{restaurant}", Names.TotalOrders), new MethodAction(DataSearchController.class, "getTotalNumberOfOrders"));
        routes.addRoute(new Route(GET, "/totalNumberOfOrdersForEachRestaurant", Names.TotalOrdersEachRestaurant), new MethodAction(DataSearchController.class, "getTotalNumberOfOrdersForEachRestaurant"));

        // Route for API #2 - Total amount of money (sales) for each restaurant
        routes.addRoute(new Route(GET, "/totalAmountOfMoney/{restaurant}", Names.TotalAmountOfMoney), new MethodAction(DataSearchController.class, "getTotalAmountOfMoney"));
        routes.addRoute(new Route(GET, "/totalAmountOfMoneyForEachRestaurant", Names.TotalAmountOfMoneyEachRestaurant), new MethodAction(DataSearchController.class, "getTotalAmountOfMoneyForEachRestaurant"));


        routes.addRoute(new Route(GET, "/mostFrequentlyOrderedMeal", Names.MostFrequentlyOrderedMeal), new MethodAction(DataSearchController.class, "getMostFrequentlyOrderedMeal"));
        routes.addRoute(new Route(GET, "/mostFrequentlyOrderedCategory", Names.MostFrequentlyOrderedCategory), new MethodAction(DataSearchController.class, "getMostFrequentlyOrderedCategory"));

    }
}
