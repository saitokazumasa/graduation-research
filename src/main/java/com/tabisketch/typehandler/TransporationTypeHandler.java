package com.tabisketch.typehandler;

import com.tabisketch.constant.Transporation;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Transporation.class)
public class TransporationTypeHandler extends BaseTypeHandler<Transporation> {
    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final Transporation parameter, final JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.toString(), Types.OTHER);
    }

    @Override
    public Transporation getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        final String enumName = rs.getString(columnName);
        return Enum.valueOf(Transporation.class, enumName);
    }

    @Override
    public Transporation getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        final String enumName = rs.getString(columnIndex);
        return Enum.valueOf(Transporation.class, enumName);
    }

    @Override
    public Transporation getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        final String enumName = cs.getString(columnIndex);
        return Enum.valueOf(Transporation.class, enumName);
    }
}
