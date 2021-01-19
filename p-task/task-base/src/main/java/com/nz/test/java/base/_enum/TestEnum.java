package com.nz.test.java.base._enum;

import java.util.EnumMap;

enum Colors {
    RED, GREEN, BLUE;
}

public class TestEnum {

    public enum Color {
        RED("红色", 1), GREEN("绿色", 2), WHITE("白色", 3), YELLOW("黄色", 4);

        private String name;
        private int index;

        private Color(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return this.index + "-" + this.name;
        }
    }

    private static EnumMap<DataBaseType, String> urls = new EnumMap<DataBaseType, String>(DataBaseType.class);

    static {
        urls.put(DataBaseType.DB2, "jdbc:db2://localhost:5000/sample");
        urls.put(DataBaseType.MYSQL, "jdbc:mysql://localhost/mydb");
        urls.put(DataBaseType.ORACLE, "jdbc:oracle:thin:@localhost:1521:sample");
        urls.put(DataBaseType.SQLSERVER, "jdbc:microsoft:sqlserver://sql:1433;Database=mydb");
    }

    public enum DataBaseType {
        MYSQL, DB2, SQLSERVER, ORACLE;
    }


    public static String getURL(DataBaseType type) {
        return urls.get(type);
    }

    public static void main(String[] args) {
        System.out.println(Colors.RED);
        System.out.println(ResultEnum.msg(500));
        System.out.println(Color.RED.toString());
        System.out.println(getURL(DataBaseType.MYSQL));
    }
}
