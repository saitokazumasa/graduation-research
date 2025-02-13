package com.tabisketch.google.bean;

/// 金額
public class Money {
    /// ISO4217で定義されている3文字の通貨コード
    private String currencyCode;
    /// 金額の整数部分
    private String units;
    /// 金額の小数部分をナノ単位で表した数
    private Integer nanos;
}
