package ru.perfomanceLabs.tasks.task4;

import ru.perfomanceLabs.tasks.common.TaskException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Starter {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new TaskException("Неверное число параметров, должно быть 1");
        }
        String fileName = args[0];

        Path file = Paths.get(fileName);
        if (!Files.exists(file))
            throw new TaskException(String.format("Файл %s не найден", fileName) );

        try {
            List<String> lines = Files.readAllLines(file);
            List<Integer> copyLine = lines.stream().map(s->Integer.parseInt(s.trim())).sorted().collect(Collectors.toList());

            final int averageInt = copyLine.get((int)Math.floor(copyLine.size()/2));
            System.out.println(lines.stream().mapToLong(s->Long.parseLong(s.trim())).map(n->Math.abs(n-averageInt)).sum());
        } catch (IOException e) {
            throw new TaskException("Ошибка чтения файла " + fileName);
        } catch (NumberFormatException e) {
            throw new TaskException("Ошибка приведения типов");
        } catch (IndexOutOfBoundsException e) {
            throw new TaskException("Файл пустой");
        }
    }
}
