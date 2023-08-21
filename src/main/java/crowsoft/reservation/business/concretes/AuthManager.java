package crowsoft.reservation.business.concretes;

import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.AuthService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.User;


@Service
public class AuthManager implements AuthService {

    @Override
    public Result Login() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Login'");
    }

    @Override
    public DataResult<User> Register(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Register'");
    }
    
}
