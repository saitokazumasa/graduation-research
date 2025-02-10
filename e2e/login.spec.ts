import { expect, test } from "@playwright/test";
import { url } from "./settings";

test('ログインすると旅行プラン一覧ページに進む', async ({ page }) => {
  // ログインページにアクセスする
  await page.goto(`${url}/login`)

  // タイトルが正しいことを確認する
  await expect(page).toHaveTitle('ログイン')

  // ログインフォームに入力する
  await page.fill('input[name="mailAddress"]', 'testuser@example.com')
  await page.fill('input[name="password"]', 'testuser')

  // ログインボタンをクリックする
  await page.click('button[type="submit"]')

  // ログイン後のページに遷移することを確認する
  await expect(page).toHaveURL(`${url}/plan/list`)

  // ログイン後のページのタイトルが正しいことを確認する
  await expect(page).toHaveTitle('旅行プラン一覧')
});
