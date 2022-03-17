package ru.perfomanceLabs.tasks.task3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.perfomanceLabs.tasks.common.TaskException;
import ru.perfomanceLabs.tasks.task3.parsers.TestsJsonParser;
import ru.perfomanceLabs.tasks.task3.parsers.ValuesJsonParser;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StarterJson {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new TaskException("Неверное число параметров, должно быть 2");
        }
        String testsFileName = args[0];
        String valuesFileName = args[1];

        Path testsFile = Paths.get(testsFileName);
        if (!Files.exists(testsFile))
            throw new TaskException(String.format("Файл %s не найден", testsFileName) );
        Path valuesFile = Paths.get(valuesFileName);
        if (!Files.exists(valuesFile))
            throw new TaskException(String.format("Файл %s не найден", valuesFileName) );

        try {

            ValuesJsonParser valuesJsonParser = new ValuesJsonParser();
            valuesJsonParser.parse(new String(Files.readAllBytes(valuesFile)));

            TestsJsonParser testsJsonParser = new TestsJsonParser();
            testsJsonParser.parse(new String(Files.readAllBytes(testsFile)));

            testsJsonParser.setValue(null, valuesJsonParser.getValues());
            JSONArray jsonArrayTests = testsJsonParser.createReportJson(null );
            JSONObject root = new JSONObject();
            root.put("tests", jsonArrayTests);

            try(FileWriter fileWriter = new FileWriter(testsFile.toFile().getParent() + "/report.json");) {
                fileWriter.write(root.toJSONString());
                fileWriter.flush();
            }catch (IOException e) {
                throw new TaskException("Ошибка записи в файл");
            }

        } catch (FileNotFoundException e) {
            throw new TaskException("Файл не найден");
        } catch (IOException e) {
            throw new TaskException("Ошибка чтения файла");
        }
    }

}
