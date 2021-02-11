/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.Product;
import entites.ProductTag;
import entites.Tag;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nikita
 */
@Stateless
public class ProductTagFacade extends AbstractFacade<ProductTag> {

    @PersistenceContext(unitName = "SPTVR19WebPhoneShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductTagFacade() {
        super(ProductTag.class);
    }
    public List<Tag> findTags(Product product) {
        try {
            List<Tag> listTag = (List<Tag>) em.createQuery("SELECT productTag FROM ProductTag pt WHERE pt.product = :name ")
                    .setParameter("name", product)
                    .getResultList();
            return listTag;
        } catch (Exception e) {
            return null;
        }
    }
    
}
