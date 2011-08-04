package org.team4u.test.core;

import org.junit.Test;
import org.team4u.core.QueryFilter;

public class QueryFilterTest {

	@Test
	public void clearWarp() {
		String queryString = "select * from ss where $[a = @a] AND d = @d order by abc";
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setQueryString(queryString);
		queryFilter.put("a", "abc");
		queryFilter.process();
		System.out.println(queryFilter.getQueryString());
	}

	@Test
	public void placeholder() {
		String queryString = "select * from ss where $[a = @a and c = @c AND d = @d] and $a order by abc";
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setQueryString(queryString);
		queryFilter.put("$a", "a = 1111");
		queryFilter.process();
		System.out.println(queryFilter.getQueryString());

		queryFilter.setQueryString(queryString);
		queryFilter.put("$a", "");
		queryFilter.process();
		System.out.println(queryFilter.getQueryString());
	}
}
