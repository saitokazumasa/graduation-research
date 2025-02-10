package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/// メールアドレス認証トークン
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerificationToken {
    /// 識別子文字列
    private UUID uuid;
    /// 関連する「ユーザー」の識別子
    private int userId;
    /// 作成日
    private LocalDateTime createdAt;
}
