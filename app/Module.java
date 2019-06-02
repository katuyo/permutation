import com.google.inject.AbstractModule;
import modules.Permutable;
import modules.Permutation;
import services.PermutationService;
import services.PermutationServiceImpl;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(PermutationService.class).to(PermutationServiceImpl.class);
        bind(Permutable.class).to(Permutation.class);
    }
}
