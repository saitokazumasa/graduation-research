package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 特定の言語のテキストのローカライズ
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocalizedText {
    /// ローカライズされた文字列
    private String text;
    /// BCP-47言語コード
    private String languageCode;
}
