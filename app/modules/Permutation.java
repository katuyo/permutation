package modules;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Permutation implements Permutable {

    private ExecutorService executorService;// = Executors.newFixedThreadPool(50);

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private Map<String, List<Integer>> takeOne(String seq, List<Integer> left,
                                               final ResultReceive<String> resultReceive) {
        final Map<String, List<Integer>> map = new HashMap<>();
        left.forEach(r -> {
            List<Integer> restList = new ArrayList<>(left);
            restList.remove(r);
            map.put(seq + r.toString(), restList);
        });
        if (left.size() == 1) {
            map.forEach((k, v) -> resultReceive.add(k));
        } else {
            map.forEach((k, v) -> Optional.ofNullable(executorService)
                    .map(executor -> executor.submit(() -> this.takeOne(k, v, resultReceive)))
                    .orElseGet(() -> Futures.immediateFuture(this.takeOne(k, v, resultReceive))));
        }
        return map;
    }

    @Override
    public void permutate(Integer n, final ResultReceive<String> resultReceive) {
        final List<Integer> left = Lists.newArrayList();
        for (int i = 1; i <= n; i++) {
            left.add(i);
        }
        takeOne("", left, resultReceive);
    }
}
