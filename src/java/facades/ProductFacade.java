/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nikita
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "SPTVR19WebPhoneShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

        public boolean productExist(String name, String price){
        try {
            return !em.createQuery("SELECT prod FROM Product prod WHERE prod.name = '" + name + "' AND prod.price = '" + price + "'")
                    .getResultList()
                    .isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
}
