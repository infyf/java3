import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        int choice = 0;

        do {
            System.out.println("Оберіть опцію:");
            System.out.println("1. Вивести всі можливі перестановки символів рядка");
            System.out.println("2. Перевірити, чи є рядок паліндромом");
            System.out.println("3. Інформація про дату та час польоту");
            System.out.println("4. Вийти з програми");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Введіть рядок для генерації перестановок:");
                    scanner.nextLine(); // Очистка буфера вводу
                    String StrPermutations = scanner.nextLine();
                    printPermutations(StrPermutations.toCharArray(), 0);
                    break;
                case 2:
                    System.out.println("Введіть рядок для перевірки на паліндром:");
                    scanner.nextLine(); // Очистка буфера вводу
                    String StrPalindrome = scanner.nextLine();
                    if (isPalindrome(StrPalindrome)) {
                        System.out.println("Рядок є паліндромом.");
                    } else {
                        System.out.println("Рядок не є паліндромом.");
                    }
                    break;
                case 3:
                    dispFlightInfo();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Невірний вибір.");
                    break;
            }
        } while (choice != 4);

        System.out.println("Програма завершує роботу.");
    }

    private static void printPermutations(char[] str, int index) {
        if (index == str.length - 1) {
            System.out.println(str);
        } else {
            for (int i = index; i < str.length; i++) {
                swap(str, index, i);
                printPermutations(str, index + 1);
                swap(str, index, i);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    private static boolean isPalindrome(String str) {
        str = str.toLowerCase();
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    private static void dispFlightInfo() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введіть час вильоту (рік-місяць-день година:хвилина): ");
        LocalDateTime depDT = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("Введіть тривалість польоту (години): ");
        int hrs = sc.nextInt();
        System.out.println("Введіть тривалість польоту (хвилини): ");
        int mins = sc.nextInt();

        System.out.println("Введіть часовий пояс пункту вильоту: ");
        ZoneId depZone = ZoneId.of(sc.next());

        System.out.println("Введіть часовий пояс пункту прильоту: ");
        ZoneId arrZone = ZoneId.of(sc.next());

        LocalDateTime arrDT = calcArrivalTime(depDT, Duration.ofHours(hrs).plusMinutes(mins), depZone, arrZone);

        dispDateTimeInfo("Дата/час вильоту в часовому поясі пункту А: ", depDT, depZone);
        dispDateTimeInfo("Дата/час прильоту в часовому поясі пункту Б: ", arrDT, arrZone);

        dispDateTimeInfo("Дата/час вильоту в UTC: ", ZonedDateTime.of(depDT, depZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), ZoneId.of("UTC"));
        dispDateTimeInfo("Дата/час прильоту в UTC: ", ZonedDateTime.of(arrDT, arrZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), ZoneId.of("UTC"));
    }

    private static void dispDateTimeInfo(String msg, LocalDateTime dt, ZoneId zone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zdt = ZonedDateTime.of(dt, zone);
        System.out.println(msg + zdt.format(formatter) + zone);
    }

    private static LocalDateTime calcArrivalTime(LocalDateTime depDT, Duration flightDur,
                                                     ZoneId depZone, ZoneId arrZone) {
        ZonedDateTime zDepDT = ZonedDateTime.of(depDT, depZone);
        ZonedDateTime zArrDT = zDepDT.plus(flightDur).withZoneSameInstant(arrZone);
        return zArrDT.toLocalDateTime();
    }
}
