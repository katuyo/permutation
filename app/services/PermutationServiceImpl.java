package services;

import com.google.common.collect.Lists;
import modules.Permutable;
import modules.ResultReceive;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PermutationServiceImpl implements PermutationService {
    @Inject
    private Permutable permutable;

    @Override
    public List<String> permuatate(Integer n) {
        ResultReceive<String> resultReceive = new ResultReceive<String>() {
            BlockingQueue<String> result = new LinkedBlockingDeque<>();
            Boolean init = true;
            @Override
            public void add(String s) {
                init = false;
                result.add(s);
            }

            @Override
            public String get() {
                return result.poll();
            }

            @Override
            public boolean isClosed() {
                return !init && result.isEmpty();
            }
        };
        permutable.permutate(n, resultReceive);
        List<String> list = Lists.newArrayList();
        while (!resultReceive.isClosed()) {
            list.add(resultReceive.get());
        }
        return list;
    }
}
