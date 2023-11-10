package service.order;

import models.orders.Orders;
import models.user.Users;
import service.IGenericService;

import java.util.List;

public interface IOrderService extends IGenericService<Orders> {
    List<Orders> findByName(String name);
}
