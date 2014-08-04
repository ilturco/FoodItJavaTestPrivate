# FOODit Test

Create the set of REST API's that can answer the following questions:

  1.	Total number of orders for each restaurant
  2.	Total amount (money) of sales per restaurant
  3.	The most frequently ordered meals on FOODit Platform
  4.	The most frequently ordered category for each restaurant

It is expected that you will write unit tests.

Deploy the solution to your own appengine instance and provide FOODit with links to the API's in a email so we can
test it.

Your data to run the API's should be stored in Google Datastore


## Pre-requisites.
The solution is to be built on the thundr framework and deployed to google appengine.
You can read more about thundr here http://3wks.github.io/thundr/.

To simplify environment setup and allow you to jump straight into solving the test questions this project is has all the dependencies for thundr and appengine.

## Get started.
1. Clone this repository https://github.com/FOODit/FOODit-JavaTest.git

2. Run mvn install to install the application

3. Run mvn appengine:devserver to start the application locally.

4. Start writing the code to expose the data required in the test.

5. When your happy with your solution you will need to deploy it to your own appengine instance and
test that it works. Please also push the solution to your own public github repository so we can review the code

When you are ready please send us the urls of the created api's and the link to the projects github repository.

## Tips
To expose the api's you can simple return a JsonView from your controller method read about views here
http://3wks.github.io/thundr/1.1/thundr/views.html

Happy coding :)

# Solution
## methods
1. **Total number of orders for each restaurant**. My interpretation is that *for each restaurant* means that the method
shall not get any input but return the information... *for each restaurant*. Anyway I have also implemented a method that
requires the name of the restaurant in input.
    * https://foodit-test-sp.appspot.com/totalNumberOfOrders/bbqgrill this method requires the name of the restaurant. If no 
    restaurant is found a 0 value is returned
    * https://foodit-test-sp.appspot.com/totalNumberOfOrdersForEachRestaurant this method aggregates the sum of the
    orders for each restaurant. The object returned is an array of objects. The first field of the object is the name
    of the restaurant and the second field is the count of the orders.
2. **Total amount (money) of sales per restaurant**. For this requirement, as in the previous case, there are two methods
    implemented.
    * https://foodit-test-sp.appspot.com/totalAmountOfMoney/bbqgrill this method requires the name of the restaurant. If no 
    restaurant is found a 0 value is returned
    * https://foodit-test-sp.appspot.com/totalAmountOfMoneyForEachRestaurant this method aggregates the sum of the
    orders for each restaurant. The object returned is an array of objects. The first field of the object is the name
    of the restaurant and the second field is the sum of the value of the orders.
3. **The most frequently ordered meals on FOODit Platform**  
    * https://foodit-test-sp.appspot.com/mostFrequentlyOrderedMeal the result is an object with fields:
        * `id` identifier of the meal as specified in the file `menu-{restaurant}.json`
        * `name` name of the meal as specified in the file `menu-{restaurant}.json`
        * `numberOfOrders` number of orders, as counted from the internal DB (populated from the file `orders-{restaurant}.json`
        * `restaurant` name of the restaurant. The id of the meal is not unique, what is unique is the pair id + restaurant
4. **The most frequently ordered category for each restaurant** For this requirement, as in the cases 1 and 2, there are two methods
    implemented.
    * https://foodit-test-sp.appspot.com/mostFrequentlyOrderedCategory/{restaurant} the input is the name of the restaurant
    and the output is an object with fields:
        * `restaurant` name of the restaurant
        * `categories` array of name of categories. We have a list here because we could have more than one category
        with the same number of orders
        * `numberOfOrders` count of all the orders of that category
    * https://foodit-test-sp.appspot.com/mostFrequentlyOrderedCategoryForEachRestaurant this method does not require
    any input and returns an array of the same object type of the previous case 
5. **Get restaurants statistics**. This is a method developed for internal debug. It reports the complete set
of information for each single restaurant
    * https://foodit-test-sp.appspot.com/getRestaurantsStats it returns an array of objects with fields:
        * `id` internal id of the restaurant
        * `storeId` name of the restaurant. The name of the field is different because in the previous cases we
        returned *frond end* beans while here the bean returned is a 1:1 representation of the DB entity
        with the same number of orders
        * `totalNumberOfOrders` count of all the orders for that restaurant
        * `totalAmountOfSales` sum of all the sales for that restaurant
        * `categoryMap` a Map with key = category name and value = count of all the orders in that category


# implementation notes
## model
The model is very simple and is aimed just to implement the methods requested in this test.
This means that I have, for example, not kept any track of meal options. 
Three objects have been created:

* `Order` It contains a subset of the fields extracted from the files `orders-{restaurant}.json`
    * `orderId`
    * `storeId` The name of the restaurant
    * `totalValue`
    * `Set<LineItem>`
        * `id`
        * `quantity`
* `Meal` It contains a subset of the fields extracted from the files `menu-{restaurant}.json`
    * `id`
    * `mealId`
    * `restaurantName` the name of the restaurant
    * `mealCategory` the name of the category
    * `name` the name of the meal       
* `Restaurant` This is the materialised view used to keep the aggregated values.
    * `id` internal id of the restaurant
    * `storeId` name of the restaurant. The name of the field is different because in the previous cases we
    returned *frond end* beans while here the bean returned is a 1:1 representation of the DB entity
    with the same number of orders
    * `totalNumberOfOrders` count of all the orders for that restaurant
    * `totalAmountOfSales` sum of all the sales for that restaurant
    * `categoryMap` a Map with key = category name and value = count of all the orders in that category

## aggregation strategy
I am not that expert with NoSQL db in general (I only used MongoDB for some months) and I had never used Datastore before.
Anyway it looks like to achieve its massive scalability Datastore had to give up many complex features as for example aggregation operators (sum(), count()) used in conjunction with GROUP BY.
These operations are not efficient since the engine has to read every single record that matches the query. A possible solution
is to do the computation not when the client invokes the method (query time) but at insert/update time. This means creating a Restaurant model and each time we add a new order we also update the counts.
In this way, by this sort of de-normalisation, we spend an extra time when writing to save a lot when reading. The feasibility of such a solution of course depend on the read/write ratio.
See also http://stackoverflow.com/questions/21915793/count-number-of-objects-in-datastore-appengine-java
We can definitely assume that each restaurant wants to know the number of orders and the total sales at least once,
therefore it makes sense to pre-calculate these values. In a real environment it makes sense to create a monthly aggregation and of course consider the status of the order. 
In this test I have used both the approaches (query time and insert/updata time); the only reason for this choice is that this is a 
test and I wanted to show two different options. Anyway in a real production environment I would prefer to use a de-normalised
materialised view and use a insert/update approach.
*
*
*

## class structure


# todo
## exception handling
There are different approaches to deal with errors in a RESTful API (see: http://stackoverflow.com/questions/12806386/standard-json-api-response-format).
Currently there is no special handling for any unchecked exception and only for the method `https://foodit-test-sp.appspot.com/mostFrequentlyOrderedCategory/{restaurant}`
checks whether the restaurant exists and in case it does not returns a 404.
My idea is using both HTTP Headers (with the appropriate error code) and the json body to better explain what really
happened. Then the consumer of the API can decide whether to use or not the additional information.
## double load bug
From time to time the "load" action is invoked twice. This was happening on my local environment even with the original
sample application. As a consequence of that some objects can be duplicated. To fix that issue all the methods return a
`java.util.Set of objects` that override the `equals(Object o)` method. So no duplicated objects are returned in the output.