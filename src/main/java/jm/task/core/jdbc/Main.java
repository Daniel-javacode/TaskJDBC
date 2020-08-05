package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl test = new UserDaoJDBCImpl();
        test.createUsersTable();
        for (int i = 0; i < 4; i++) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите имя");
            String name = sc.next();
            System.out.println("Введите фамилию");
            String lastName = sc.next();
            System.out.println("Введите возраст");
            byte age = sc.nextByte();
            test.saveUser(name, lastName, age);
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }
        List<User> temp = test.getAllUsers();
        for (int i=0;i<temp.size();i++){
            System.out.println(temp.get(i).toString());
        }
        test.cleanUsersTable();
        test.dropUsersTable();
    }
}
