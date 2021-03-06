package com.bdbvip.utils.common.dao.support.mybatis.dialect;

/**
 * derby的物理分页
 */
public class DerbyDialect implements Dialect {

	public String getLimitString(String sql, int offset, int limit) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return false;
	}

}
