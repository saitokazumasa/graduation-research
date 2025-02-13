package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 料金情報
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TollInfo {
    /// 通行料の金額
    private Money estimatedPrice;
}
