package service.cart;

import config.Config;
import models.carts.Carts;
import java.util.ArrayList;
import java.util.List;


public class CartIMPL implements ICartService {
    static Config<List<Carts>> config = new Config<>();
    private List<Carts> cartsList;

    public CartIMPL() {
        this.cartsList = config.readFile(Config.URL_CART);
        if (this.cartsList == null) {
            this.cartsList = new ArrayList<>();
        }
    }


    @Override
    public List<Carts> findAll() {
        return cartsList;
    }

    @Override
    public void save(Carts carts) {
        if (findById(carts.getCartId()) == null) {
            cartsList.add(carts);
            updateData();
        } else {
            cartsList.set(cartsList.indexOf(carts), carts);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        int index = findIndexById(id);
        if (index != -1) {
            cartsList.remove(index);
        }
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Carts carts : cartsList) {
            if (carts.getCartId() > idMax) {
                idMax = carts.getCartId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CART, cartsList);
    }

    @Override
    public Carts findById(int id) {
        for (Carts carts : cartsList) {
            if (carts.getCartId() == id) {
                return carts;
            }
        }
        return null;
    }

    @Override
    public int findIndexById(int id) {
        for (int i = 0; i < cartsList.size(); i++) {
            if (cartsList.get(i).getCartId() == id) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void addToCart(int userId, int productId, int quantity) {
        Carts cart = findById(userId);
        if (cart == null) {
            cart = new Carts(getNewId(), userId);
            save(cart);
        }
        cart.addProduct(productId, quantity);
        save(cart);
    }

    @Override
    public void removeFromCart(int userId, int productId) {
        Carts cart = findById(userId);
        if (cart != null) {
            cart.removeProduct(productId);
            save(cart);
        }
    }

    @Override
    public Carts findByUserId(int userId) {
        for (Carts cart : cartsList) {
            if (cart.getUserId() == userId) {
                return cart;
            }
        }
        return null;
    }
}
