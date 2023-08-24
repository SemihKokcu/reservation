package crowsoft.reservation.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import crowsoft.reservation.business.abstracts.UserService;
import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.dtos.user.GetAllUserResponse;

@RestController
@RequestMapping("api/users")
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public ResponseEntity<DataResult<List<GetAllUserResponse>>> getAll() {
        try {
            DataResult<List<GetAllUserResponse>> result = userService.getAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DataResult<User>> getById(@PathVariable int id) {
        try {
            DataResult<User> result = userService.getById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody User user) {
        Result result = userService.add(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Result> update(@RequestBody User user) {
        Result result = userService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> delete(@PathVariable int id) {
        Result result = userService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get{email}")
    public ResponseEntity<DataResult<User>> getByEmail(@PathVariable String email) {
        try {
            DataResult<User> result = userService.getByEmail(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDataResult<>("Something went wrong when getting the user by email"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
