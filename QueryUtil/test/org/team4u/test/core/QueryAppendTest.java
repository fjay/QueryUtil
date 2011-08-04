package org.team4u.test.core;

import org.junit.Test;
import org.team4u.core.QueryAppend;

public class QueryAppendTest {

	@Test
	public void append() {
		String queryString = "select * from ss where d = @d and @append_cnd";
		QueryAppend queryAppend = new QueryAppend();
		queryAppend.setQueryString(queryString);
		queryAppend.put("a", "=", "abc");
		queryAppend.process();
		System.out.println(queryAppend.getQueryString());
		System.out.println(queryAppend.getParamValues());
	}
}
