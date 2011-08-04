package org.team4u.test.core;

import org.junit.Test;
import org.team4u.core.QueryUtil;

public class QueryUtilTest {

	@Test
	public void appendRule() {
		String queryString = "select * from ss where a = @a and $[d = @d and] @append_cnd and b = 1 and 1 != $fixed";

		QueryUtil queryUtil = new QueryUtil();
		queryUtil.setQueryString(queryString);
		queryUtil.put("a", 11);
		queryUtil.put("d", "124");
		queryUtil.put("$fixed", "aaaa");
		queryUtil.append("append_b", ">", 1);
		queryUtil.process();
		System.out.println(queryUtil.getQueryString());
		System.out.println(queryUtil.getParamValues());
	}

	@Test
	public void withoutAppendRule() {
		String queryString = "select * from ss where d = @d";

		QueryUtil queryUtil = new QueryUtil();
		queryUtil.setQueryString(queryString);
		queryUtil.put("d", "124");
		queryUtil.append("append_b", "=", 1);
		queryUtil.process();
		System.out.println(queryUtil.getQueryString());
		System.out.println(queryUtil.getParamValues());
	}

	@Test
	public void clearAfterWhere() {
		String queryString = "select * from ss where $[a = @a and c = @c] AND d = @d order by abc";
		QueryUtil queryUtil = new QueryUtil();
		queryUtil.setQueryString(queryString);
		queryUtil.put("d", "123");
		queryUtil.process();
		System.out.println(queryUtil.getQueryString());
	}

	@Test
	public void clearWhere() {
		String queryString = "select * from ss where $[a = @a and c = @c AND d = @d] order by abc";
		QueryUtil queryUtil = new QueryUtil();
		queryUtil.setQueryString(queryString);
		queryUtil.process();
		System.out.println(queryUtil.getQueryString());
	}
}
