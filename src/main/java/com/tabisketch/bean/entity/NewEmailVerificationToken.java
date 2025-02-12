package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/// 新メールアドレス認証トークン
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewEmailVerificationToken {
    /// 有効期限（分）
    public static final int LIFETIME_MINUTES = 30;

    /// 識別子文字列
    private UUID uuid;
    /// 新しいメールアドレス
    private String newEmail;
    /// 関連する「ユーザー」の識別子
    private int userId;
    /// 作成日
    private LocalDateTime createdAt;

    public LocalDateTime getLifeTime() {
        return this.createdAt.plusMinutes(LIFETIME_MINUTES);
    }
}
