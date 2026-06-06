# 05 GitHub Actions と Artifact

## レッスンのリポジトリ

- 前のレッスン: [git-lesson-04-pr-review](https://github.com/bp22029/git-lesson-04-pr-review.git)
- このレッスン: [git-lesson-05-actions-artifact](https://github.com/bp22029/git-lesson-05-actions-artifact.git)

## 学習目標

この演習では、GitHub Actions を使って Java プログラムを自動実行し、生成された `results/summary.txt` を Artifact として保存する流れを体験します。

終了後、次のことを説明できるようになることを目標にします。

- GitHub Actions がいつ実行されるかを確認できる
- workflow ファイルの基本構造を読める
- Java プログラムの実行結果を GitHub 上で確認できる
- Artifact として保存されたファイルを確認できる

## 前提条件

- GitHub に push できる
- GitHub の Actions タブを開ける
- Java の基本的な `main` メソッドを読める

ローカルで Java を確認する場合:

```bash
javac -version
```

```bash
java -version
```

## 作業場所と clone

このレッスンでは、01 で作成した授業用の親フォルダを使います。

まだこのレッスンのリポジトリを clone していない場合は、授業用の親フォルダで clone します。

```bash
git clone <GitHubリポジトリURL>
```

```bash
cd <リポジトリ名>
```

確認ポイント:

- 授業用の親フォルダの中に、このレッスンのリポジトリフォルダが作成されたか確認します。
- ターミナルが clone したリポジトリの中にいる状態で、手順に進みます。

## このリポジトリで使うファイル

```text
.github/workflows/run-analysis.yml
data/sample.csv
src/AnalyzeData.java
results/
```

## Actions と Artifact の流れ

```text
git push
   |
   v
GitHub Actions
   |
   v
javac src/AnalyzeData.java
java -cp src AnalyzeData
   |
   v
results/summary.txt
   |
   v
Artifact: analysis-summary
```

## 手順

ここまでは、GitHub Actions と Artifact の考え方です。ここからは、説明を読みながら全員で同じ操作を行う基本手順です。

### 1. workflow ファイルを確認する

VS Code で `.github/workflows/run-analysis.yml` を開きます。

確認ポイント:

- `on: push` があるか確認します。
- `actions/setup-java@v4` を使っているか確認します。
- `javac src/AnalyzeData.java` を実行しているか確認します。
- `java -cp src AnalyzeData` を実行しているか確認します。
- `actions/upload-artifact@v4` を使っているか確認します。
- Artifact の `path` が `results/summary.txt` になっているか確認します。

### 2. ローカルで Java プログラムを実行する

Java が使える場合は、次を実行します。

```bash
javac src/AnalyzeData.java
```

```bash
java -cp src AnalyzeData
```

確認ポイント:

- `results/summary.txt` が作成されるか確認します。
- VS Code で `results/summary.txt` を開き、件数や平均値が書かれているか確認します。

Java が実行できない場合:

- ローカル実行は無理に続けなくてかまいません。
- GitHub Actions 上では Java がセットアップされるため、push 後に Actions の結果を確認します。
- 作業記録に「ローカルの Java 実行環境は未確認」と書いてください。

### 3. Java ファイルを編集する

VS Code で `src/AnalyzeData.java` を開き、`buildSummary` メソッド内の出力見出しを変更します。

変更前:

```java
"分析結果の要約",
```

変更後:

```java
"研究データの分析結果",
```

### 4. もう一度ローカルで確認する

Java が使える場合:

```bash
javac src/AnalyzeData.java
```

```bash
java -cp src AnalyzeData
```

確認ポイント:

- `results/summary.txt` の見出しが変わっているか確認します。

### 5. 変更を commit する

```bash
git status
```

ローカルで `results/summary.txt` を作成できた場合:

```bash
git add src/AnalyzeData.java results/summary.txt
```

ローカルで `results/summary.txt` を作成できなかった場合:

```bash
git add src/AnalyzeData.java
```

```bash
git commit -m "分析結果の見出しを改善"
```

確認ポイント:

- `git log --oneline` で commit が増えているか確認します。

```bash
git log --oneline
```

### 6. GitHub に push する

```bash
git push
```

確認ポイント:

- push 後、GitHub の Actions タブを開きます。
- `Run analysis` という workflow が実行されるか確認します。

### 7. GitHub Actions の結果を確認する

Actions タブで最新の実行結果を開きます。

確認ポイント:

- `Checkout repository` が成功しているか
- `Set up Java` が成功しているか
- `Run analysis` が成功しているか
- `Upload summary` が成功しているか

### 8. Artifact を確認する

Actions の実行結果画面で Artifact を探します。

確認ポイント:

- `analysis-summary` という Artifact が表示されるか確認します。
- Artifact の中に `summary.txt` が含まれているか確認します。
- `summary.txt` に件数、平均値、最小値、最大値が書かれているか確認します。

## 演習課題

ここからは、上の説明付き基本手順を参考にして、自分で小さな変更を作り、push によって GitHub Actions が動くことを確認します。

課題:

- 新しい feature ブランチを作る
- `src/AnalyzeData.java` の出力文を1か所変更する
- 変更を commit する
- GitHub に push する
- GitHub Actions の実行結果を確認する
- Artifact `analysis-summary` の `summary.txt` を確認する

ブランチ名の例:

```bash
git switch -c feature/change-summary-message
```

変更例:

```java
"分析結果の要約",
```

を次のように変更します。

```java
"研究データの分析結果",
```

commit の例:

```bash
git status
```

```bash
git add src/AnalyzeData.java
```

ローカルで `results/summary.txt` を再生成した場合:

```bash
git add src/AnalyzeData.java results/summary.txt
```

```bash
git commit -m "分析結果の見出しを変更"
```

push の例:

```bash
git push -u origin feature/change-summary-message
```

この課題の主目的:

- Java の文法ではなく、push をきっかけに自動処理が動き、生成物が Artifact として保存される流れを体感すること

## 期待される結果

- push によって GitHub Actions が実行される
- GitHub Actions が `src/AnalyzeData.java` をコンパイルして実行する
- `results/summary.txt` が生成される
- `analysis-summary` Artifact が保存される
- Actions のログで各 step の成功を確認できる

## よくあるエラー

### GitHub Actions が実行されない

確認すること:

- `.github/workflows/run-analysis.yml` が存在するか
- GitHub の Actions タブが有効になっているか
- push したブランチに workflow ファイルが含まれているか

### GitHub Actions が失敗する

確認すること:

- `src/AnalyzeData.java` が存在するか
- `data/sample.csv` が存在するか
- `results/summary.txt` が作成される処理があるか
- workflow の `path` が `results/summary.txt` になっているか

### Artifact が見つからない

確認すること:

- Actions の実行が成功しているか
- `Upload summary` step が成功しているか
- `actions/upload-artifact@v4` を使っているか

## 提出物

- GitHub リポジトリ URL
- GitHub Actions の成功画面の記録
- Artifact `analysis-summary` を確認した記録
- `summary.txt` の内容メモ
- 授業中の作業記録

## 振り返り質問

- GitHub Actions は、手作業のどの部分を自動化していましたか。
- Artifact として保存する利点は何ですか。
- workflow の中で、Java をコンパイルして実行している step はどこですか。

## 提出前チェックリスト

- [ ] `.github/workflows/run-analysis.yml` を確認した
- [ ] `src/AnalyzeData.java` を編集した
- [ ] Java プログラムをローカルまたは Actions で実行した
- [ ] 変更を commit した
- [ ] GitHub に push した
- [ ] GitHub Actions の成功を確認した
- [ ] Artifact `analysis-summary` を確認した
- [ ] 作業記録を書いた
