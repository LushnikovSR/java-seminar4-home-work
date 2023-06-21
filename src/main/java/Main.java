import java.util.*;
import java.io.FileReader;
import java.io.IOException;

import static java.util.Collections.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> book;
        book = uploadData("src/List");

        Scanner scanner = new Scanner(System.in);

        int mode = 1;
        while ((mode == 1) | (mode == 2)){
            System.out.println();
            System.out.println("Выберите действие");
            System.out.println("1. добавить данные, 2. показать список");
            System.out.println("Для выхода из программы введите любое другое целое число");
            System.out.print("Введите число: ");
            mode = scanner.nextInt();
            if (mode == 1) {
                addData(book);
            }
            else if (mode == 2) {
                System.out.println("Выберите действие");
                System.out.println("1. в оригинале, 2. в сжатом формате, 3. отсортированный по возросту, " +
                        "4.отсортированный по возросту и полу");
                System.out.println("Для выхода из программы введите любое другое целое число");
                System.out.print("Введите число: ");
                int subMode = scanner.nextInt();
                if (subMode == 1) {
                    System.out.println();
                    showData(book);
                }
                else if (subMode == 2) {
                    System.out.println();
                    showCompactForm(book);
                } else if (subMode == 3) {
                    System.out.println();
                    int[] tempIndexArr = showAgeSorted(book);
                    for (int i = 0; i < book.get(0).size(); i++) {
                        for (ArrayList<String> strings : book) {
                            System.out.print(strings.get(tempIndexArr[i]) + " ");
                        }
                        System.out.println();
                    }
                } else if (subMode == 4) {
                    System.out.println();
                    ArrayList<Integer> tempIndexArr = showAgeSexSorted(book);
                    for (int i = 0; i < book.get(0).size(); i++) {
                        for (ArrayList<String> strings : book) {
                            System.out.print(strings.get(tempIndexArr.get(i)) + " ");
                        }
                        System.out.println();
                    }
                }
            }
        }

        scanner.close();
        System.out.println("конец работы программы");
    }


    public static ArrayList<ArrayList<String>> uploadData(String path) {
        ArrayList<ArrayList<String>> book = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            Scanner scan = new Scanner(fr);

            while (scan.hasNextLine()) {
                ArrayList<String> data = new ArrayList<>();
                addAll(data, scan.nextLine().split(", "));
                addAll(book, data);
            }
            scan.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return book;
    }


    public static void addData (ArrayList<ArrayList<String>> list) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите через пробел данные о человеке");
        System.out.print("Фамилия Имя Отчество Пол Возраст: ");
        String[] personData = scanner.nextLine().split(" ");

        if (personData.length == 5){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).add(personData[i]);
            }
        } else {
            System.out.print("Вы ввели неполные данные, попробуйте снова");
            addData(list);
        }
    }


    public static void showData(ArrayList<ArrayList<String>> list){
        for (int i = 0; i < list.get(0).size(); i++) {
            for (ArrayList<String> strings : list) {
                System.out.print(strings.get(i) + " ");
            }
            System.out.println();
        }
    }


    public static void showCompactForm (ArrayList<ArrayList<String>> list){
        for (int i = 0; i < list.get(0).size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if ((j == 1)) System.out.print(list.get(j).get(i).charAt(0) + ".");
                else if ((j == 2)) System.out.print(list.get(j).get(i).charAt(0) + ". ");
                else System.out.print(list.get(j).get(i) + " ");
            }
            System.out.println();
        }
    }


    public static int[] showAgeSorted(ArrayList<ArrayList<String>> list) {
        int[] tempIndexes = new int[list.get(4).size()];
        for (int i = 0; i < list.get(4).size(); i++) {
            tempIndexes[i] = i;
        }

        int[] tempAgeArray = new int[list.get(4).size()];
        for (int i = 0; i < list.get(4).size(); i++) {
            tempAgeArray[i] = Integer.parseInt(list.get(4).get(i));
        }

        for (int i = 0; i < list.get(4).size() - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < list.get(4).size(); j++) {
                if (tempAgeArray[j] < tempAgeArray[indexMin]) {
                    indexMin = j;
                }
            }
            int temp = tempAgeArray[i];
            tempAgeArray[i] = tempAgeArray[indexMin];
            tempAgeArray[indexMin] = temp;

            int tempIndex = tempIndexes[i];
            tempIndexes[i] = tempIndexes[indexMin];
            tempIndexes[indexMin] = tempIndex;
        }
        return tempIndexes;
    }


    public static ArrayList<Integer> showAgeSexSorted(ArrayList<ArrayList<String>> list) {
        ArrayList<Integer> insertIndexList = new ArrayList<>();
        int[] insertIndexArr = showAgeSorted(list);

        for (int i : insertIndexArr) {
            insertIndexList.add(i);
        }

        ArrayList<String> sortSexList = new ArrayList<>();
        for (int i = 0; i < list.get(3).size(); i++) {
            sortSexList.add(list.get(3).get(insertIndexList.get(i)));
        }

        ArrayList<String> manList = new ArrayList<>();
        ArrayList<Integer> manIndexList = new ArrayList<>();

        String sex = "м";
        for (int i = 0; i < (list.get(3).size() - manList.size()); i++) {
            if (sortSexList.get(i).equals(sex)) {
                manList.add(sortSexList.get(i));
                sortSexList.remove(i);
                manIndexList.add(insertIndexList.get(i));
                insertIndexList.remove(i);
                i = i - 1;
            }
        }
        insertIndexList.addAll(manIndexList);
        sortSexList.addAll(manList);
        return insertIndexList;
    }
}
//        Данные для добавления:
//        Соболева Маргарита Гордеевна ж 72
//        Давыдова Гера Евсеевна ж 62
//        Субботина Виктория Валентиновна ж 52
//        Комиссарова Дана Филипповна ж 82