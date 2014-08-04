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
    * `https://foodit-test-sp.appspot.com/totalNumberOfOrders/bbqgrill` this method requires the name of the restaurant. If no 
    restaurant is found a 0 value is returned
    * `https://foodit-test-sp.appspot.com/totalNumberOfOrdersForEachRestaurant` this method aggregates the sum of the
    orders for each restaurant. The object returned is an array of objects. The first field of the object is the name
    of the restaurant and the second field is the count of the orders.
2. **Total amount (money) of sales per restaurant**. For this requirement, as in the previous case, there are two methods
    implemented.
    * `https://foodit-test-sp.appspot.com/totalAmountOfMoney/bbqgrill` this method requires the name of the restaurant. If no 
    restaurant is found a 0 value is returned
    * `https://foodit-test-sp.appspot.com/totalAmountOfMoneyForEachRestaurant` this method aggregates the sum of the
    orders for each restaurant. The object returned is an array of objects. The first field of the object is the name
    of the restaurant and the second field is the sum of the value of the orders.
3. **The most frequently ordered meals on FOODit Platform**  
    * `https://foodit-test-sp.appspot.com/mostFrequentlyOrderedMeal` the result is an object with fields:
        * `id` identifier of the meal as specified in the file `menu-{restaurant}.json`
        * `name` name of the meal as specified in the file `menu-{restaurant}.json`
        * `numberOfOrders` number of orders, as counted from the internal DB (populated from the file `menu-{restaurant}.json`
        * `restaurant` name of the restaurant. The id of the meal is not unique, what is unique is the pair id + restaurant
4. **The most frequently ordered category for each restaurant** For this requirement, as in the cases 1 and 2, there are two methods
    implemented.
    * `https://foodit-test-sp.appspot.com/mostFrequentlyOrderedCategory/{restaurant}`
    * `https://foodit-test-sp.appspot.com/mostFrequentlyOrderedCategoryForEachRestaurant`
5. **Get restaurants statistics**. This is a method developed for internal debug. It reports the complete set
of information for each single restaurant
    * `https://foodit-test-sp.appspot.com/getRestaurantsStats`

## model

## todo
# exception handling


