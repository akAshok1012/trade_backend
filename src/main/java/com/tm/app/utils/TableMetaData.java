package com.tm.app.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

public class TableMetaData {

	public static List<String> getHeaders(DSLContext dsl, String tableName) {
		List<String> columnNames = new ArrayList<>();
		Table<Record> table = DSL.table(tableName);
		List<String> omittedHeaderList = Arrays.asList("id", "updated_at","is_notification_enabled");
		Result<Record> result = dsl.select().from(table).limit(0).fetch();
		for (Field<?> field : result.fields()) {
			if (!omittedHeaderList.contains(field.getName())) {
				columnNames.add(field.getName());
			}
		}
		return columnNames;
	}
}