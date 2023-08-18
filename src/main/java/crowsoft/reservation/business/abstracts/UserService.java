package crowsoft.reservation.business.abstracts;

import java.util.List;

import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.User;

public interface UserService {
    DataResult<List<User>> getAll();
    DataResult<User> getById(int id);
    Result add(User user);
    Result update(User user);
    Result delete(int id);
}
