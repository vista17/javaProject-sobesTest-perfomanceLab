package ru.perfomanceLabs.tasks.task2;

import ru.perfomanceLabs.tasks.common.TaskException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Starter {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            throw new TaskException("Неверное число параметров, должно быть 2");
        }
        String circleFileName = args[0];
        String pointsFileName = args[1];

        Path circleFile = Paths.get(circleFileName);
        if (!Files.exists(circleFile))
            throw new TaskException("Файл с данными об окружности не найден: " + circleFileName);
        Path pointsFile = Paths.get(pointsFileName);
        if (!Files.exists(pointsFile))
            throw new TaskException("Файл с координатами точек не найден: " + pointsFileName);

        try {
            List<String> circle = Files.readAllLines(circleFile);
            if ( circle.size()<2 ) {
                throw new TaskException(String.format("Неверное число параметров окружности: %d в файле %s ", circle.size(), circleFileName ));
            }
            String[] center = circle.get(0).split(" ");
            if ( center.length<2 ) {
                throw new TaskException(String.format("Неверно задан центр окружности: %s в файле %s ", center, circleFileName ));
            }
            try {
                Float.parseFloat(center[0]);
            }catch (NumberFormatException e) {
                throw new TaskException("Координата X центра окружности должна быть числом " + center[0]);
            }
            try {
                Float.parseFloat(center[1]);
            }catch (NumberFormatException e) {
                throw new TaskException("Координата Y центра окружности должна быть числом " + center[1]);
            }
            try {
                if ( Float.parseFloat(circle.get(1)) <0 )
                    throw new TaskException("Радиус окружнсти должен быть >0");
            }catch (NumberFormatException e) {
                throw new TaskException("Радиус окружности должен быть числом " + circle.get(1));
            }

            final float x = Float.parseFloat(center[0]);
            final float y = Float.parseFloat(center[1]);
            final float r = Float.parseFloat(circle.get(1));

            AtomicInteger num = new AtomicInteger();

            try (Stream<String> lineStream = Files.lines(pointsFile)) {
                long linesCount = lineStream.count();
                if (linesCount <=0 || linesCount>100 )
                    throw new TaskException("Неверно задано количество точек в файле "+ pointsFileName);
            }
            try (Stream<String> lineStream = Files.lines(pointsFile)) {
                lineStream.map(str -> str.split(" "))
                        .map(str -> {
                            float x1 = x - Float.parseFloat(str[0]);
                            float y1 = y - Float.parseFloat(str[1]);
                            return (x1 * x1 + y1 * y1 - r * r);
                        }).forEach(i -> {
                            System.out.println(num.getAndIncrement() + (i == 0 ? " - точка лежит на окружности" : i < 0 ? " - точка внутри" : " - точка снаружи"));
                        });
            }catch (IndexOutOfBoundsException e) {
                num.getAndIncrement();
                throw new TaskException("Неверное количество координат точки в строке " + num);
            }catch (NumberFormatException e) {
                num.getAndIncrement();
                throw new TaskException("Координаты точки заданы не числом в строке " + num);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
