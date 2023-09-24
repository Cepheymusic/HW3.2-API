package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.ThreadService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/auxiliary")
public class AuxiliaryController {
    private ThreadService threadService;

    public AuxiliaryController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public Integer sumNumber() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return sum;
    }

    @GetMapping("/thread")
    public void startThread() {
        threadService.thread();
    }

}
