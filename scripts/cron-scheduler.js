// scripts/cron-scheduler.js
import { GoogleGenAI } from "@google/genai";
import fs from "fs";

// 環境変数から必要な各種キーやIDを読み込み
const NOTION_TOKEN = process.env.NOTION_TOKEN;
const NOTION_DATABASE_ID = process.env.NOTION_NOTION_DATABASE_ID;
const ai = new GoogleGenAI({ apiKey: process.env.GEMINI_API_KEY });

async function checkNotionAndDevelop() {
  console.log("🔍 Notionのデータベースをスキャン中...");

  // 1. Notionから「Claude生成待ち」のタスクを1件だけ検索
  const response = await fetch(`https://api.notion.com/v1/databases/${NOTION_DATABASE_ID}/query`, {
    method: "POST",
    headers: {
      "Authorization": `Bearer ${NOTION_TOKEN}`,
      "Notion-Version": "2022-06-28",
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      filter: {
        property: "ステータス",
        status: { equals: "Claude生成待ち" }
      },
      page_size: 1 // 1回につき1タスクずつ確実に処理する
    })
  });
  
  const data = await response.json();
  
  // 「Claude生成待ち」のタスクがない場合は、何もせず安全に終了
  if (!data.results || data.results.length === 0) {
    console.log("🎵 『Claude生成待ち』のタスクはありませんでした。");
    return;
  }

  // 2. 該当タスクから必要な情報を抽出
  const task = data.results[0];
  const pageId = task.id;
  const instruction = task.properties["仕様"]?.rich_text[0]?.plain_text || "";

  // Notionの公式「ID」プロパティから、自動採番された接頭辞と数値を結合して取得
  // 例: 接頭辞が "TASK"、番号が 45 の場合 ➔ "TASK-45" になります
  const prefix = task.properties["タスクID"]?.unique_id?.prefix || "TASK";
  const number = task.properties["タスクID"]?.unique_id?.number;
  
  if (!number) {
    console.error("❌ エラー: タスクID（Unique ID）の取得に失敗しました。Notionのプロパティ設定を確認してください。");
    process.exit(1);
  }
  
  const taskId = `${prefix}-${number}`;

  console.log(`🎯 対象タスクを発見しました: ${taskId}`);
  console.log(`📝 指示文の内容: ${instruction.substring(0, 30)}...`);

  // 3. 後続のGitHub Actions（ai-scheduler.yml）へ引き渡すために環境変数ファイルに保存
  const envContent = `TASK_ID=${taskId}\nPAGE_ID=${pageId}\nINSTRUCTION=${instruction}`;
  fs.writeFileSync("task_info.env", envContent);
  
  console.log("💾 task_info.env にタスク情報を格納しました。自動生成フェーズに進みます。");
}

checkNotionAndDevelop().catch((error) => {
  console.error("🚨 予期せぬエラーが発生しました:", error);
  process.exit(1);
});
