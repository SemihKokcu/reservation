package crowsoft.reservation.business.abstracts;

import java.util.List;

import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.dtos.user.GetAllUserResponse;

public interface UserService {
    DataResult<List<GetAllUserResponse>> getAll();
    DataResult<User> getById(int id);
    DataResult<User> getByEmail(String email);
    Result add(User user);
    Result update(User user);
    Result delete(int id);

}
