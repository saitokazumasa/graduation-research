# たびすけっち

## ブランチ

- main ... メインブランチ
- release ... アプリリリースブランチ

その他ブランチ名は [GitHub フロー](https://docs.github.com/ja/get-started/using-github/github-flow) に従い、変更内容に沿った名前にする。

## 初期設定

1. コマンドラインで `npm install` を実行する
2. DATABASE を作成する
```postgresql
CREATE DATABASE tabisketch;
```
3. Run/Debug Configurations の環境変数を設定する

```xml
DATABASE_URL データベースのURL
DATABASE_USERNAME データベースのユーザー名
DATABASE_PASSWORD データベースのパスワード
GOOGLE_MAPS_API_KEY グーグルマップのAPIキー
```

3. CreateTables.run を実行する

## IntelliJ IDEA .run
- BuildAndRun ... ビルドと実行を同時に行う
- TailwindCSSBuild ... TailwindCSS のビルドを行う
- CreateTables.run ... テーブルを作成する
- DropTables.run ... テーブルを削除する