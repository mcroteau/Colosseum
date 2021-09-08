package sigma.support;

import chico.support.DbSecurityAccess;
import sigma.model.User;
import sigma.repo.UserRepo;
import qio.annotate.Element;
import qio.annotate.Inject;

import java.util.Set;

@Element
public class Access implements DbSecurityAccess {

    @Inject
    private UserRepo userRepo;

    public String getPassword(String username){
        String password = userRepo.getUserPassword(username);
        return password;
    }

    public Set<String> getRoles(String username){
        User user = userRepo.get(username);
        Set<String> roles = userRepo.getUserRoles(user.getId());
        return roles;
    }

    public Set<String> getPermissions(String username){
        User user = userRepo.get(username);
        Set<String> permissions = userRepo.getUserPermissions(user.getId());
        return permissions;
    }

}
