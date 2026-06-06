import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AnalyzeData {
    public static void main(String[] args) throws IOException {
        Path csvPath = findCsvFile("sample.csv");

        // data/sample.csv があるフォルダを基準に、プロジェクトのルートを決める
        Path projectRoot = csvPath.getParent().getParent();

        List<Double> values = readValues(csvPath);
        String summary = buildSummary(values);

        Path resultsDir = projectRoot.resolve("results");
        Files.createDirectories(resultsDir);
        Files.writeString(resultsDir.resolve("summary.txt"), summary);

        System.out.println("読み込んだCSV: " + csvPath);
        System.out.println("出力先: " + resultsDir.resolve("summary.txt"));
        System.out.println("results/summary.txt を作成しました。");
    }

    private static Path findCsvFile(String fileName) throws IOException {
        Path currentDir = Path.of("").toAbsolutePath().normalize();

        // まず、通常どおり「現在のフォルダ/data/sample.csv」を確認する
        Path directPath = currentDir.resolve("data").resolve(fileName);
        if (Files.exists(directPath)) {
            return directPath;
        }

        // 見つからない場合は、現在のフォルダ以下から探す
        try (Stream<Path> paths = Files.walk(currentDir, 6)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .filter(path -> path.getParent() != null)
                    .filter(path -> path.getParent().getFileName().toString().equals("data"))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFileException(
                            "data/" + fileName + " が見つかりません。現在の場所: " + currentDir
                    ));
        }
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
                "研究データの分析結果",
                "",
                "件数: " + values.size(),
                String.format("平均値: %.2f", average),
                String.format("最小値: %.2f", min),
                String.format("最大値: %.2f", max),
                "");
    }
}