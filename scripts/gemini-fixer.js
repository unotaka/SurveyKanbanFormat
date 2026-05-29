// scripts/gemini-fixer.js
import { GoogleGenAI } from "@google/genai";
import fs from "fs";
import path from "path";

const ai = new GoogleGenAI({ apiKey: process.env.GEMINI_API_KEY });

async function fixCode() {
  // 1. 元の指示（task_info.json）とテストのエラーログ（test_result.log）を読み込む
  let originalPrompt = "";
  try {
    const taskData = JSON.parse(fs.readFileSync("task_info.json", "utf8"));
    originalPrompt = taskData.INSTRUCTION;
  } catch (e) {
    console.error("❌ task_info.json が見つかりません。");
    process.exit(1);
  }

  let errorLog = "";
  try {
    errorLog = fs.readFileSync("test_result.log", "utf8");
  } catch (e) {
    console.error("❌ test_result.log が見つかりません。");
    process.exit(1);
  }

  console.log("🛠️ Geminiがエラーログを解析し、コードの修正案を考えています...");

  const response = await ai.models.generateContent({
    model: "gemini-2.5-pro",
    contents: `
      あなたはシニアフルスタックエンジニアです。
      あなたが実装したTypeScriptコードのテストが失敗しました。以下の【元の要件】と【エラーログ】を解析し、バグを修正した正しいコードを出力してください。

      【元の要件】: 
      ${originalPrompt}

      【テストのエラーログ】:
      ${errorLog}

      【出力ルール】:
      必ず以下の形式で、修正が必要なファイル名と修正後のコード一式をコードブロックで出力してください。解説などの雑談は一切不要です。コードのみを出力してください。

      [FILE_START: src/components/Example.tsx]
      \`\`\`tsx
      // ここに修正後のコード
      \`\`\`
      [FILE_END]
    `,
  });

  // 2. 返答から修正コードを抽出して上書き
  const text = response.text;
  const fileRegex = /\[FILE_START:\s*(.+?)\][\s\S]*?```[\s\S]*?\n([\s\S]*?)```[\s\S]*?\[FILE_END\]/g;
  let match;
  let fixedCount = 0;

  while ((match = fileRegex.exec(text)) !== null) {
    const filePath = match[1].trim();
    const codeContent = match[2];

    fs.mkdirSync(path.dirname(filePath), { recursive: true });
    fs.writeFileSync(filePath, codeContent, "utf8");
    console.log(`🔧 バグを修正し、ファイルを更新しました: ${filePath}`);
    fixedCount++;
  }

  if (fixedCount === 0) {
    console.log("🤔 Geminiから有効な修正コードブロックが返されませんでした。返答内容:\n", text);
  }
}

fixCode().catch(console.error);
