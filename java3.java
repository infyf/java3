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
            System.out.println("������ �����:");
            System.out.println("1. ������� �� ������ ������������ ������� �����");
            System.out.println("2. ���������, �� � ����� ����������");
            System.out.println("3. ���������� ��� ���� �� ��� �������");
            System.out.println("4. ����� � ��������");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("������ ����� ��� ��������� ������������:");
                    scanner.nextLine(); // ������� ������ �����
                    String StrPermutations = scanner.nextLine();
                    printPermutations(StrPermutations.toCharArray(), 0);
                    break;
                case 2:
                    System.out.println("������ ����� ��� �������� �� ��������:");
                    scanner.nextLine(); // ������� ������ �����
                    String StrPalindrome = scanner.nextLine();
                    if (isPalindrome(StrPalindrome)) {
                        System.out.println("����� � ����������.");
                    } else {
                        System.out.println("����� �� � ����������.");
                    }
                    break;
                case 3:
                    dispFlightInfo();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("������� ����.");
                    break;
            }
        } while (choice != 4);

        System.out.println("�������� ������� ������.");
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

        System.out.println("������ ��� ������� (��-�����-���� ������:�������): ");
        LocalDateTime depDT = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("������ ��������� ������� (������): ");
        int hrs = sc.nextInt();
        System.out.println("������ ��������� ������� (�������): ");
        int mins = sc.nextInt();

        System.out.println("������ ������� ���� ������ �������: ");
        ZoneId depZone = ZoneId.of(sc.next());

        System.out.println("������ ������� ���� ������ ��������: ");
        ZoneId arrZone = ZoneId.of(sc.next());

        LocalDateTime arrDT = calcArrivalTime(depDT, Duration.ofHours(hrs).plusMinutes(mins), depZone, arrZone);

        dispDateTimeInfo("����/��� ������� � �������� ���� ������ �: ", depDT, depZone);
        dispDateTimeInfo("����/��� �������� � �������� ���� ������ �: ", arrDT, arrZone);

        dispDateTimeInfo("����/��� ������� � UTC: ", ZonedDateTime.of(depDT, depZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), ZoneId.of("UTC"));
        dispDateTimeInfo("����/��� �������� � UTC: ", ZonedDateTime.of(arrDT, arrZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), ZoneId.of("UTC"));
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
