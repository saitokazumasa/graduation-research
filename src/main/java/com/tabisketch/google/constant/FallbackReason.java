package com.tabisketch.google.constant;

/// フォールバックレスポンスを使用している理由
public enum FallbackReason {
    /// 未指定
    FALLBACK_REASON_UNSPECIFIED,
    /// 計算中にサーバーエラーが発生したが、別のモードで計算された結果を返すことができた
    SERVER_ERROR,
    /// 計算を完了できなかったが、別のモードで計算した結果を返すことができた
    LATENCY_EXCEEDED
}
