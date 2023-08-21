package crowsoft.reservation.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.UserService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.UserDao;
import crowsoft.reservation.entities.concretes.User;

@Service
public class UserManager implements UserService {

    private UserDao userDao;
    
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<User>> getAll() {
        try {
            return new SuccessDataResult<List<User>>(this.userDao.findAll(),"User data listed");
        } catch (Exception e) {
            return new ErrorDataResult<List<User>>("Error: " + e.getMessage());
        }
    }

    @Override
    public DataResult<User> getById(int id) {
        User user = this.userDao.findById(id).orElse(null);
        try {
            return new SuccessDataResult<User>(user,"User data listed");
        } catch (Exception e) {
            return new ErrorDataResult<User>(e.getMessage());
        }
    }

    @Override
    public Result add(User user) {
        this.userDao.save(user);
        return new SuccessResult("User added successfully");
    }

    @Override
    public Result update(User user) {
       this.userDao.save(user);
        return new SuccessResult("User update successfully");
    }

    @Override
    public Result delete(int id) {
       this.userDao.deleteById(id);
       return new SuccessResult("User deleted");
    }
    
}
