package crowsoft.reservation.business.abstracts;

import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.User;

public interface AuthService {
    Result Login();
    DataResult<User> Register(User user);
}
