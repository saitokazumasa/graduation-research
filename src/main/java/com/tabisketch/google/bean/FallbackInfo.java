package com.tabisketch.google.bean;

import com.tabisketch.google.constant.FallbackReason;
import com.tabisketch.google.constant.FallbackRoutingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// フォールバック結果が使用された方法と理由に関する情報
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FallbackInfo {
    /// レスポンスに使用されるルーティング モード
    private FallbackRoutingMode routingMode;
    /// 元のレスポンスではなく、フォールバック レスポンスが使用された理由
    private FallbackReason reason;
}
