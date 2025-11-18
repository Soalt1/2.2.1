package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);


        //Создаем пользователей
    List<User> users = new ArrayList<>();
    users.add(new User("User1", "Lastname1", "user1@mail.ru"));
    users.add(new User("User2", "Lastname2", "user2@mail.ru"));
    users.add(new User("User3", "Lastname3", "user3@mail.ru"));
    users.add(new User("User4", "Lastname4", "user4@mail.ru"));


    // создаем машины
    List<Car> cars = new ArrayList<>();
    cars.add(new Car("Audi A4", 70));
    cars.add(new Car("Audi A5", 45));
    cars.add(new Car("Audi A6", 30));
    cars.add(new Car("Audi A7", 25));


        System.out.println("=== Сохранение пользователей и машин в БД ===");
        for (User user : users) {
            userService.add(user);
        }
        carService.addAll(cars);

        // получаем списки
        System.out.println("\n=== Получение данных из БД ===");
        List<User> usersFromDb = userService.listUsers();
        List<Car> carsFromDb = carService.listCars();

        System.out.println("Пользователи из БД:");
        for (User user : usersFromDb) {
            System.out.println("Id = " + user.getId() + ", Name = " + user.getFirstName());
        }

        System.out.println("\nМашины из БД:");
        for (Car car : carsFromDb) {
            System.out.println("Id = " + car.getId() + ", Model = " + car.getModel() + ", Series = " + car.getSeries());
        }

        // Раздаем машины
        System.out.println("\n=== Раздача машин пользователям ===");
        for (int i = 0; i < usersFromDb.size(); i++) {
            User user = usersFromDb.get(i);
            Car car = carsFromDb.get(i);
            user.assignCar(car);
            System.out.println("Пользователю " + user.getFirstName() + " выдана машина " + car.getModel());
        }

        // сохранение
        System.out.println("\n=== Сохранение пользователей с машинами ===");
        for (User user : usersFromDb) {
            userService.add(user);  // Сохраняем каждого пользователя отдельно
        }

        // проверка
        System.out.println("\n=== Финальный список пользователей ===");
        List<User> finalUsers = userService.listUsers();
        for (User user : finalUsers) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car = " + user.getCar().getModel() + " series " + user.getCar().getSeries());
            }
            System.out.println();
        }

        // поиск
        System.out.println("=== Поиск пользователя по машине ===");
        User userWithCar = userService.getUserByCarModelAndSeries("Audi A5", 45);
        if (userWithCar != null) {
            System.out.println("Найден пользователь: " + userWithCar.getFirstName() + " " +
                    userWithCar.getLastName() + " с машиной " +
                    userWithCar.getCar().getModel());
        } else {
            System.out.println("Пользователь с указанной машиной не найден");
        }

        context.close();
    }
}


