package utils;

//import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.DBOperator;

public class ClassReflectUtil {

	public final static int _INVALID_NUM_ = -1;
	public final static String _INVALID_STRING_ = null;
	
	/**
	 * 传入对象获取相对应的 Insert 操作的 Sql 语句
	 * @param object 传入的对象
	 * @param tableName 数据库表名，若该参数为 null 则使用传入对象的类名
	 * @return Sql 的 Insert 语句
	 */
	public static String getInsertObjectSql(Object object, String tableName) {
		// 定义一个sql字符串
		String sql = "insert into ";
		// 得到对象的类
		Class c = object.getClass();
		// 得到对象中所有的方法
		Method[] methods = c.getMethods();
		// 得到对象中所有的属性
		//Field[] fields = c.getFields();
		// 得到对象类的名字
		String cName = c.getName();
		// 从类的名字中解析出表名
		if (tableName == null)
			tableName = cName.substring(cName.lastIndexOf(".") + 1, cName.length());
		sql += tableName + "(";
		List<String> mList = new ArrayList<String>();
		List vList = new ArrayList();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get") && !mName.startsWith("getClass")) {
				try {
					Object value = method.invoke(object, null);
					System.out.println("执行方法返回的值：" + value);
					if (value == null) {
						System.out.println("返回 Object 为空, 跳过!");
						continue;
					}
					if (value instanceof String) {
						vList.add("\"" + value + "\"");
						System.out.println("字段值------>" + value);
					} else {
						if (value.toString().equals("-1")) {
							System.out.println("字段值无效, 跳过!");
							continue;
						}
						vList.add(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				String fieldName = mName.substring(3, mName.length()).toLowerCase();
				mList.add(fieldName);
				System.out.println("字段名字----->" + fieldName);
			}
		}
		for (int i = 0; i < mList.size(); i++) {
			if (i < mList.size() - 1) {
				sql += mList.get(i) + ",";
			} else {
				sql += mList.get(i) + ") values (";
			}
		}
		for (int i = 0; i < vList.size(); i++) {
			if (i < vList.size() - 1) {
				sql += vList.get(i) + ",";
			} else {
				sql += vList.get(i) + ")";
			}
		}
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 传入对象获取相对应的 Select 操作的 Sql 语句
	 * @param object 传入的对象
	 * @param tableName 数据库表名，若该参数为 null 则使用传入对象的类名
	 * @return Sql 的 Select 语句
	 */
	public static String getSelectObjectSql(Object object, String tableName) {
		// 定义一个sql字符串
		String sql = "select * from ";
		// 得到对象的类
		Class c = object.getClass();
		// 得到对象中所有的方法
		Method[] methods = c.getMethods();
		// 得到对象中所有的属性
		//Field[] fields = c.getFields();
		// 得到对象类的名字
		String cName = c.getName();
		// 从类的名字中解析出表名
		if (tableName == null)
			tableName = cName.substring(cName.lastIndexOf(".") + 1, cName.length());
		sql += tableName + " where ";
		List<String> mList = new ArrayList<String>();
		List vList = new ArrayList();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get") && !mName.startsWith("getClass")) {
				try {
					Object value = method.invoke(object, null);
					System.out.println("执行方法返回的值：" + value);
					if (value == null) {
						System.out.println("返回 Object 为空, 跳过!");
						continue;
					}
					if (value instanceof String) {
						vList.add("\"" + value + "\"");
						System.out.println("字段值------>" + value);
					} else {
						if (value.toString().equals("-1")) {
							System.out.println("字段值无效, 跳过!");
							continue;
						}
						vList.add(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				String fieldName = mName.substring(3, mName.length()).toLowerCase();
				mList.add(fieldName);
				System.out.println("字段名字----->" + fieldName);
			}
		}
		for (int i = 0; i < mList.size(); i++) {
			if (i < mList.size() - 1) {
				sql += mList.get(i) + " = "  + vList.get(i) + " and ";
			} else {
				sql += mList.get(i) + " = "  + vList.get(i);
			}
		}
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 传入对象获取相对应的 Update 操作的 Sql 语句
	 * @param object 传入的对象
	 * @param tableName 数据库表名，若该参数为 null 则使用传入对象的类名
	 * @param conditions 限定语句 where 的字段名集合，该集合中的字段将与类中对应的值组成限定语句
	 * @return Sql 的 Update 语句
	 */
	public static String getUpdateObjectSql(Object object, String tableName, String[] conditions) {
		String sql = "update ";
		String endSql = " where ";
		boolean hasOneCondition = false;
		Class c = object.getClass();
		// 得到对象中所有的方法
		Method[] methods = c.getMethods();
		// 得到对象中所有的属性
		//Field[] fields = c.getFields();
		// 得到对象类的名字
		String cName = c.getName();
		// 从类的名字中解析出表名
		if (tableName == null)
			tableName = cName.substring(cName.lastIndexOf(".") + 1, cName.length());
		sql += tableName + " set ";
		Map<String, Object> list = new HashMap<>();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get") && !mName.startsWith("getClass")) {
				String fieldName = mName.substring(3, mName.length()).toLowerCase();
				System.out.println("字段名字----->" + fieldName);
				try {
					Object value = method.invoke(object, null);
					System.out.println("执行方法返回的值：" + value);
					if (value == null) {
						System.out.println("返回 Object 为空, 跳过!");
						continue;
					}
					if (value instanceof String) {
						value = "\"" + value + "\"";
						System.out.println("字段值------>" + value);
					} else {
						if (value.toString().equals("-1")) {
							System.out.println("字段值无效, 跳过!");
							continue;
						}
					}
					list.put(fieldName, value);
					for (int i = 0; i < conditions.length; i++) {
						if (conditions[i].toLowerCase().equals(fieldName.toLowerCase())) {
							if (hasOneCondition) {
								endSql += " and " + fieldName + " = " + value;
							} else {
								endSql += fieldName + " = " + value;
								hasOneCondition = true;
							}
							list.remove(fieldName, value);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		for (Map.Entry<String, Object> entry : list.entrySet()) {
			sql += entry.getKey() + " = " + entry.getValue() + " , ";
		}
		sql = sql.substring(0, sql.length() - 3);
		sql += endSql;
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 传入对象获取相对应的 delete 操作的 Sql 语句
	 * @param object 传入的对象
	 * @param tableName 数据库表名，若该参数为 null 则使用传入对象的类名
	 * @param conditions 限定语句 where 的字段名集合，该集合中的字段将与类中对应的值组成限定语句
	 * @return Sql 的 Delete 语句
	 */
	public static String getDeleteObjectSql(Object object, String tableName, String[] conditions) {
		String sql = "delete from ";
		Class c = object.getClass();
		// 得到对象中所有的方法
		Method[] methods = c.getMethods();
		// 得到对象中所有的属性
		//Field[] fields = c.getFields();
		// 得到对象类的名字
		String cName = c.getName();
		// 从类的名字中解析出表名
		if (tableName == null)
			tableName = cName.substring(cName.lastIndexOf(".") + 1, cName.length());
		sql += tableName + " where ";
		List<String> mList = new ArrayList<String>();
		List vList = new ArrayList();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get") && !mName.startsWith("getClass")) {
				String fieldName = mName.substring(3, mName.length()).toLowerCase();
				for (int i = 0; i < conditions.length; i++) {
					if (fieldName.toLowerCase().equals(conditions[i].toLowerCase())) {
						mList.add(fieldName);
						System.out.println("字段名字----->" + fieldName);
						try {
							Object value = method.invoke(object, null);
							System.out.println("执行方法返回的值：" + value);
							if (value == null) {
								System.out.println("返回 Object 为空, 跳过!");
								mList.remove(fieldName);
								continue;
							}
							if (value instanceof String) {
								value = "\"" + value + "\"";
								vList.add(value);
								System.out.println("字段值------>" + value);
							} else {
								if (value.toString().equals("-1")) {
									System.out.println("字段值无效, 跳过!");
									mList.remove(fieldName);
									continue;
								}
								vList.add(value);
							}
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
						break;
					}
				}
			}
		}
		for (int i = 0; i < mList.size(); i++) {
			if (i < mList.size() - 1) {
				sql += mList.get(i) + " = "  + vList.get(i) + " and ";
			} else {
				sql += mList.get(i) + " = "  + vList.get(i);
			}
		}
		System.out.println(sql);
		return sql;
	}

	/**
	 * 从数据库中获取对应 id 的对象
	 *
	 * @param className 类名
	 * @param id 限定语句的表项 id
	 * @return 需要查找的对象
	 */
	public static Object getObjectFromDb(Object object, String tableName, int id) {
		String cName = object.getClass().getName();
		// 从类的名字中解析出表名
		if (tableName == null)
			tableName = cName.substring(cName.lastIndexOf(".") + 1, cName.length());
		List<Object> objs = new ArrayList<>();
		try {
			if (DBOperator.initDBConnection() == null)
				return null;
			ResultSet set = null;
			if (id != -1)
				set = DBOperator.loadObject(tableName, id);
			else
				set = DBOperator.loadObjects(tableName);
			// 得到对象的方法数组
			Method[] methods = object.getClass().getMethods();
			while (set.next()) {
				// 遍历对象的方法
				for (Method method : methods) {
					String methodName = method.getName();
					// 如果对象的方法以set开头
					if (methodName.startsWith("set")) {
						// 根据方法名字得到数据表格中字段的名字
						String columnName = methodName.substring(3, methodName.length());
						// 得到方法的参数类型
						Class[] parmts = method.getParameterTypes();
						if (parmts[0] == String.class) {
							// 如果参数为 String 类型，则从结果集中按照列名取得对应的值，并且执行该 set 方法
							method.invoke(object, set.getString(columnName));
							continue;
						}
						if (parmts[0] == int.class) {
							method.invoke(object, set.getInt(columnName));
							continue;
						}
						if (parmts[0] == long.class) {
							method.invoke(object, set.getLong(columnName));
							continue;
						}
					}
				}
				objs.add(object);
				if (!set.isLast())
					object = object.getClass().newInstance();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (objs.isEmpty())
			return null;
		if (id != -1) {
			return objs.get(0);
		} else
			return objs;
	}
	
	/**
	 * 从对象中获取所有对象
	 * @param className 对象对应数据库的表名
	 * @return 所有对象的集合
	 */
	public static List<Object> getObjectsFromDb(Object object, String tableName) {
		Object objs = getObjectFromDb(object, tableName, -1);
		if (objs != null)
			return (List<Object>) objs;
		else
			return null;
	}
	
	/**
	 * 从 HTTP 请求中获取所有参数的值并 set 到对应 {@code object} 中去
	 * @param object 要被 set 的 {@code object}
	 * @param request HTTP 请求
	 * @return set 完成后的 {@code object}
	 */
	public static Object getObjectFromHttpRequestParameters(Object object, HttpServletRequest request) {
		// 得到对象的方法数组
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName().toLowerCase();
			// 如果对象的方法以set开头
			if (methodName.startsWith("set")) {
				// 根据方法名字得到参数的名字
				String paramName = methodName.substring(3, methodName.length());
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues == null || paramValues.length != 1 || paramValues[0].length() == 0)
					continue;
				// 得到方法的参数类型
				Class[] parmts = method.getParameterTypes();
				if (parmts[0] == String.class) {
					// 如果参数为 String 类型，则从结果集中按照列名取得对应的值，并且执行该 set 方法
					try {
						method.invoke(object, paramValues[0]);
						continue;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
				if (parmts[0] == int.class) {
					try {
						method.invoke(object, Integer.parseInt(paramValues[0]));
						continue;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
				if (parmts[0] == long.class) {
					try {
						method.invoke(object, Long.parseLong(paramValues[0]));
						continue;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
			}
		}
		return object;
	}
}
