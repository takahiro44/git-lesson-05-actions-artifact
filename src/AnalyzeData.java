import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeData {
    public static void main(String[] args) throws IOException {
        List<Double> values = readValues(Path.of("data", "sample.csv"));
        String summary = buildSummary(values);

        Path resultsDir = Path.of("results");
        Files.createDirectories(resultsDir);
        Files.writeString(resultsDir.resolve("summary.txt"), summary);

        System.out.println("results/summary.txt を作成しました。");
    }

    private static List<Double> readValues(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        List<Double> values = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] columns = lines.get(i).split(",");
            values.add(Double.parseDouble(columns[2]));
        }

        return values;
    }

    private static String buildSummary(List<Double> values) {
        double sum = 0.0;
        double min = values.get(0);
        double max = values.get(0);

        for (double value : values) {
            sum += value;
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        double average = sum / values.size();

        return String.join(System.lineSeparator(),
                "分析結果の要約",
                "",
                "件数: " + values.size(),
                String.format("平均値: %.2f", average),
                String.format("最小値: %.2f", min),
                String.format("最大値: %.2f", max),
                "");
    }
}
