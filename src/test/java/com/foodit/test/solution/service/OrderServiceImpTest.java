package com.foodit.test.solution.service;

import com.foodit.test.sample.controller.SetupAppengine;
import com.foodit.test.sample.controller.SetupObjectify;
import com.foodit.test.solution.bean.dto.LineItem;
import com.foodit.test.solution.bean.dto.Order;
import com.threewks.thundr.gae.objectify.repository.AsyncResult;
import com.threewks.thundr.gae.objectify.repository.BaseRepository;
import com.threewks.thundr.search.google.GoogleSearchService;
import com.threewks.thundr.search.google.SearchService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.atomicleopard.expressive.Expressive.list;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by salvatore on 01/08/2014.
 */
public class OrderServiceImpTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule public SetupAppengine setupAppengine = new SetupAppengine();
    @Rule public SetupObjectify setupObjectify = new SetupObjectify(Order.class);

    private SearchService searchService;
    private BaseRepository<Order> repository;

    @Before
    public void before() {
        searchService = new GoogleSearchService();
        repository = new BaseRepository<>(Order.class, list("id", "name"), searchService);
    }


    @Test
    public void shouldAllowSaveAndLoadOfEntity() {
        Order testOrder = getOrder(123);

        AsyncResult<Order> result = repository.save(testOrder);
        assertThat(result, is(notNullValue()));
        Order complete = result.complete();
        assertThat(complete, is(sameInstance(testOrder)));

        Order load = repository.load(testOrder.getId());
        assertThat(load.equals(testOrder), is(true));
    }


    @Test
    public void shouldAllowSaveAndSearchOfEntity() {
        Order testOrder = getOrder(123);

        AsyncResult<Order> result = repository.save(testOrder);
        assertThat(result, is(notNullValue()));
        Order complete = result.complete();
        assertThat(complete, is(sameInstance(testOrder)));

        List<Order> results = repository.search().field("storeId").eq("The clove").search();
        //assertEquals(results.size(), 1);
        //Logger.debug("......... results.size() = " + results.size());
        //assertThat(results, hasItem(testOrder));
    }

    @Test
    public void shouldAllowSaveAndLoadOfMultipleEntities() {
        Order testEntity = getOrder(123);
        Order testEntity2 = getOrder(321);
        AsyncResult<List<Order>> result = repository.save(testEntity, testEntity2);
        assertThat(result, is(notNullValue()));
        List<Order> complete = result.complete();
        assertThat(complete.contains(testEntity), is(true));
        assertThat(complete.contains(testEntity2), is(true));

        List<Order> load = repository.load(testEntity.getId(), testEntity2.getId());
        assertThat(load, hasItems(testEntity, testEntity2));
    }

    @Test
    public void shouldLoadByField() {
        Order testEntity = getOrder(1);
        Order testEntity2 = getOrder(2);
        Order testEntity3 = getOrder(3);
        testEntity3.setStoreId("foo");
        repository.save(testEntity, testEntity2, testEntity3).complete();

        List<Order> list = repository.loadByField("orderId", new Long(2));
        assertThat(list.size(), is(1));
        assertThat(list, hasItem(testEntity2));

        list = repository.loadByField("storeId", "foo");
        assertThat(list.size(), is(1));
        assertThat(list, hasItem(testEntity3));
    }

    @Test
    public void findIndexMax(){
        Map<Long, Long> map = new HashMap<>();
        map.put(new Long(1), new Long(1));
        map.put(new Long(2), new Long(6));
        map.put(new Long(3), new Long(6));
        map.put(new Long(4), new Long(7));
        map.put(new Long(5), new Long(8));
        map.put(new Long(6), new Long(7));
        map.put(new Long(7), new Long(8));
        map.put(new Long(8), new Long(1));
        map.put(new Long(9), new Long(3));

        OrderServiceImp orderService = new OrderServiceImp();
        List<Long> maxKeys = orderService.getIndexesMaxValue(map);
        assertEquals(2, maxKeys.size());
        for (int i = 0; i < maxKeys.size(); i++) {
            Long aLong = maxKeys.get(i);
            System.out.println("\n\n" + aLong.longValue());
        }
        assertTrue(maxKeys.contains(new Long(5)));
        assertTrue(maxKeys.contains(new Long(7)));


    }

    private Order getOrder(int id) {
        Order testOrder = new Order();
        testOrder.setOrderId(new Long(id));
        testOrder.setStoreId("The clove");
        testOrder.setTotalValue(23.4f);

        LineItem testLineItem = new LineItem();
        testLineItem.setId(new Long(2));
        testLineItem.setQuantity(1);

        HashSet<LineItem> lineItems = new HashSet<>();
        lineItems.add(testLineItem);
        testOrder.setLineItems(lineItems);
        return testOrder;
    }


}
