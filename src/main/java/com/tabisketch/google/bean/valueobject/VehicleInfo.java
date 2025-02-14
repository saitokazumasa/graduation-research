package com.tabisketch.google.bean.valueobject;

import com.tabisketch.google.constant.VehicleEmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 車両情報
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleInfo {
    /// 車両の排出ガスタイプ
    private VehicleEmissionType emissionType;
}
