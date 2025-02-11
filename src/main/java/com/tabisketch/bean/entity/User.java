package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// ユーザー
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    /// 識別子
    private int id;
    /// メールアドレス
    private String email;
    /// パスワード
    private String password;
    /// メールアドレス認証フラグ
    private boolean emailVerified;
}
