import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Service s = new Service();

    boolean exit = false;
    while (!exit) {
      System.out.println("Wybierz opcję:");
      System.out.println("1. Dodaj studenta");
      System.out.println("2. Wyświetl listę studentów");
      System.out.println("3. Wyszukaj studenta po imieniu");
      System.out.println("4. Wyjdź");

      int choice = Integer.parseInt(scanner.nextLine());

      switch (choice) {
        case 1:
          System.out.println("Podaj imię studenta:");
          String firstName = scanner.nextLine();
          System.out.println("Podaj nazwisko studenta:");
          String lastName = scanner.nextLine();
          System.out.println("Podaj wiek studenta:");
          int age = Integer.parseInt(scanner.nextLine());

          String dateOfBirth = null;
          while (dateOfBirth == null) {
            System.out.println("Podaj datę urodzenia studenta (YYYY-MM-DD):");
            String inputDate = scanner.nextLine();
            if (isValidDate(inputDate)) {
              dateOfBirth = inputDate;
            } else {
              System.out.println("Niepoprawny format daty. Spróbuj ponownie.");
            }
          }

          s.addStudent(new Student(firstName, lastName, age, dateOfBirth));
          break;
        case 2:
          var students = s.getStudents();
          for (Student current : students) {
            System.out.println(current.toString());
          }
          break;
        case 3:
          System.out.println("Podaj imię studenta do wyszukania:");
          String searchName = scanner.nextLine();
          var foundStudents = s.findStudentByName(searchName);
          if (foundStudents.isEmpty()) {
            System.out.println("Nie znaleziono studentów o imieniu: " + searchName);
          } else {
            for (Student student : foundStudents) {
              System.out.println(student.toString());
            }
          }
          break;
        case 4:
          exit = true;
          break;
        default:
          System.out.println("Niepoprawny wybór.");
      }
    }
    scanner.close();
  }

  private static boolean isValidDate(String dateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate.parse(dateStr, formatter);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}

class Student {
  private String firstName;
  private String lastName;
  private int age;
  private String dateOfBirth;

  public Student(String firstName, String lastName, int age, String dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.dateOfBirth = dateOfBirth;
  }

  public String getFirstName() {
    return firstName;
  }

  @Override
  public String toString() {
    return "Student{firstName='" + firstName + "', lastName='" + lastName + "', age=" + age + ", dateOfBirth='" + dateOfBirth + "'}";
  }
}

class Service {
  private List<Student> students;

  public Service() {
    this.students = new ArrayList<>();
  }

  public void addStudent(Student student) {
    students.add(student);
  }

  public List<Student> getStudents() {
    return students;
  }

  public List<Student> findStudentByName(String name) {
    List<Student> foundStudents = new ArrayList<>();
    for (Student student : students) {
      if (student.getFirstName().equalsIgnoreCase(name)) {
        foundStudents.add(student);
      }
    }
    return foundStudents;
  }
}
