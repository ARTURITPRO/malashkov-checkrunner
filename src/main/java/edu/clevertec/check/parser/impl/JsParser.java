package edu.clevertec.check.parser.impl;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import edu.clevertec.check.parser.Parser;
public class JsParser implements Parser {

    @SneakyThrows
    private String parseString(Field field, Object o) {
        Object o1 = field.get(o);
        StringBuilder sb = new StringBuilder();
        if (o1 == null) {
            return sb.append("\"").append(field.getName()).append("\":").toString() + null + ",";
        }
        return sb.append("\"").append(field.getName()).append("\":")
                .append("\"").append(field.get(o)).append("\",").toString();
    }

    @SneakyThrows
    private String parseString(Object o) {
        return "\"" + o + "\"";
    }

    @SneakyThrows
    private String parseArray(Field field, Object o) {
        Object[] objects = new Object[Array.getLength(field.get(o))];
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < objects.length; i++) {
            objects[i] = Array.get(field.get(o), i);
        }
        for (Object o1 : objects) {
            res.append(parseSelect(o1)).append(",");
        }

        return "\"" + field.getName() + "\":" + res.deleteCharAt(res.length() - 1).append("],");
    }


    @SneakyThrows
    private String preferParseField(Field field, Object o) {
        if (field.getType().isPrimitive()) {
            return parsePrimitive(field, o);
        } else if (field.getType().isArray()) {
            return parseArray(field, o);
        } else if (Number.class.isAssignableFrom(field.getType())) {
            return parsePrimitive(field, o);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return parseString(field, o);
        } else if (Iterable.class.isAssignableFrom(field.getType())) {
            return parseList(field, o);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            return parseMap(field, o);
        } else {
            return parseObject(field, o);
        }
    }

    @SneakyThrows
    private String parseList(Field field, Object list) {
        StringBuilder res = new StringBuilder("[");
        List<?> listOfObjects = ((List<?>) field.get(list));
        for (Object o1 : listOfObjects) {
            res.append(parseSelect(o1)).append(",");
        }
        return "\"" + field.getName() + "\":" + res.deleteCharAt(res.length() - 1).append("],");
    }

    @SneakyThrows
    private String parseSelect (Object o) {
        if (o.getClass().isPrimitive()) {
            return parsePrimitive(o);
        } else if (o.getClass().isArray()) {
            return parseArray(o);
        } else if (Number.class.isAssignableFrom(o.getClass())) {
            return parsePrimitive(o);
        } else if (String.class.isAssignableFrom(o.getClass())) {
            return parseString(o);
        } else if (Iterable.class.isAssignableFrom(o.getClass())) {
            return parseList(o);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            return parseMap(o);
        } else {
            return parseObject(o);
        }
    }

    @SneakyThrows
    private String parseMap(Field field, Object o) {
        Map<?, ?> map = ((Map<?, ?>) field.get(o));
        StringBuilder str = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            str.append(parseSelect(entry.getKey())).append(":");
            str.append(parseSelect(entry.getValue())).append(",");
        }

        return "\"" + field.getName() + "\":" + str.deleteCharAt(str.length() - 1).append("},");
    }

    @SneakyThrows
    private String parseObject(Field field, Object o) {
        if (o == null) {
            return null;
        }

        Object o1 = field.get(o);
        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o1.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(preferParseField(declaredField, o1));

            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return "\"" + field.getName() + "\":" + result.append("},");
    }

    @Override
    public String parseToString(Object o) {
        if (o == null) {
            return null;
        }
        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(preferParseField(declaredField, o));

            declaredField.setAccessible(false);
        }

        return result.deleteCharAt(result.length() - 1).append("}").toString();
    }

    @SneakyThrows
    private String parsePrimitive(Object o) {
        return o + "";
    }

    @SneakyThrows
    private String parseArray(Object o) {
        Object[] array2 = new Object[Array.getLength(o)];
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < array2.length; i++) {
            array2[i] = Array.get(o, i);
        }
        for (Object o1 : array2) {
            res.append(parseSelect(o1)).append(",");
        }

        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    @SneakyThrows
    private String parseMap(Object o) {
        Map<?, ?> map = ((Map<?, ?>) o);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            str.append(parseSelect(entry.getKey())).append(":");
            str.append(parseSelect(entry.getValue())).append(",");
        }

        return str.deleteCharAt(str.length() - 1).append("}").toString();
    }

    @SneakyThrows
    private String parseList(Object o) {
        StringBuilder res = new StringBuilder("[");
        List<?> list = ((List<?>) (o));
        for (Object o1 : list) {
            res.append("\"").append(parseSelect(o1)).append(",");
        }
        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    @SneakyThrows
    public String parseObject(Object o) {
        if (o == null) {
            return null;
        }

        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(preferParseField(declaredField, o));

            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return result.append("}").toString();
    }


    @SneakyThrows
    private String parsePrimitive(Field field, Object o) {
        StringBuilder stringBuilder = new StringBuilder();
        if (char.class.isAssignableFrom(field.getType())) {
            return stringBuilder.append("\"").append(field.getName()).append("\"")
                    .append(":\"").append(field.get(o)).append("\",").toString();
        }
        return stringBuilder.append("\"").append(field.getName()).append("\":")
                .append(field.get(o)).append(",").toString();
    }

}
