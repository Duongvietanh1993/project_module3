package service.order;

import config.Config;
import models.orders.OrderStatus;
import models.orders.Orders;
import models.products.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderIMPL implements IOrderService {
    static Config<List<Orders>> config = new Config<>();
    private List<Orders> ordersList;

    public OrderIMPL() {
        this.ordersList = config.readFile(Config.URL_ORDER);
        if (this.ordersList == null) {
            this.ordersList = new ArrayList<>();
        }
    }

    @Override
    public List<Orders> findAll() {
        return ordersList;
    }

    @Override
    public void save(Orders orders) {
        if (findById(orders.getOrderId()) == null) {
            ordersList.add(orders);
            updateData();
        } else {
            ordersList.set(ordersList.indexOf(orders), orders);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        int index = findIndexById(id);
        if (index != -1) {
            ordersList.remove(index);
            updateData();
        }
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Orders orders : ordersList) {
            if (orders.getOrderId() > idMax) {
                idMax = orders.getOrderId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDER, ordersList);
    }

    @Override
    public Orders findById(int id) {
        for (Orders orders : ordersList) {
            if (orders.getOrderId() == id) {
                return orders;
            }
        }
        return null;
    }

    @Override
    public int findIndexById(int id) {
        for (int i = 0; i < ordersList.size(); i++) {
            if (ordersList.get(i).getOrderId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Orders> findByName(String name) {
        List<Orders> foundOrder = new ArrayList<>();
        for (Orders orders : ordersList) {
            if (orders.getName().trim().contains(name)){
                foundOrder.add(orders);
            }
        }
        return foundOrder;
    }
}
