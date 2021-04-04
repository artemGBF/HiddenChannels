package lab2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Program {

    public static void main(String[] args) throws IOException {

        ArrayList<Double> timePackages = new ArrayList<>();
        ArrayList<Double> timePackagesShort = new ArrayList<>();
        int inc = 0;

        /**
         * Считываем время длин интервалов
         */
        try (BufferedReader br = new BufferedReader(new FileReader("12.csv"))) {
            br.readLine();
            while (br.ready()) {
                String[] split = br.readLine().split(",");
                double v = Double.parseDouble(split[1].replace("\"", ""));
                timePackages.add(v);
                inc++;
                if (inc >= 100) {
                    timePackagesShort.add(v);
                }
            }
        } catch (IOException e) {
            System.out.println("Wrong file");
        }


        /**
         * Строим гистограмму полную
         */
        /*Map<Double, Integer> counts = getBarCharMap(timePackages);
        BarChartDemo barChartDemo = new BarChartDemo("try", counts);
        barChartDemo.setVisible(true);*/

        /**
         * Строим гистограмму фильтрованную
         */
       /* Map<Double, Integer> countsShort = getBarCharMap(timePackagesShort);
        BarChartDemo barChartDemoShort = new BarChartDemo("try", countsShort);
        barChartDemoShort.setVisible(true);*/

        /**
         * Получаем бинарную строку
         */
        ArrayList<Double> intervals = new ArrayList<>();
        String binary = "";
        for (int i = 0; i < timePackages.size() - 1; i++) {
            intervals.add(timePackages.get(i + 1) - timePackages.get(i));
        }
        for (int i = 0; i < intervals.size(); i++) {
            Double aDouble = intervals.get(i);
            if (aDouble <= 0.5) {
                binary += "0";
            } else {
                binary += "1";
            }
        }
        String substring = binary.substring(80);
        List<String> word = new LinkedList<>();
        for (int i = substring.length(); i > 8; i -= 8) {
            word.add(0, substring.substring(i-8, i)+" ");
        }
        String full="";
        for (int i = 0; i < word.size(); i++) {
            full+=word.get(i);
        }
        System.out.println(full);
        String s = binaryToText(full);
        System.out.println(s);
    }

    /**
     * Функция для опредления параметров гистограммы
     *
     * @param timePackages
     * @return
     */
    private static Map<Double, Integer> getBarCharMap(ArrayList<Double> timePackages) {
        ArrayList<Double> intervals = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.#");
        for (int i = 0; i < timePackages.size() - 1; i++) {
            intervals.add(timePackages.get(i + 1) - timePackages.get(i));
        }
        Map<Double, Integer> counts = new TreeMap<>();
        for (double i = 0.1; i < 4.5; i += 0.1) {
            counts.put(Double.parseDouble(df.format(i).replace(",", ".")), 0);
        }
        for (int i = 0; i < intervals.size(); i++) {
            Double aDouble = Double.parseDouble(df.format(intervals.get(i)).replace(",", "."));
            Integer integer = counts.get(aDouble);
            counts.put(aDouble, integer + 1);
        }
        return counts;
    }

    public static String binaryToText(String binaryText) {
        String[] binaryNumbers = binaryText.split(" ");
        String text = "";

        for (String currentBinary : binaryNumbers) {
            int decimal = binaryToDecimal(currentBinary);
            char letra = (char) decimal;
            text += letra;
        }
        return text;
    }

    public static int binaryToDecimal(String binary) {
        int decimal = 0;
        int position = 0;
        for (int x = binary.length() - 1; x >= 0; x--) {
            // Saber si es 1 o 0; primero asumimos que es 1 y abajo comprobamos
            short digit = 1;
            if (binary.charAt(x) == '0') {
                digit = 0;
            }
            double multiplier = Math.pow(2, position);
            decimal += digit * multiplier;
            position++;
        }
        return decimal;
    }
}
