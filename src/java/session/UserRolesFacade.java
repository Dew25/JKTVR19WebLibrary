/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.User;
import entity.UserRoles;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jvm
 */
@Stateless
public class UserRolesFacade extends AbstractFacade<UserRoles> {

    @PersistenceContext(unitName = "JKTVR19WebLibraryPU")
    private EntityManager em;
    
    @EJB private RoleFacade roleFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }

    public boolean isRole(String roleName, User user) {
        try {
            UserRoles userRoles = (UserRoles) em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.role.roleName = :roleName AND ur.user = :user")
                   .setParameter("roleName", roleName)
                    .setParameter("user", user)
                    .getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTopRole(User user) {
        try {
            List<UserRoles> listUserRoles = em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.user = :user")
                    .setParameter("user", user)
                    .getResultList();
            for(UserRoles ur : listUserRoles){
                if("ADMIN".equals(ur.getRole().getRoleName())){
                    return "ADMIN";
                }
            }
            for(UserRoles ur : listUserRoles){
                if("MANAGER".equals(ur.getRole().getRoleName())){
                    return "MANAGER";
                }
            }
            for(UserRoles ur : listUserRoles){
                if("READER".equals(ur.getRole().getRoleName())){
                    return "READER";
                }
            }
            return "-";
        } catch (Exception e) {
            return "-";
        }
    }

    public void setNewRole(String roleName, User user) {
        this.em.createQuery("DELETE FROM UserRoles ur WHERE ur.user = :user")
                .setParameter("user", user)
                .executeUpdate();
        UserRoles userRoles;
        if("ADMIN".equals(roleName)){
            userRoles = new UserRoles(roleFacade.findByName("ADMIN"), user);
            this.create(userRoles);
            userRoles = new UserRoles(roleFacade.findByName("MANAGER"), user);
            this.create(userRoles);
            userRoles = new UserRoles(roleFacade.findByName("READER"), user);
            this.create(userRoles);
        }
        if("MANAGER".equals(roleName)){
            userRoles = new UserRoles(roleFacade.findByName("MANAGER"), user);
            this.create(userRoles);
            userRoles = new UserRoles(roleFacade.findByName("READER"), user);
            this.create(userRoles);
        }
        if("READER".equals(roleName)){
            userRoles = new UserRoles(roleFacade.findByName("READER"), user);
            this.create(userRoles);
        }
    }

    
    
}
