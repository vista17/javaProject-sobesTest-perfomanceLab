package ru.perfomanceLabs.tasks.task3.parsers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.perfomanceLabs.tasks.common.TaskException;

import java.util.HashMap;
import java.util.Map;

public class ValuesJsonParser {
    private static final String TAG_VALUES = "values";
    private static final String TAG_ID = "id";
    private static final String TAG_VALUE = "value";
    private Map<Long, String> values = new HashMap<>();

    public void parse(String jsonString) {
        try {
            JSONObject root = (JSONObject) new JSONParser().parse(jsonString);

            JSONArray array = (JSONArray) root.get(TAG_VALUES);
            if (array.isEmpty())
                throw new TaskException("Файл со значениями пуст");
            for (Object o: array) {
                JSONObject value = (JSONObject) o;
                values.put((Long)value.get(TAG_ID), (String)value.get(TAG_VALUE));
            }

        } catch (ParseException e) {
            throw new TaskException("Ошибка в формате файла со значениями");
        }
    }

    public Map<Long, String> getValues() {
        return values;
    }
}
