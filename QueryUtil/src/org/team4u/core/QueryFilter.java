package org.team4u.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.team4u.SysConstants;
import org.team4u.util.StringUtil;

public class QueryFilter {

	private String dynamicRule = SysConstants.DEFAULT_DYNAMIC_RULE;

	private String paramRule = SysConstants.DEFAULT_PARAM_PREFIX + SysConstants.DEFAULT_PARAM_RULE;

	private String placeholderRule = SysConstants.DEFAULT_PLACEHOLDER_PREFIX
			+ SysConstants.DEFAULT_PARAM_RULE;

	private String queryString;

	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public void put(String key, Object value) {
		if (StringUtil.isBlank(value)) {
			paramMap.remove(key);
			return;
		}

		paramMap.put(key, value);
	}

	public Object get(String key) {
		return paramMap.get(key);
	}

	public String process() {
		Pattern p = Pattern.compile(dynamicRule, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(queryString);

		while (m.find()) {
			String cndGroupString = m.group(0);
			String cndString = m.group(1);

			Pattern paramPattern = Pattern.compile(paramRule, Pattern.CASE_INSENSITIVE);
			Matcher paramMatcher = paramPattern.matcher(cndString);
			boolean clear = false;

			while (paramMatcher.find()) {
				String param = paramMatcher.group(1);
				Object value = paramMap.get(param);

				if (value == null || value.equals("")) {
					queryString = queryString.replace(cndGroupString, "");
					clear = true;
					break;
				}
			}

			if (!clear) {
				queryString = queryString.replace(cndGroupString, cndString);
			}
		}

		p = Pattern.compile(placeholderRule, Pattern.CASE_INSENSITIVE);
		m = p.matcher(queryString);

		while (m.find()) {
			String param = m.group(0);
			Object value = paramMap.get(param);

			if (value == null) {
				value = "";
			}

			queryString = queryString.replaceAll("\\" + param + "\\b", value.toString());
		}

		return queryString;
	}

	public String getDynamicRule() {
		return dynamicRule;
	}

	public void setDynamicRule(String dynamicRule) {
		this.dynamicRule = dynamicRule;
	}

	public String getParamRule() {
		return paramRule;
	}

	public void setParamRule(String paramRule) {
		this.paramRule = paramRule;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
}
