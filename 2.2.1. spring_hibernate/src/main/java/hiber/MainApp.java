package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        // Новые машины
        Car car1 = new Car("Audi A5", 70);
        Car car2 = new Car("Audi A6", 45);
        Car car3 = new Car("Audi A7", 30);
        Car car4 = new Car("Audi A4", 25);

        // создание пользователя
        userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", car3));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", car4));

        //все пользователи
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car = " + user.getCar().getModel() + " series " + user.getCar().getSeries());
            }
            System.out.println();
        }

        System.out.println("=== Поиск пользователя по машине ===");
        try {
            User userWithCar = userService.getUserByCarModelAndSeries("BMW X5", 30);
            System.out.println("Найден пользователь: " + userWithCar.getFirstName() + " " +
                    userWithCar.getLastName() + " с машиной " +
                    userWithCar.getCar().getModel());
        } catch (Exception e) {
            System.out.println("Пользователь с указанной машиной не найден");
        }

        context.close();
    }
}
