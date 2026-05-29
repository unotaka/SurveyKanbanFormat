// scripts/gemini-coder.js
import { GoogleGenAI } from "@google/genai";
import fs from "fs";
import path from "path";

// 1. Gemini APIの初期化（環境変数から自動読み込み）
const ai = new GoogleGenAI({ apiKey: process.env.GEMINI_API_KEY });

async function generateCode() {
  // --- 💡 修正：環境変数ではなく、JSONファイルから指示を安全に読み込む ---
  let prompt = "";
  try {
    const fileContent = fs.readFileSync("task_info.json", "utf8");
    const taskData = JSON.parse(fileContent);
    prompt = taskData.INSTRUCTION;
  } catch (error) {
    console.error("❌ エラー: task_info.json の読み込みに失敗したか、ファイルが存在しません。");
    process.exit(1);
  }

  if (!prompt) {
    console.error("エラー: Notionからの指示(INSTRUCTION)が空です。");
    process.exit(1);
  }

  console.log("🚀 Geminiが要件を解析中...");

  const response = await ai.models.generateContent({
    model: "gemini-2.5-flash",
    contents: `
      あなたはシニアフルスタックエンジニアです。以下の【要件】を満たすTypeScriptコードを実装してください。

      【要件】: ${prompt}

      【出力ルール】:
      必ず以下の形式で、ファイル名とコードをコードブロックで出力してください。複数のファイルが必要な場合は、それぞれブロックを分けてください。解説などの雑談は一切不要です。コードのみを出力してください。

      [FILE_START: src/components/Example.tsx]
      \`\`\`tsx
      // ここにコード
      \`\`\`
      [FILE_END]
    `,
  });

  // 2. Geminiの返答からファイルを抽出し、ローカルに書き出す
  const text = response.text;
  const fileRegex = /\[FILE_START:\s*(.+?)\][\s\S]*?```[\s\S]*?\n([\s\S]*?)```[\s\S]*?\[FILE_END\]/g;
  let match;

  while ((match = fileRegex.exec(text)) !== null) {
    const filePath = match[1].trim();
    const codeContent = match[2];

    // ディレクトリがなければ自動作成して書き込み
    fs.mkdirSync(path.dirname(filePath), { recursive: true });
    fs.writeFileSync(filePath, codeContent, "utf8");
    console.log(`📝 ファイルを自動作成しました: ${filePath}`);
  }
}

generateCode().catch(console.error);
