/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.Product;
import entites.ProductTag;
import entites.Tag;
import java.util.ArrayList;
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

    public List<Tag> getRoles(Product p) {
        try {
            return em.createQuery("SELECT pt.name FROM ProductTag pt WHERE pt.product = :product")
                    .setParameter("product", p)
                    .getResultList();
            } catch (Exception e) {
                return (List<Tag>) new ArrayList<Tag>();
            }
        }

    public void setTagToProduct(Tag t, Product p) {
        if(!this.isTag(t.getName(), p)){
            ProductTag pt = new ProductTag(p, t);
            this.create(pt);
        }
    }
    public void removeRoleFromUser(Product p, Tag t) {
        if(this.isTag(t.getName(), p)){
            em.createQuery("DELETE FROM ProductTag pt WHERE pt.product = :product AND pt.tag = :tag")
                    .setParameter("product", p)
                    .setParameter("tag", t)
                    .executeUpdate();
        }
    }

    private boolean isTag(String name, Product p) {
        try {
            ProductTag productTag = (ProductTag) em.createQuery("SELECT productTag FROM ProductTag pt WHERE pt.role.name = :name AND pt.product = :product")
                    .setParameter("name", name)
                    .setParameter("product", p)
                    .getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void removeTagFromProduct(Tag t, Product p) {
        if(this.isTag(t.getName(), p)){
            em.createQuery("DELETE FROM ProductTag pt WHERE pt.product = :product AND pt.tag = :tag")
                    .setParameter("product", p)
                    .setParameter("tag", t)
                    .executeUpdate();
        }
    }
}
    
