package service.cart;

import models.carts.Carts;
import service.IGenericService;

public interface ICartService extends IGenericService<Carts> {
    public void addToCart(int userId, int productId, int quantity);
    public void removeFromCart(int userId, int productId);
    public Carts findByUserId(int userId);
}
