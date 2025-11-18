package hiber.dao;

import hiber.model.Car;
import java.util.List;

public interface CarDao {
    void add(Car car);
    void addAll(List<Car> cars);
    List<Car> listCars();
    Car getCarById(Long id);
}
