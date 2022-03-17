package ru.perfomanceLabs.tasks.task1;

import ru.perfomanceLabs.tasks.common.TaskException;

import java.util.Deque;
import java.util.ArrayDeque;

public class StarterTask1 {
    private static Deque<Integer> path = new ArrayDeque<>();

    public static void main(String[] args) {
        if (args.length < 2)
            throw new TaskException("Неверное число параметров, должно быть 2");

        int n=0, m=0;
        try {
            n = Integer.parseInt(args[0]);
            if (n<=0)
                throw new TaskException("Длина массива должна быть больше 0");
        }catch (NumberFormatException e) {
            throw new TaskException("Первый параметр должен быть целым числом");
        }

        try {
            m = Integer.parseInt(args[1]);
            if (m<=0)
                throw new TaskException("Длина интервала должна быть больше 0");
        }catch (NumberFormatException e) {
            throw new TaskException("Второй параметр должен быть целым числом");
        }

        Deque deque = createArray(n, m);

        convertDeque(deque, m);

        path.stream().forEach(System.out::print);
    }

    /**метод для случая m>n, увеличивает массив в m/n раз*/
    public static Deque<Integer> createArray(int n, int m) {
        int length = m<n?n:m/n *n;
        Deque<Integer> deque =  new ArrayDeque<Integer>(length);
        for (int i = 0; i < length; i++)
            deque.add(i<n?i+1:i-i/n*n+1);

        return deque;
    }

    public static void convertDeque(Deque input, int m) {

        while (true) {
            if (input.element().equals(1) && !path.isEmpty())
                break;

            for (int i = 0; i < m-1; i++) {
                int element = (Integer)input.removeFirst();
                input.addLast(element);
                if (i==0)
                    path.add(element);

            }
            convertDeque(input, m);
        }
    }
}
